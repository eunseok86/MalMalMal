package com.common;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class oesLog {

	    //public static String m_PathName = Util.LOG_FILE_PATH;  
	    
	    public static String m_FileName = "log";     
	    private static FileWriter objfile = null;
	    /***************************
	     * Logging Method          *  
	     ***************************/
	    public static void TraceLog(String folder, String log)
	    {
	        int i                 = 0;
	        String stPath         = "";
	        String stFileName     = "";
	        
	        String m_PathName = "c://oesProject//"+folder+"//";  


	        stPath     = m_PathName;
	        stFileName = m_FileName;
	        SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyyMMdd");
	        SimpleDateFormat formatter2 = new SimpleDateFormat ("HH:mm:ss");
	        
	        String stDate = formatter1.format(new Date());
	        String stTime = formatter2.format(new Date());
	        StringBuffer bufLogPath  = new StringBuffer();       
	                     bufLogPath.append(stPath);
	                     bufLogPath.append(stFileName);
	                     bufLogPath.append("_");
	                     bufLogPath.append(stDate);
	                     bufLogPath.append(".log") ;
	        StringBuffer bufLogMsg = new StringBuffer(); 
	            bufLogMsg.append("[");
	            bufLogMsg.append(stTime);
	            bufLogMsg.append("]\r\n");             
	            bufLogMsg.append(log);
	                     
	        try{
	                objfile = new FileWriter(bufLogPath.toString(), true);
	                objfile.write(bufLogMsg.toString());
	                objfile.write("\r\n");
	        }catch(IOException e){
	            
	        }
	        finally
	        {
	            try{
	             objfile.close();
	            }catch(Exception e1){}
	        }
	    }
	
}
