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

namespace NewsContentCrawler
{
    public partial class frmNewsContentByJson : Form
    {
        public frmNewsContentByJson()
        {
            InitializeComponent();
        }

        private void frmNewsContentByJson_Load(object sender, EventArgs e)
        {
            this.dgv1.Rows.Clear();
            this.dgv1.AutoResizeRowHeadersWidth(DataGridViewRowHeadersWidthSizeMode.AutoSizeToAllHeaders);
        }

        private void btnGetNewsList_Click(object sender, EventArgs e)
        {
            if (cNews.GetNewsNoContent(ref this.dgv1))
                MessageBox.Show("GetNewsList 성공");
            else
                MessageBox.Show("ERROR!!! GetNewsList 실패");
        }

        private void btnSaveNewsOnDB_Click(object sender, EventArgs e)
        {
            if (SaveNewsOnDB())
                MessageBox.Show("SaveNewsOnDB 성공");
            else
                MessageBox.Show("ERROR!!! SaveNewsOnDB 실패");
        }

        private bool SaveNewsOnDB()
        {
            bool result = false;
            int ErrorCnt = 0;
            try
            {
                foreach (DataGridViewRow dgvr in this.dgv1.Rows)
                {
                    string newsId = dgvr.Cells[0].Value.ToString();
                    if (procGetNewsContent(newsId))
                    {
                        Console.WriteLine("procGetNewsContent에 오류가 발생하였습니다. newsId=[" + newsId + "]");
                        ErrorCnt++;
                    }
                    //break;      // for debug                    
                }
                result = true;
                MessageBox.Show("SaveNewsOnDB() 처리 중 발생된 오류 개수 : [" + ErrorCnt.ToString() + "]");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;            
        }

        private bool procGetNewsContent(string newsId)
        {
            bool result = false;

            try
            {
                string url = "http://media.daum.net/api/service/news/view.jsonp?newsid=" + newsId + " ";
                string jsondata = "";
                if (getWebRequest(url, ref jsondata))
                {
                    //Console.WriteLine(jsondata);
                    cNews n = null;
                    result = procJsonData(ref jsondata, ref n);

                    if (!n.UpdateNewsContent())
                        Console.WriteLine("news update 중에 오류가 발생했습니다. newsId=[" + n.newsId + "]");
                }
                
                result = true;
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

        private bool procJsonData(ref string jsondata, ref cNews n)
        {
            bool result = false;

            try
            {
                JsonTextParser parser = new JsonTextParser();
                JsonObject obj = parser.Parse(jsondata);
                JsonObjectCollection collection = (JsonObjectCollection)obj;

                string newsId = (string)collection["newsId"].GetValue();
                string content = (string)collection["content"].GetValue();
                n = new cNews(newsId, content);
                
                result = true;
            }
            catch (Exception)
            {

            }

            return result;
        }
    }
}
