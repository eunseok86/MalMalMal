using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;

namespace NewsCrawlerConsole
{
    class Program
    {
        static void Main(string[] args)
        {
            /*
            conMain cm = new conMain();
            cm.run();
            */

            // 요청을 보내는 URI
            string strUri = "http://media.daum.net/politics/all/?regdate=20130813#page=1&type=title";
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(strUri);
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();

            Console.WriteLine("Content length is {0}", response.ContentLength);
            Console.WriteLine("Content type is {0}", response.ContentType);

            // Get the stream associated with the response.
            Stream receiveStream = response.GetResponseStream();

            // Pipes the stream to a higher level stream reader with the required encoding format. 
            StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8);

            Console.WriteLine("Response stream received.");
            Console.WriteLine(readStream.ReadToEnd());
            response.Close();
            readStream.Close();
        }
    }
}
