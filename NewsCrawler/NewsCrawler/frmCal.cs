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
    public partial class frmCal : Form
    {
        public frmCal()
        {
            InitializeComponent();
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
            //bw1.RunWorkerAsync();

            int day_s = 0;
            int day_e = 0;

            day_s = int.Parse(txtDate1.Text.Substring(8, 2));
            day_e = int.Parse(txtDate2.Text.Substring(8, 2));

            for (int i = day_s; i <= day_e; i++)
            {
                // 일별 호출
                frmOneDay childForm = new frmOneDay();
                childForm.MdiParent = this.ParentForm;
                childForm.Text = "Cal 창 ";
                childForm.Show();
                childForm.idx = i;
                childForm.setDay(txtDate1.Text.Substring(0, 8) + (i.ToString().Length == 1 ? "0" : "") + i.ToString());
                childForm.run();
            }
        }

        private void bw1_DoWork(object sender, DoWorkEventArgs e)
        {
            
        }
    }
}
