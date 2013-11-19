import java.util.ArrayList;
import java.util.LinkedList;


import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;

import com.Entity.NewsMorphemeSentence;
import com.Entity.NewsSentence;
import com.NewsDBWork.NewsHan_Biz;
import com.Sequence.knowledgeExtSeq;



public class debugNewsTool {

	public static void main(String[] args) {
		int news_idx = 476692;
		NewsHan_Biz newsBiz = new NewsHan_Biz();
		ArrayList<NewsSentence> strArr = newsBiz.selectNewsCheck(news_idx);
		if(strArr.size()!=0){
			Workflow workflow = new Workflow("res");
			ArrayList<com.DB.Entity.NewsSentence> data = new ArrayList<com.DB.Entity.NewsSentence>();
			
			
			try {
				workflow.appendPlainTextProcessor(new InformalSentenceFilter(), null);
				workflow.setMorphAnalyzer(new ChartMorphAnalyzer(), "conf/plugin/MajorPlugin/MorphAnalyzer/ChartMorphAnalyzer.json");
				workflow.appendMorphemeProcessor(new UnknownProcessor(), null);
				workflow.setPosTagger(new HMMTagger(), "conf/plugin/MajorPlugin/PosTagger/HmmPosTagger.json");
				workflow.activateWorkflow(true);

				
				ArrayList<NewsMorphemeSentence> arr = new ArrayList<>();
				for(int i=0; i < strArr.size(); i++){
					com.DB.Entity.NewsSentence newsData = new com.DB.Entity.NewsSentence();
					String document = strArr.get(i).getSentence();
					
					workflow.analyze(document);
					LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));

					NewsMorphemeSentence result = printHanResult(resultList);
					result.setNewsId(strArr.get(i).getNewsId());
					result.setSentenceId(strArr.get(i).getSentenceId());
					
					//지식추출용 데이터
					newsData.setNewsId(result.getNewsId());
					newsData.setSentenceId(result.getSentenceId());
					newsData.setSentence(result.getMorpheme());
					newsData.setExp(result.getExp());
					
					
					data.add(newsData);
					arr.add(result);
				}
				
				NewsHan_Biz newsIBiz = new NewsHan_Biz();
				newsIBiz.insertNewsSentence(arr);
				
				
				knowledgeExtSeq keE = new knowledgeExtSeq();
				//db호출
				keE.callDebugDB(data);
				//
				
				keE.callCandidatesExtractingSeq(true);
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			workflow.close();
			

		}

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
}
