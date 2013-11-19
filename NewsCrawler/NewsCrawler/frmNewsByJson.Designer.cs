namespace NewsCrawler
{
    partial class frmNewsByJson
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
            this.dgv1 = new System.Windows.Forms.DataGridView();
            this.newsId = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.title = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.cpKorName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.cpEngName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.regDt = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.modiDt = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.status = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.bw1 = new System.ComponentModel.BackgroundWorker();
            this.btnGetNewsList = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.txtDate2 = new System.Windows.Forms.TextBox();
            this.txtDate1 = new System.Windows.Forms.TextBox();
            this.mc2 = new System.Windows.Forms.MonthCalendar();
            this.mc1 = new System.Windows.Forms.MonthCalendar();
            this.btnSaveNewsOnDB = new System.Windows.Forms.Button();
            this.btnListClear = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dgv1)).BeginInit();
            this.SuspendLayout();
            // 
            // dgv1
            // 
            this.dgv1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.dgv1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgv1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.newsId,
            this.title,
            this.cpKorName,
            this.cpEngName,
            this.regDt,
            this.modiDt,
            this.status});
            this.dgv1.Location = new System.Drawing.Point(7, 206);
            this.dgv1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.dgv1.Name = "dgv1";
            this.dgv1.RowTemplate.Height = 27;
            this.dgv1.Size = new System.Drawing.Size(626, 255);
            this.dgv1.TabIndex = 25;
            // 
            // newsId
            // 
            this.newsId.HeaderText = "newsId";
            this.newsId.Name = "newsId";
            this.newsId.Width = 300;
            // 
            // title
            // 
            this.title.HeaderText = "title";
            this.title.Name = "title";
            this.title.Width = 300;
            // 
            // cpKorName
            // 
            this.cpKorName.HeaderText = "cpKorName";
            this.cpKorName.Name = "cpKorName";
            // 
            // cpEngName
            // 
            this.cpEngName.HeaderText = "cpEngName";
            this.cpEngName.Name = "cpEngName";
            // 
            // regDt
            // 
            this.regDt.HeaderText = "regDt";
            this.regDt.Name = "regDt";
            // 
            // modiDt
            // 
            this.modiDt.HeaderText = "modiDt";
            this.modiDt.Name = "modiDt";
            // 
            // status
            // 
            this.status.HeaderText = "status";
            this.status.Name = "status";
            // 
            // bw1
            // 
            this.bw1.WorkerReportsProgress = true;
            this.bw1.WorkerSupportsCancellation = true;
            // 
            // btnGetNewsList
            // 
            this.btnGetNewsList.Location = new System.Drawing.Point(466, 6);
            this.btnGetNewsList.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnGetNewsList.Name = "btnGetNewsList";
            this.btnGetNewsList.Size = new System.Drawing.Size(166, 67);
            this.btnGetNewsList.TabIndex = 24;
            this.btnGetNewsList.Text = "Get NewsList";
            this.btnGetNewsList.UseVisualStyleBackColor = true;
            this.btnGetNewsList.Click += new System.EventHandler(this.btnGetNewsList_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(222, 184);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(14, 12);
            this.label1.TabIndex = 23;
            this.label1.Text = "~";
            // 
            // txtDate2
            // 
            this.txtDate2.Location = new System.Drawing.Point(243, 182);
            this.txtDate2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtDate2.Name = "txtDate2";
            this.txtDate2.Size = new System.Drawing.Size(213, 21);
            this.txtDate2.TabIndex = 22;
            // 
            // txtDate1
            // 
            this.txtDate1.Location = new System.Drawing.Point(6, 182);
            this.txtDate1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtDate1.Name = "txtDate1";
            this.txtDate1.Size = new System.Drawing.Size(211, 21);
            this.txtDate1.TabIndex = 21;
            // 
            // mc2
            // 
            this.mc2.Location = new System.Drawing.Point(239, 6);
            this.mc2.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.mc2.Name = "mc2";
            this.mc2.TabIndex = 20;
            this.mc2.DateChanged += new System.Windows.Forms.DateRangeEventHandler(this.mc2_DateChanged);
            // 
            // mc1
            // 
            this.mc1.Location = new System.Drawing.Point(6, 6);
            this.mc1.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.mc1.Name = "mc1";
            this.mc1.TabIndex = 19;
            this.mc1.DateChanged += new System.Windows.Forms.DateRangeEventHandler(this.mc1_DateChanged);
            // 
            // btnSaveNewsOnDB
            // 
            this.btnSaveNewsOnDB.Location = new System.Drawing.Point(466, 78);
            this.btnSaveNewsOnDB.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnSaveNewsOnDB.Name = "btnSaveNewsOnDB";
            this.btnSaveNewsOnDB.Size = new System.Drawing.Size(166, 67);
            this.btnSaveNewsOnDB.TabIndex = 26;
            this.btnSaveNewsOnDB.Text = "Save News On DB";
            this.btnSaveNewsOnDB.UseVisualStyleBackColor = true;
            this.btnSaveNewsOnDB.Click += new System.EventHandler(this.btnSaveNewsOnDB_Click);
            // 
            // btnListClear
            // 
            this.btnListClear.Location = new System.Drawing.Point(466, 149);
            this.btnListClear.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnListClear.Name = "btnListClear";
            this.btnListClear.Size = new System.Drawing.Size(166, 53);
            this.btnListClear.TabIndex = 27;
            this.btnListClear.Text = "List Clear";
            this.btnListClear.UseVisualStyleBackColor = true;
            this.btnListClear.Click += new System.EventHandler(this.btnListClear_Click);
            // 
            // frmNewsByJson
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(639, 471);
            this.Controls.Add(this.btnListClear);
            this.Controls.Add(this.btnSaveNewsOnDB);
            this.Controls.Add(this.dgv1);
            this.Controls.Add(this.btnGetNewsList);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtDate2);
            this.Controls.Add(this.txtDate1);
            this.Controls.Add(this.mc2);
            this.Controls.Add(this.mc1);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "frmNewsByJson";
            this.Text = "News By Json";
            this.Load += new System.EventHandler(this.frmNewsByJson_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgv1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dgv1;
        private System.ComponentModel.BackgroundWorker bw1;
        private System.Windows.Forms.Button btnGetNewsList;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtDate2;
        private System.Windows.Forms.TextBox txtDate1;
        private System.Windows.Forms.MonthCalendar mc2;
        private System.Windows.Forms.MonthCalendar mc1;
        private System.Windows.Forms.Button btnSaveNewsOnDB;
        private System.Windows.Forms.DataGridViewTextBoxColumn newsId;
        private System.Windows.Forms.DataGridViewTextBoxColumn title;
        private System.Windows.Forms.DataGridViewTextBoxColumn cpKorName;
        private System.Windows.Forms.DataGridViewTextBoxColumn cpEngName;
        private System.Windows.Forms.DataGridViewTextBoxColumn regDt;
        private System.Windows.Forms.DataGridViewTextBoxColumn modiDt;
        private System.Windows.Forms.DataGridViewTextBoxColumn status;
        private System.Windows.Forms.Button btnListClear;
    }
}