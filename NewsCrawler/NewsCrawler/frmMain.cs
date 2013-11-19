using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.IO;
using System.Text.RegularExpressions;

namespace NewsCrawler
{
    public partial class frmMain : Form
    {
        //private string url = "";

        public frmMain()
        {
            InitializeComponent();
        }

        private void frmMain_Load(object sender, EventArgs e)
        {
            txtDate.Text = monthCalendar1.SelectionStart.ToShortDateString();
        }

        private void monthCalendar1_DateChanged(object sender, DateRangeEventArgs e)
        {
            txtDate.Text = e.Start.ToShortDateString();
        }

        bool bProcessing = false;
        bool bFinished = false;

        private void btnGetNewsList_Click(object sender, EventArgs e)
        {
            //this.url = @"http://media.daum.net/politics/all/?regdate=" + txtDate.Text.Replace("-", "") + "#page=1&type=title";
            //int num = 1;
            //this.url = @"http://media.daum.net/politics/all/?regdate=" + txtDate.Text.Replace("-", "") + "#page=" + num.ToString() + "&type=title";
            //this.wb1.Navigate(url);

            bw1.RunWorkerAsync();

            /*
            for (num = 1; num <= 2; num++)
            {
                bProcessing = true;
                
            }
            */
        }

        private void getNews1(string url)
        {
            //MessageBox.Show(url);

            // Create a request for the URL. 		
            WebRequest request = WebRequest.Create(url);
            // If required by the server, set the credentials.
            request.Credentials = CredentialCache.DefaultCredentials;
            // Get the response.
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            // Display the status.
            Console.WriteLine(response.StatusDescription);
            // Get the stream containing content returned by the server.
            Stream dataStream = response.GetResponseStream();
            // Open the stream using a StreamReader for easy access.
            StreamReader reader = new StreamReader(dataStream);
            // Read the content.
            string responseFromServer = reader.ReadToEnd();
            // Display the content.
            //Console.WriteLine(responseFromServer);
            rtxtResult.Text = responseFromServer;
            // Cleanup the streams and the response.
            reader.Close();
            dataStream.Close();
            response.Close();
        }

        private void wb1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            // 문서가 정상적으로 로드 완료 되었을 경우만 실행
            if (this.wb1.ReadyState == WebBrowserReadyState.Complete)
            {
                //rtxtResult.Text += "WebBrowserReadyState.Complete ";
                
                if (e.Url.AbsoluteUri == wb1.Url.AbsoluteUri)
                {
                    // 원하는 작업 실행
                    //rtxtResult.Text += "DocumentCompleted ";

                    getData();
                }
                else
                {
                    //rtxtResult.Text += "... ";
                }
            }
        }

        private void getData()
        {
            int cnt = 0;

            bool bSearch = false;

            // body 태그 이하 모든 요소들 추출
            foreach (HtmlElement elements in this.wb1.Document.Body.Children)
            {
                // 각 부모요소안의 모든 요소를 추출
                foreach (HtmlElement element in elements.All)
                {
                    // 특정 태그 목록 추출
                    //rtxtResult.Text += element.TagName + "/" + element.GetAttribute("CLASS") + "  ";

                    switch (element.TagName)
                    {
                        case "DIV":
                            if (element.GetAttribute("id") == "listWrap")
                            {
                                //rtxtResult.Text += element.TagName + "/" + element.GetAttribute("id") + "\n";
                                bSearch = true;
                            }
                            else if (element.GetAttribute("id") == "pagingNews")
                            {
                                bSearch = false;
                                bProcessing = false;
                            }
                            break;

                        case "A":
                            //if (element.GetAttribute("class") == "txt")
                            if (bSearch)
                            {
                                rtxtResult.Text += element.GetAttribute("HREF") + " | " +
                                                   element.InnerText + "\n";
                                Console.WriteLine(element.GetAttribute("class"));
                                cnt++;

                                //http://media.daum.net/
                                //http://media.daum.net/v/20130810173512560
                            }
                            break;

                        /*
                    case "UL":
                        rtxtResult.Text += element.GetAttribute("CLASS");
                        break;
                                    
                    */
                        /*
                    case "IMG":
                        // 이미지 경로 출력
                        MessageBox.Show(element.GetAttribute("SRC"));
                        break;
                    case "A":
                        // 링크 경로 출력
                        MessageBox.Show(element.GetAttribute("HREF"));
                        break;
                         */
                    }
                }
            }

            if (cnt == 0)
                bFinished = true;
        }

        private void bw1_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;
            int i = 0;
            int num = 0;
            //while (true)
            {
                if (worker.CancellationPending == true)
                {
                    e.Cancel = true;
                    //break;
                }
                else
                {
                    // Perform a time consuming operation and report progress.
                    
                    if (bProcessing == false)
                    {
                        num++;
                        bProcessing = true;
                        string url = @"http://media.daum.net/politics/all/?regdate=" + txtDate.Text.Replace("-", "") + "#page=" + num.ToString() + "&type=title";
                        Console.WriteLine(url);
                        this.wb1.Navigate(url);
                    }

                    if (bFinished == true)
                    {
                        //break;
                    }
                    else
                    {
                        
                    }

                    System.Threading.Thread.Sleep(1000);
                    worker.ReportProgress(++i);
                }
            }
        }

        private void bw1_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            txtProgress.Text = e.ProgressPercentage.ToString();
        }

        private void bw1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            if (e.Cancelled == true)
            {
                rtxtResult.Text += "Canceled!\n";
            }
            else if (e.Error != null)
            {
                rtxtResult.Text += "Error: " + e.Error.Message + "\n";
            }
            else
            {
                rtxtResult.Text += "Done!\n";
            }
        }
    }
}
