using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NewsCrawlerConsole
{
    public partial class conMain
    {
        private System.Windows.Forms.WebBrowser wb1;

        public void main()
        {
            run();
        }

        public void run()
        {
            this.wb1 = new System.Windows.Forms.WebBrowser();
            this.wb1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.wb1_DocumentCompleted);

            string url = "http://media.daum.net/politics/all/?regdate=20130813#page=1&type=title";
            this.wb1.Navigate(url);
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
                            }
                            break;

                        case "A":
                            //if (element.GetAttribute("class") == "txt")
                            if (bSearch)
                            {
                                Console.Out.WriteLine(element.GetAttribute("HREF") + " | " +
                                                   element.InnerText + "\n");
                                //Console.WriteLine(element.GetAttribute("class"));
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
        }
    }   
}
