using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.Net.Json;
using System.Net;
using System.IO;

using NewsForNLP;

namespace NewsCrawler
{
    public partial class frmNewsByJson : Form
    {
        public frmNewsByJson()
        {
            InitializeComponent();
        }

        private void frmNewsByJson_Load(object sender, EventArgs e)
        {
            txtDate1.Text = mc1.SelectionStart.ToShortDateString();
            txtDate2.Text = mc2.SelectionStart.ToShortDateString();

            this.dgv1.Rows.Clear();
            this.dgv1.AutoResizeRowHeadersWidth(DataGridViewRowHeadersWidthSizeMode.AutoSizeToAllHeaders);
        }

        private void mc1_DateChanged(object sender, DateRangeEventArgs e)
        {
            txtDate1.Text = e.Start.ToShortDateString();
        }

        private void mc2_DateChanged(object sender, DateRangeEventArgs e)
        {
            txtDate2.Text = e.Start.ToShortDateString();
        }

        private void btnGetNewsList_Click(object sender, EventArgs e)
        {
            if (GetNewsList())
                MessageBox.Show("GetNewsList 완료");
            else
                MessageBox.Show("ERROR!!! GetNewsList 실패");
        }

        private bool GetNewsList()
        {
            bool result = false;

            DateTime dtStart = mc1.SelectionStart;
            DateTime dtEnd = mc2.SelectionStart;
            DateTime dtProc = mc1.SelectionStart;

            try
            {
                #region 년/월가 바뀌면 처리 않함
                if (dtStart.Year != dtEnd.Year || dtStart.Month != dtEnd.Month)
                {
                    MessageBox.Show("조회 범위가 너무 넓기 때문에 지원하지 않습니다. 동년/동월");
                    throw new Exception("조회 범위가 너무 넓기 때문에 지원하지 않습니다. 동년/동월");
                }
                #endregion

                #region 날짜가 역전되면 처리 않함
                if (dtStart.Day > dtEnd.Day)
                {
                    MessageBox.Show("날짜가 역전되었기 때문에 지원하지 않습니다.");
                    throw new Exception("조회 범위가 너무 넓기 때문에 지원하지 않습니다. 동년/동월");                    
                }
                #endregion

                #region 반복할 일자를 지정
                //for (int i = dtStart.Day; i <= dtEnd.Day; i++)
                while (int.Parse(dtProc.ToShortDateString().Replace("-", "")) <= int.Parse(dtEnd.ToShortDateString().Replace("-", "")))
                {
                    GetNewsListByOneDay(dtProc.ToShortDateString().Replace("-", ""));
                    dtProc = dtProc.AddDays(1);
                }
                #endregion
                result = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return result;
        }

        private void GetNewsListByOneDay(string date)
        {
            bool hasNext = true;
            int page = 1;
            do
            {
                if (!GetNewsListByOneDayByOnePage(date, page))
                    hasNext = false;
                
                //if (page == 1) hasNext = false;   // for debug
                page++;
            } while (hasNext);
        }

        private bool GetNewsListByOneDayByOnePage(string date, int page)
        {
            //Console.WriteLine(date + " - " + page.ToString());
            bool result = false;

            try
            {
                string url = "http://media.daum.net/api/service/news/list/category/1002.json?regdate=" + date + "&page=" + page + " ";
                string jsondata = "";
                if (getWebRequest(url, ref jsondata))
                {
                    //Console.WriteLine(jsondata);
                    result = procJsonData(ref jsondata);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            return result;
        }

        private bool getWebRequest(string url, ref string jsondata)
        {
            bool result = false;

            // Create a request using a URL that can receive a post. 
            WebRequest request = WebRequest.Create(url);
            // Set the Method property of the request to POST.
            request.Method = "POST";
            // Create POST data and convert it to a byte array.
            string postData = "This is a test that posts this string to a Web server.";
            byte[] byteArray = Encoding.UTF8.GetBytes(postData);
            // Set the ContentType property of the WebRequest.
            request.ContentType = "application/x-www-form-urlencoded";
            // Set the ContentLength property of the WebRequest.
            request.ContentLength = byteArray.Length;
            // Get the request stream.
            Stream dataStream = request.GetRequestStream();
            // Write the data to the request stream.
            dataStream.Write(byteArray, 0, byteArray.Length);
            // Close the Stream object.
            dataStream.Close();
            // Get the response.
            WebResponse response = request.GetResponse();
            // Display the status.
            //Console.WriteLine(((HttpWebResponse)response).StatusDescription);

            if (((HttpWebResponse)response).StatusDescription == "OK")
            {
                // Get the stream containing content returned by the server.
                dataStream = response.GetResponseStream();
                // Open the stream using a StreamReader for easy access.
                StreamReader reader = new StreamReader(dataStream);
                // Read the content.
                string responseFromServer = reader.ReadToEnd();
                // Display the content.
                //Console.WriteLine(responseFromServer);

                jsondata = responseFromServer;

                reader.Close();

                result = true;
            }

            // Clean up the streams.            
            dataStream.Close();
            response.Close();

            return result;
        }

        private bool procJsonData(ref string jsondata)
        {
            bool result = false;

            try
            {
                JsonTextParser parser = new JsonTextParser();
                JsonObject obj = parser.Parse(jsondata);
                JsonObjectCollection collection = (JsonObjectCollection)obj;

                bool hasNext = (bool)collection["hasNext"].GetValue();
                JsonArrayCollection simpleNews = (JsonArrayCollection)collection["simpleNews"];

                foreach (JsonObjectCollection news in simpleNews)
                {
                    string newsId = (string)news["newsId"].GetValue();
                    string title = (string)news["title"].GetValue();
                    string cpKorName = (string)news["cpKorName"].GetValue();
                    string cpEngName = (string)news["cpEngName"].GetValue();
                    string regDt = (string)news["regDt"].GetValue();
                    string modiDt = (string)news["modiDt"].GetValue();
                    string status = (string)news["status"].GetValue();

                    this.dgv1.Rows.Add(new string[] { newsId, title, cpKorName, cpEngName, regDt, modiDt, status });
                    this.dgv1.Rows[this.dgv1.Rows.Count - 2].HeaderCell.Value = (this.dgv1.Rows.Count - 1).ToString() ;
                }

                result = true && hasNext;
            }
            catch (Exception)
            {

            }
            
            return result;
        }

        private void btnSaveNewsOnDB_Click(object sender, EventArgs e)
        {
            if (SaveNewsOnDB())
                MessageBox.Show("SaveNewsOnDB 완료");
            else
                MessageBox.Show("ERROR!!! SaveNewsOnDB 실패");
        }

        private bool SaveNewsOnDB()
        {
            bool result = false;
            try
            {
                foreach (DataGridViewRow dgvr in dgv1.Rows)
                {
                    DataGridViewRow dgvr2 = dgvr;
                    cNews n = new cNews(ref dgvr2);
                    if (!n.InsertData())
                        Console.WriteLine("news insert 중에 오류가 발생했습니다. newsId=[" + n.newsId + "]");
                }
                result = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                
            }
            return result;
        }

        private void btnListClear_Click(object sender, EventArgs e)
        {
            this.dgv1.Rows.Clear();
        }
    }
}

/*
ALTER PROCEDURE sp_AddNews
	@newsId		nchar(17) 
,	@title		nvarchar(200)
,	@cpKorName	nvarchar(50)
,	@cpEngName	nvarchar(50)
,	@regDt		nchar(14)
,	@modiDt		nchar(14)
,	@status		nvarchar(50)
 */
