namespace NewsCrawler
{
    partial class frmCal
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
            this.mc1 = new System.Windows.Forms.MonthCalendar();
            this.mc2 = new System.Windows.Forms.MonthCalendar();
            this.txtDate1 = new System.Windows.Forms.TextBox();
            this.txtDate2 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.btnGetNewsList = new System.Windows.Forms.Button();
            this.dgv1 = new System.Windows.Forms.DataGridView();
            this.Column1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column2 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.bw1 = new System.ComponentModel.BackgroundWorker();
            ((System.ComponentModel.ISupportInitialize)(this.dgv1)).BeginInit();
            this.SuspendLayout();
            // 
            // mc1
            // 
            this.mc1.Location = new System.Drawing.Point(18, 18);
            this.mc1.Name = "mc1";
            this.mc1.TabIndex = 5;
            this.mc1.DateChanged += new System.Windows.Forms.DateRangeEventHandler(this.mc1_DateChanged);
            // 
            // mc2
            // 
            this.mc2.Location = new System.Drawing.Point(284, 18);
            this.mc2.Name = "mc2";
            this.mc2.TabIndex = 6;
            this.mc2.DateChanged += new System.Windows.Forms.DateRangeEventHandler(this.mc2_DateChanged);
            // 
            // txtDate1
            // 
            this.txtDate1.Location = new System.Drawing.Point(18, 237);
            this.txtDate1.Name = "txtDate1";
            this.txtDate1.Size = new System.Drawing.Size(241, 25);
            this.txtDate1.TabIndex = 7;
            // 
            // txtDate2
            // 
            this.txtDate2.Location = new System.Drawing.Point(289, 237);
            this.txtDate2.Name = "txtDate2";
            this.txtDate2.Size = new System.Drawing.Size(243, 25);
            this.txtDate2.TabIndex = 8;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(265, 240);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(18, 15);
            this.label1.TabIndex = 9;
            this.label1.Text = "~";
            // 
            // btnGetNewsList
            // 
            this.btnGetNewsList.Location = new System.Drawing.Point(544, 18);
            this.btnGetNewsList.Name = "btnGetNewsList";
            this.btnGetNewsList.Size = new System.Drawing.Size(190, 237);
            this.btnGetNewsList.TabIndex = 10;
            this.btnGetNewsList.Text = "Get NewsList";
            this.btnGetNewsList.UseVisualStyleBackColor = true;
            this.btnGetNewsList.Click += new System.EventHandler(this.btnGetNewsList_Click);
            // 
            // dgv1
            // 
            this.dgv1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.dgv1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgv1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Column1,
            this.Column2});
            this.dgv1.Location = new System.Drawing.Point(19, 282);
            this.dgv1.Name = "dgv1";
            this.dgv1.RowTemplate.Height = 27;
            this.dgv1.Size = new System.Drawing.Size(716, 405);
            this.dgv1.TabIndex = 11;
            // 
            // Column1
            // 
            this.Column1.HeaderText = "Column1";
            this.Column1.Name = "Column1";
            this.Column1.Width = 300;
            // 
            // Column2
            // 
            this.Column2.HeaderText = "Column2";
            this.Column2.Name = "Column2";
            this.Column2.Width = 300;
            // 
            // bw1
            // 
            this.bw1.WorkerReportsProgress = true;
            this.bw1.WorkerSupportsCancellation = true;
            this.bw1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bw1_DoWork);
            // 
            // frmCal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(747, 699);
            this.Controls.Add(this.dgv1);
            this.Controls.Add(this.btnGetNewsList);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtDate2);
            this.Controls.Add(this.txtDate1);
            this.Controls.Add(this.mc2);
            this.Controls.Add(this.mc1);
            this.Name = "frmCal";
            this.Text = "frmCal";
            ((System.ComponentModel.ISupportInitialize)(this.dgv1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MonthCalendar mc1;
        private System.Windows.Forms.MonthCalendar mc2;
        private System.Windows.Forms.TextBox txtDate1;
        private System.Windows.Forms.TextBox txtDate2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnGetNewsList;
        private System.Windows.Forms.DataGridView dgv1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column2;
        private System.ComponentModel.BackgroundWorker bw1;
    }
}