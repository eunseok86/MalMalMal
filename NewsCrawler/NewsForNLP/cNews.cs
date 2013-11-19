using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Windows.Forms;
using System.Data.SqlClient;

namespace NewsForNLP
{
    public class cNews
    {
        public string newsId = "";
        public string title = "";
        public string cpKorName = "";
        public string cpEngName = "";
        public string regDt = "";
        public string modiDt = "";
        public string status = "";
        public string content = "";

        private static string ConnectionString = "";

        public cNews(string newsId, string content)
        {
            this.newsId = newsId;
            this.content = content;
        }

        public cNews(string newsId, string title, string cpKorName, string cpEngName, string regDt, string modiDt, string status)
        {
            this.newsId = newsId;
            this.title = title;
            this.cpKorName = cpKorName;
            this.cpEngName = cpEngName;
            this.regDt = regDt;
            this.modiDt = modiDt;
            this.status = status;
            this.content = "";
        }

        public cNews(string newsId, string title, string cpKorName, string cpEngName, string regDt, string modiDt, string status, string content)
        {
            this.newsId = newsId;
            this.title = title;
            this.cpKorName = cpKorName;
            this.cpEngName = cpEngName;
            this.regDt = regDt;
            this.modiDt = modiDt;
            this.status = status;
            this.content = content;
        }

        public cNews(ref DataGridViewRow dgvr)
        {
            try
            {
                this.newsId = dgvr.Cells["newsId"].Value.ToString();
                this.title = dgvr.Cells["title"].Value.ToString();
                this.cpKorName = dgvr.Cells["cpKorName"].Value.ToString();
                this.cpEngName = dgvr.Cells["cpEngName"].Value.ToString();
                this.regDt = dgvr.Cells["regDt"].Value.ToString();
                this.modiDt = dgvr.Cells["modiDt"].Value.ToString();
                this.status = dgvr.Cells["status"].Value.ToString();
            }
            catch (Exception)
            {
            }
        }

        public static bool GetNewsNoContent(ref DataGridView dgv)
        {
            bool result = false;

            try
            {
                dgv.Rows.Clear();

                LinkedList<cNews> newslist = null;
                if (!SelectDatas(ref newslist))
                {
                    result = true;
                    throw new Exception("[GetNewsNoContent] 가져올 데이터가 없거나 가져올 수 없었습니다.");                    
                }

                foreach (cNews n in newslist)
                {
                    dgv.Rows.Add(n.getStrings());
                    dgv.Rows[dgv.Rows.Count - 2].HeaderCell.Value = (dgv.Rows.Count - 1).ToString();
                }
                result = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return result;
        }

        public string[] getStrings()
        {
            return new string[] {newsId, title, cpKorName, cpEngName, regDt, modiDt, status};
        }

        private static bool SelectDatas(ref LinkedList<cNews> newslist)
        {
            bool result = false;

            try
            {
                SqlDataReader sdr = null;
                SqlConnection con = new SqlConnection();
                con.ConnectionString = ConnectionString;

                string strSql = "sp_GetNewsNoContent";
                SqlCommand cmd = new SqlCommand(strSql, con);
                cmd.CommandType = System.Data.CommandType.StoredProcedure;

                //cmd.Parameters.AddWithValue("@nothing", this.nothing);
                
                con.Open();
                sdr = cmd.ExecuteReader();

                if (!sdr.HasRows)
                {
                    throw new Exception("[GetNewsNoContent] 가져올 데이터가 없거나 가져올 수 없었습니다.");
                }

                newslist = new LinkedList<cNews>();

                while (sdr.Read())
                {
                    string newsId = NoNull(ref sdr, 0);
                    string title = NoNull(ref sdr, 1);
                    string cpKorName = NoNull(ref sdr, 2);
                    string cpEngName = NoNull(ref sdr, 3);
                    string regDt = NoNull(ref sdr, 4);
                    string modiDt = NoNull(ref sdr, 5);
                    string status = NoNull(ref sdr, 6);
                    string content = NoNull(ref sdr, 7);    // 미사용

                    cNews n = new cNews(newsId, title, cpKorName, cpEngName, regDt, modiDt, status, content);

                    newslist.AddLast(n);
                }                

                con.Close();

                result = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return result;
        }

        private static string NoNull(ref SqlDataReader sdr, int i)
        {
            if (!sdr.IsDBNull(i))
            {
                return sdr.GetString(i);
            }
            else
            {
                return "";
            }            
        }

        public bool UpdateNewsContent()
        {
            bool result = false;

            if (this.newsId == "" || this.content == null || this.content == "")
                return result;

            try
            {
                // db에 insert 한다.
                SqlConnection con = new SqlConnection();
                con.ConnectionString = ConnectionString;

                string strSql = "UpdateNewsContent";
                SqlCommand cmd = new SqlCommand(strSql, con);
                cmd.CommandType = System.Data.CommandType.StoredProcedure;

                cmd.Parameters.AddWithValue("@newsId", this.newsId);
                cmd.Parameters.AddWithValue("@content", this.content);

                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();

                result = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return result;
        }

        public bool InsertData()
        {
            bool result = false;

            if (this.newsId == "")
                return result;

            try
            {
                // db에 insert 한다.
                SqlConnection con = new SqlConnection();
                con.ConnectionString = ConnectionString;
                
                string strSql = "sp_AddNews";
                SqlCommand cmd = new SqlCommand(strSql, con);
                cmd.CommandType = System.Data.CommandType.StoredProcedure;
                
                cmd.Parameters.AddWithValue("@newsId", this.newsId);
                cmd.Parameters.AddWithValue("@title", this.title);
                cmd.Parameters.AddWithValue("@cpKorName", this.cpKorName);
                cmd.Parameters.AddWithValue("@cpEngName", this.cpEngName);
                cmd.Parameters.AddWithValue("@regDt", this.regDt);
                cmd.Parameters.AddWithValue("@modiDt", this.modiDt);
                cmd.Parameters.AddWithValue("@status", this.status);

                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();

                result = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            return result;
        }
    }
}
