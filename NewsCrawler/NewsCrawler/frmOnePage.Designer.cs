namespace NewsCrawler
{
    partial class frmOnePage
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
            this.wb1 = new System.Windows.Forms.WebBrowser();
            this.rtxtResult = new System.Windows.Forms.RichTextBox();
            this.bw1 = new System.ComponentModel.BackgroundWorker();
            this.SuspendLayout();
            // 
            // wb1
            // 
            this.wb1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.wb1.Location = new System.Drawing.Point(12, 311);
            this.wb1.MinimumSize = new System.Drawing.Size(20, 20);
            this.wb1.Name = "wb1";
            this.wb1.Size = new System.Drawing.Size(924, 391);
            this.wb1.TabIndex = 7;
            this.wb1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.wb1_DocumentCompleted);
            // 
            // rtxtResult
            // 
            this.rtxtResult.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.rtxtResult.Location = new System.Drawing.Point(12, 12);
            this.rtxtResult.Name = "rtxtResult";
            this.rtxtResult.Size = new System.Drawing.Size(924, 293);
            this.rtxtResult.TabIndex = 9;
            this.rtxtResult.Text = "";
            // 
            // bw1
            // 
            this.bw1.WorkerReportsProgress = true;
            this.bw1.WorkerSupportsCancellation = true;
            this.bw1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.bw1_DoWork);
            // 
            // frmOnePage
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(948, 714);
            this.Controls.Add(this.rtxtResult);
            this.Controls.Add(this.wb1);
            this.Name = "frmOnePage";
            this.Text = "frmOnePage";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.WebBrowser wb1;
        private System.Windows.Forms.RichTextBox rtxtResult;
        private System.ComponentModel.BackgroundWorker bw1;
    }
}