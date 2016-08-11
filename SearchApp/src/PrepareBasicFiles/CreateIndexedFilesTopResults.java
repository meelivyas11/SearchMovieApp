package PrepareBasicFiles;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CreateIndexedFilesTopResults {

	// Strat -- meeli
	public void indexedFileTopActorActressList() throws IOException
	{
       String content = "";
       FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"resource/actor_actress_list_top.txt");
       // Get the object of DataInputStream
       DataInputStream in = new DataInputStream(fstream);
       BufferedReader br = new BufferedReader(new InputStreamReader(in));
       String strLine;
       //Read File Line By Line
       
       File fileDir = new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-data");
       Directory indexDir = FSDirectory.open(new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-index-actor"));
       
       Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
       @SuppressWarnings("deprecation")
       IndexWriter indexer = new IndexWriter(indexDir, luceneAnalyzer, true, MaxFieldLength.UNLIMITED);
       
       // now create the document list
      File[] textFiles  = fileDir.listFiles();
      //long startTime = new Date().getTime();

       
       while ((strLine = br.readLine()) != null)   {
       // Print the content on the console
      	 content += strLine + "\t";
       //System.out.println (strLine);
       }
      
       Pattern style = Pattern.compile("<table.*?>.*?</table>");
       Matcher mstyle = style.matcher(content);
       while (mstyle.find()) {
      	
      	String eachTable = mstyle.group();
      	 
       Pattern styleTable = Pattern.compile("<tr.*?>.*?</tr>");
       Matcher mstyleTable = styleTable.matcher(eachTable);
       while(mstyleTable.find())
       {
          Pattern styleTd = Pattern.compile("<td.*?>.*?</td>");
          Matcher mstyleTd = styleTd.matcher(eachTable);
          
          int k = 0;
          while(mstyleTd.find())
          {
        	  Document document = new Document();
       	   
       	   Pattern stylerecord = Pattern.compile("<td.*?>");
              Matcher mstylerecord = stylerecord.matcher(mstyleTd.group());
              
              Pattern stylerecord1 = Pattern.compile("<font.*?>");
              Matcher mstylerecord1 = stylerecord1.matcher(mstylerecord.replaceAll(""));
              
              Pattern stylerecord2 = Pattern.compile("<a.*?>");
              Matcher mstylerecord2 = stylerecord2.matcher(mstylerecord1.replaceAll(""));
              
              Pattern stylerecord3 = Pattern.compile("</td>");
              Matcher mstylerecord3 = stylerecord3.matcher(mstylerecord2.replaceAll(""));
              
              Pattern stylerecord4= Pattern.compile("</font>");
              Matcher mstylerecord4 = stylerecord4.matcher(mstylerecord3.replaceAll(""));
              
              Pattern stylerecord5 = Pattern.compile("</a>");
              Matcher mstylerecord5 = stylerecord5.matcher(mstylerecord4.replaceAll(""));
              
              String Value =mstylerecord5.replaceAll("");  
            
              if(k%5 ==0){
            	  document.add(new Field("rank",Value, Field.Store.YES, Field.Index.ANALYZED));
              }
       	   	  else if(k%5 ==1){
       		      document.add(new Field("name",Value, Field.Store.YES, Field.Index.ANALYZED));
       	   	  }
       	      else if(k%5 ==2){
       		      document.add(new Field("known for",Value, Field.Store.YES, Field.Index.ANALYZED));
       	      }
       	      else if(k%5 ==3){
       		      document.add(new Field("BirthDate",Value, Field.Store.YES, Field.Index.ANALYZED));
       		      }
       	      else if(k%5 == 4){
       		      document.add(new Field("Date",Value, Field.Store.YES, Field.Index.ANALYZED));
       	      }
       	   
           k++;
           indexer.addDocument(document);
            }
       }
        
       }
       indexer.optimize();
       indexer.close();
	}
	
	public void indexedFileTopMoviesList() throws IOException
	{
       String content = "";
       FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"resource/top_500_list.txt");
       // Get the object of DataInputStream
       DataInputStream in = new DataInputStream(fstream);
       BufferedReader br = new BufferedReader(new InputStreamReader(in));
       String strLine;
       //Read File Line By Line
       
       File fileDir = new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-data");
       Directory indexDir = FSDirectory.open(new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-index-movie"));
       
       Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
       @SuppressWarnings("deprecation")
       IndexWriter indexer = new IndexWriter(indexDir, luceneAnalyzer, true, MaxFieldLength.UNLIMITED);
       
       // now create the document list
      File[] textFiles  = fileDir.listFiles();

       
       while ((strLine = br.readLine()) != null)   {
      	 content += strLine + "\t";
       }
      
       Pattern style = Pattern.compile("<table.*?>.*?</table>");
       Matcher mstyle = style.matcher(content);
       while (mstyle.find()) {
      	
      	String eachTable = mstyle.group();
      	 
       Pattern styleTable = Pattern.compile("<tr.*?>.*?</tr>");
       Matcher mstyleTable = styleTable.matcher(eachTable);
       while(mstyleTable.find())
       {
          Pattern styleTd = Pattern.compile("<td.*?>.*?</td>");
          Matcher mstyleTd = styleTd.matcher(eachTable);
          
          int k = 0;
          
          while(mstyleTd.find())
          {
        	  Document document = new Document();
       	   
       	   Pattern stylerecord = Pattern.compile("<td.*?>");
              Matcher mstylerecord = stylerecord.matcher(mstyleTd.group());
              
              Pattern stylerecord1 = Pattern.compile("<font.*?>");
              Matcher mstylerecord1 = stylerecord1.matcher(mstylerecord.replaceAll(""));
              
              Pattern stylerecord2 = Pattern.compile("<a.*?>");
              Matcher mstylerecord2 = stylerecord2.matcher(mstylerecord1.replaceAll(""));
              
              Pattern stylerecord3 = Pattern.compile("</td>");
              Matcher mstylerecord3 = stylerecord3.matcher(mstylerecord2.replaceAll(""));
              
              Pattern stylerecord4= Pattern.compile("</font>");
              Matcher mstylerecord4 = stylerecord4.matcher(mstylerecord3.replaceAll(""));
              
              Pattern stylerecord5 = Pattern.compile("</a>");
              Matcher mstylerecord5 = stylerecord5.matcher(mstylerecord4.replaceAll(""));
              
              Pattern stylerecord6 = Pattern.compile("<div.*?>.*?</div>");
              Matcher mstylerecord6 = stylerecord6.matcher(mstylerecord5.replaceAll(""));
              
              Pattern stylerecord7 = Pattern.compile("Close");
              Matcher mstylerecord7 = stylerecord7.matcher(mstylerecord6.replaceAll(""));
              
              String Value =mstylerecord7.replaceAll("");  
              
              if(k%9 ==0){
            	  document.add(new Field("rank",Value, Field.Store.YES, Field.Index.ANALYZED));
              }
       	   	  else if(k%9 == 1){
       		      document.add(new Field("Title",Value, Field.Store.YES, Field.Index.ANALYZED));
       	   	  }
       	      else if(k%9 == 2){
       		      document.add(new Field("Year",Value, Field.Store.YES, Field.Index.ANALYZED));
       	      }
       	      else if(k%9 == 3){
//    		   System.out.println("3 : " + Value);
    	      }
              else if(k%9 == 4){
 		      	//System.out.println("4 : " + Value);
 	      }
       	      else if(k%9 == 5){
       		      //System.out.println("5 : " + Value);
       		      }

       	   else if(k%9 == 6){
    		      document.add(new Field("Star",Value, Field.Store.YES, Field.Index.ANALYZED));
    		      }
    	     
       	else if(k%9 == 7){
 		      document.add(new Field("Rating",Value, Field.Store.YES, Field.Index.ANALYZED));
 		      }
 	    
       	else if(k%9 == 8){
		      // System.out.println("7 : " + Value);
		      }
	    
              
           k++;
           indexer.addDocument(document);
            }
       }
        
       }
       indexer.optimize();
       indexer.close();
	}
	
}
// End -- meeli