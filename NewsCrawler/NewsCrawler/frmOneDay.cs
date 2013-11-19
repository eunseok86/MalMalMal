using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NewsCrawler
{
    public partial class frmOneDay : Form
    {
        public frmOneDay()
        {
            InitializeComponent();
        }

        public int idx = 0;

        public void oneday()
        {

        }

        public void setDay(string day)
        {
            this.txtDate.Text = day;
        }

        public void run()
        {
            //bw1.RunWorkerAsync();
            run2();
        }

        private void bw1_DoWork(object sender, DoWorkEventArgs e)
        {
            run2();
        }

        private void run2()
        {
            int i = 0;

            for (i = 1; i <= 100; i++)      // idx
            {
                string url = @"http://media.daum.net/politics/all/?regdate=" + txtDate.Text.Replace("-", "") + "#page=" + i.ToString() + "&type=title";

                frmOnePage childForm = new frmOnePage();
                childForm.MdiParent = this.ParentForm;
                childForm.Text = "OnePage 창 ";
                childForm.Show();
                childForm.run(url);

                System.Threading.Thread.Sleep(1000);
            }
        }
    }
}
