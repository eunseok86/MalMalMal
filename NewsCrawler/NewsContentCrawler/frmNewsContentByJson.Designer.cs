namespace NewsContentCrawler
{
    partial class frmNewsContentByJson
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
            this.btnGetNewsList = new System.Windows.Forms.Button();
            this.btnSaveNewsOnDB = new System.Windows.Forms.Button();
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
            this.dgv1.Location = new System.Drawing.Point(6, 6);
            this.dgv1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.dgv1.Name = "dgv1";
            this.dgv1.RowTemplate.Height = 27;
            this.dgv1.Size = new System.Drawing.Size(845, 528);
            this.dgv1.TabIndex = 26;
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
            // btnGetNewsList
            // 
            this.btnGetNewsList.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnGetNewsList.Location = new System.Drawing.Point(857, 6);
            this.btnGetNewsList.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnGetNewsList.Name = "btnGetNewsList";
            this.btnGetNewsList.Size = new System.Drawing.Size(166, 67);
            this.btnGetNewsList.TabIndex = 27;
            this.btnGetNewsList.Text = "Get NewsList";
            this.btnGetNewsList.UseVisualStyleBackColor = true;
            this.btnGetNewsList.Click += new System.EventHandler(this.btnGetNewsList_Click);
            // 
            // btnSaveNewsOnDB
            // 
            this.btnSaveNewsOnDB.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnSaveNewsOnDB.Location = new System.Drawing.Point(857, 77);
            this.btnSaveNewsOnDB.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnSaveNewsOnDB.Name = "btnSaveNewsOnDB";
            this.btnSaveNewsOnDB.Size = new System.Drawing.Size(166, 67);
            this.btnSaveNewsOnDB.TabIndex = 28;
            this.btnSaveNewsOnDB.Text = "Save News On DB";
            this.btnSaveNewsOnDB.UseVisualStyleBackColor = true;
            this.btnSaveNewsOnDB.Click += new System.EventHandler(this.btnSaveNewsOnDB_Click);
            // 
            // frmNewsContentByJson
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1026, 539);
            this.Controls.Add(this.btnSaveNewsOnDB);
            this.Controls.Add(this.btnGetNewsList);
            this.Controls.Add(this.dgv1);
            this.Name = "frmNewsContentByJson";
            this.Text = "NewsContent By Json";
            this.Load += new System.EventHandler(this.frmNewsContentByJson_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgv1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgv1;
        private System.Windows.Forms.DataGridViewTextBoxColumn newsId;
        private System.Windows.Forms.DataGridViewTextBoxColumn title;
        private System.Windows.Forms.DataGridViewTextBoxColumn cpKorName;
        private System.Windows.Forms.DataGridViewTextBoxColumn cpEngName;
        private System.Windows.Forms.DataGridViewTextBoxColumn regDt;
        private System.Windows.Forms.DataGridViewTextBoxColumn modiDt;
        private System.Windows.Forms.DataGridViewTextBoxColumn status;
        private System.Windows.Forms.Button btnGetNewsList;
        private System.Windows.Forms.Button btnSaveNewsOnDB;
    }
}