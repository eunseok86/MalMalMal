package com.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;




import java.util.TimerTask;

import com.Entity.IssueData;
import com.Entity.NewsMorphemeSentence;
import com.Entity.NewsSentence;
import com.NewsDBWork.NewsHan_Biz;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;




import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class Han_Scheduler extends JPanel implements ActionListener, PropertyChangeListener {

	private JProgressBar progressBar;
	private static JButton startButton;
	private JTextArea taskOutput, scheduleOutput;
	private Task task;
	private JLabel timeLabel;
	

	class Task extends SwingWorker<Void, Void> {
		private NewsHan_Biz newsIBiz;
		/*
		 *Main task. Executed in background thread.
		 */

		@Override
		public Void doInBackground() {
			long startTime = System.currentTimeMillis();
			NewsHan_Biz newsBiz = new NewsHan_Biz();
			ArrayList<NewsSentence> strArr = newsBiz.selectNewsCheck();
			
			
			if(strArr.size()!=0){
				scheduleOutput.append(String.format("스케쥴 시작"+new Date().toString()+"\n"));
				taskOutput.append(String.format(strArr.size()+"문장의 형태소를 분석중입니다.\n"));
				//taskOutput.append(String.format("사전을 로드중입니다.\n"));
				//Workflow workflow = new Workflow("res");
				Workflow workflow = new Workflow("res");
				
				try {
					workflow.appendPlainTextProcessor(new InformalSentenceFilter(), null);
					workflow.setMorphAnalyzer(new ChartMorphAnalyzer(), "conf/plugin/MajorPlugin/MorphAnalyzer/ChartMorphAnalyzer.json");
					workflow.appendMorphemeProcessor(new UnknownProcessor(), null);
					workflow.setPosTagger(new HMMTagger(), "conf/plugin/MajorPlugin/PosTagger/HmmPosTagger.json");
					workflow.activateWorkflow(true);

					//taskOutput.append(String.format("이슈어를 로드 중입니다.\n"));

					//Initialize progress property.
					setProgress(0);

					//새로 형태소 분석 해야 할 뉴스 데이터들 확인
					
					int size = (strArr.size()-1);
					ArrayList<NewsMorphemeSentence> arr = new ArrayList<>();
					for(int i=0; i < strArr.size(); i++){

						String document = strArr.get(i).getSentence();
						//System.out.println(document);
						workflow.analyze(document);
						LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));

						NewsMorphemeSentence result = printHanResult(resultList);
						result.setNewsId(strArr.get(i).getNewsId());
						result.setSentenceId(strArr.get(i).getSentenceId());

						//newsIBiz = new NewsHan_Biz();
						//newsIBiz.insertNewsSentence(strArr.get(i).getNewsId(),strArr.get(i).getSentenceId(),
						//	result.getMorpheme(),result.getExp());
						arr.add(result);

						setProgress(100*i/size);

					}
					long endTime = System.currentTimeMillis();
					long lTime = endTime - startTime;
					scheduleOutput.append(String.format("소요시간 : " + lTime/1000/60 + "(m)\n"));

					scheduleOutput.append(String.format(strArr.size()+"문장의 분석결과를 입력중입니다.\n"));

					long startTime2 = System.currentTimeMillis();

					newsIBiz = new NewsHan_Biz();
					newsIBiz.insertNewsSentence(arr);
					scheduleOutput.append(String.format(strArr.size()+"문장의 분석이 끝났습니다.\n"));
					
					
					NewsHan_Biz biz = new NewsHan_Biz();
					ArrayList<IssueData> issuedata = biz.selectIssueCheck();
					if(issuedata.size()!=0){
						scheduleOutput.append("###처리된 이슈가 있습니다.###\n");
						for(int i=0; i < issuedata.size(); i++){
							//System.out.println(issuedata.size());
							String document = issuedata.get(i).getIssueSentence();
							//System.out.println(document);
							workflow.analyze(document);
							LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));

							issuedata.get(i).setIssueSentence(printHanResult2(resultList)); 
							

						}
						NewsHan_Biz biz2 = new NewsHan_Biz();
						biz2.insertIssusentence(issuedata);
						
					}
					
					
					long endTime2 = System.currentTimeMillis();
					long lTime2 = endTime2 - startTime2;
					
					scheduleOutput.append(String.format("소요시간 : " + lTime2/1000/60 + "(m)\n"));
					scheduleOutput.append(String.format("스케쥴 종료"+new Date().toString()+"\n"));

					taskOutput.append(String.format("TIME : " + lTime/1000/60 + "(m)\n"));
					taskOutput.append(String.format("작업종료, 대기중\n\n\n"	));

					//jobScheduler.schedule(job, 1000);
					//Thread.sleep(2000);

					//jobScheduler.cancel();
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				workflow.close();
			}
			
			

			
			
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			startButton.setEnabled(true);
			setCursor(null); //turn off the wait cursor

		}
	}


	public Han_Scheduler() {

		super(new BorderLayout());

		//UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);
		startButton.setSize(100, 100);

		progressBar = new JProgressBar(0, 100);
		//progressBar.setSize(250, 10);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);



		taskOutput = new JTextArea(50, 40);
		taskOutput.setMargin(new Insets(5,5,5,5));
		taskOutput.setEditable(false);


		scheduleOutput = new JTextArea(50, 40);
		scheduleOutput.setMargin(new Insets(5,5,5,5));
		scheduleOutput.setEditable(false);



		JPanel panel = new JPanel(new BorderLayout());
		timeLabel = new JLabel("시간");

		timeLabel.setBackground(Color.CYAN);


		panel.add(startButton,BorderLayout.WEST);
		panel.add(progressBar);
		panel.add(timeLabel,BorderLayout.EAST);

		//panel.setSize(300, 500);

		//panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.EAST);
		add(new JScrollPane(scheduleOutput), BorderLayout.WEST);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


		//Timer scheduler = new Timer(10000,this);
		//scheduler.start();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//if(startButton.d)
		if(getCursor()!=Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)){
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if ("progress" == e.getPropertyName()) {
			int progress = (Integer) e.getNewValue();
			progressBar.setValue(progress);
			//taskOutput.append(String.format(
			//	"Completed %d%% of task.\n", task.getProgress()));
		} 

	}

	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("Han_Scheduler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		JComponent newContentPane = new Han_Scheduler();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
		
		
		Timer planTimer = new Timer(30000, new ActionListener() {   
		    @Override
		    public void actionPerformed( ActionEvent e ) {
		    	startButton.doClick();
		    }
		});
		planTimer.setRepeats(true);//Set repeatable.
		planTimer.start();
	}




	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
				
			}
		});


		/*


		 */
	}


	public static void callHan_Scheduler(){

	}


	public static NewsMorphemeSentence printHanResult(LinkedList<Sentence> resultList){
		String resExpr ="";
		String resSentencer ="";
		NewsMorphemeSentence result;
		for (Sentence s : resultList) {
			Eojeol[] eojeolArray = s.getEojeols();
			String[] plainEojeols = s.getPlainEojeols();
			for (int i = 0; i < eojeolArray.length; i++) {
				if (eojeolArray[i].length > 0) {
					String[] morphemes = eojeolArray[i].getMorphemes();
					String[] tags = eojeolArray[i].getTags();
					String plainEojeol= plainEojeols[i];
					for (int j = 0; j < morphemes.length; j++) {
						resSentencer+=morphemes[j]+"/"+tags[j];
						if(j != morphemes.length-1){
							resSentencer+="+";
						}
					}
					resExpr+=plainEojeol+" ";
					resSentencer+=" ";
				}
			}
			result = new NewsMorphemeSentence(resExpr,resSentencer);

			return result;
		}
		return null;
	}
	
	public static String printHanResult2(LinkedList<Sentence> resultList){
		String resExpr ="";
		String resSentencer ="";
		NewsMorphemeSentence result;
		for (Sentence s : resultList) {
			Eojeol[] eojeolArray = s.getEojeols();
			String[] plainEojeols = s.getPlainEojeols();
			for (int i = 0; i < eojeolArray.length; i++) {
				if (eojeolArray[i].length > 0) {
					String[] morphemes = eojeolArray[i].getMorphemes();
					String[] tags = eojeolArray[i].getTags();
					String plainEojeol= plainEojeols[i];
					for (int j = 0; j < morphemes.length; j++) {
						resSentencer+=morphemes[j]+"/"+tags[j];
						if(j != morphemes.length-1){
							resSentencer+="+";
						}
					}
					resExpr+=plainEojeol+" ";
					resSentencer+=" ";
				}
			}
			

			return resSentencer;
		}
		return "";
	}
	
	
	
	
	

}	










