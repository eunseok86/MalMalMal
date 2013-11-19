namespace NewsCrawler
{
    partial class frmMain
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.rtxtResult = new System.Windows.Forms.RichTextBox();
            this.btnGetNewsList = new System.Windows.Forms.Button();
            this.txtDate = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.monthCalendar1 = new System.Windows.Forms.MonthCalendar();
            this.wb1 = new System.Windows.Forms.WebBrowser();
            this.bw1 = new System.ComponentModel.BackgroundWorker();
            this.txtProgress = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.txtProgress);
            this.splitContainer1.Panel1.Controls.Add(this.rtxtResult);
            this.splitContainer1.Panel1.Controls.Add(this.btnGetNewsList);
            this.splitContainer1.Panel1.Controls.Add(this.txtDate);
            this.splitContainer1.Panel1.Controls.Add(this.label1);
            this.splitContainer1.Panel1.Controls.Add(this.monthCalendar1);
            this.splitContainer1.Panel1MinSize = 300;
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.wb1);
            this.splitContainer1.Size = new System.Drawing.Size(978, 637);
            this.splitContainer1.SplitterDistance = 300;
            this.splitContainer1.TabIndex = 6;
            // 
            // rtxtResult
            // 
            this.rtxtResult.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.rtxtResult.Location = new System.Drawing.Point(254, 0);
            this.rtxtResult.Name = "rtxtResult";
            this.rtxtResult.Size = new System.Drawing.Size(720, 293);
            this.rtxtResult.TabIndex = 8;
            this.rtxtResult.Text = "";
            // 
            // btnGetNewsList
            // 
            this.btnGetNewsList.Location = new System.Drawing.Point(0, 249);
            this.btnGetNewsList.Name = "btnGetNewsList";
            this.btnGetNewsList.Size = new System.Drawing.Size(203, 48);
            this.btnGetNewsList.TabIndex = 7;
            this.btnGetNewsList.Text = "Get NewsList";
            this.btnGetNewsList.UseVisualStyleBackColor = true;
            this.btnGetNewsList.Click += new System.EventHandler(this.btnGetNewsList_Click);
            // 
            // txtDate
            // 
            this.txtDate.Location = new System.Drawing.Point(99, 216);
            this.txtDate.Name = "txtDate";
            this.txtDate.Size = new System.Drawing.Size(149, 25);
            this.txtDate.TabIndex = 6;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(-3, 216);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 15);
            this.label1.TabIndex = 5;
            this.label1.Text = "selected date";
            // 
            // monthCalendar1
            // 
            this.monthCalendar1.Location = new System.Drawing.Point(0, 0);
            this.monthCalendar1.Name = "monthCalendar1";
            this.monthCalendar1.TabIndex = 4;
            this.monthCalendar1.DateChanged += new System.Windows.Forms.DateRangeEventHandler(this.monthCalendar1_DateChanged);
            // 
            // wb1
            // 
            this.wb1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.wb1.Location = new System.Drawing.Point(0, 5);
            this.wb1.MinimumSize = new System.Drawing.Size(20, 20);
            this.wb1.Name = "wb1";
            this.wb1.Size = new System.Drawing.Size(974, 319);
            this.wb1.TabIndex = 6;
            this.wb1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.wb1_DocumentCompleted);
            // 
            // bw1
            // 
            this.bw1.WorkerReportsProgress = true;
            this.bw1.WorkerSupportsCancellation = true;
            this.bw1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bw1_DoWork);
            this.bw1.ProgressChanged += new System.ComponentModel.ProgressChangedEventHandler(this.bw1_ProgressChanged);
            this.bw1.RunWorkerCompleted += new System.ComponentModel.RunWorkerCompletedEventHandler(this.bw1_RunWorkerCompleted);
            // 
            // txtProgress
            // 
            this.txtProgress.Location = new System.Drawing.Point(209, 249);
            this.txtProgress.Name = "txtProgress";
            this.txtProgress.Size = new System.Drawing.Size(39, 25);
            this.txtProgress.TabIndex = 9;
            this.txtProgress.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // frmMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(978, 637);
            this.Controls.Add(this.splitContainer1);
            this.Name = "frmMain";
            this.Text = "News Crawler";
            this.Load += new System.EventHandler(this.frmMain_Load);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.RichTextBox rtxtResult;
        private System.Windows.Forms.Button btnGetNewsList;
        private System.Windows.Forms.TextBox txtDate;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.MonthCalendar monthCalendar1;
        private System.Windows.Forms.WebBrowser wb1;
        private System.ComponentModel.BackgroundWorker bw1;
        private System.Windows.Forms.TextBox txtProgress;

    }
}