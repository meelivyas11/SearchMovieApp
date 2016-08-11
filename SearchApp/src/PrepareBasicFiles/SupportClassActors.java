package PrepareBasicFiles;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class SupportClassActors {

	// Start -- meeli 
	public  void actors() throws IOException {
        String content = "";
        int i = 1;
        
        
        FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"resource/actress_tables_links.txt");
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        
        
        File fileDir = new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/data");
        Directory indexDir = FSDirectory.open(new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/index"));
        
        WhitespaceAnalyzer luceneAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_30);
        IndexWriter indexer = new IndexWriter(indexDir, luceneAnalyzer, true, MaxFieldLength.UNLIMITED);
        
        // now create the document list
        
        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
       	 content += strLine + "\t";
        }
       
        Pattern style = Pattern.compile("<meeli.*?>.*?</meeli>");
        Matcher mstyle = style.matcher(content);
        while (mstyle.find()) {
       	 
        Document document = new Document();
        	
       	 //String fileName = "Actress_" + Integer.toString(i) +".txt";
       	 //File dynFile = new File(fileName);
       	 //boolean fileCreated = dynFile.createNewFile();
       	// System.out.println("The value os write is :"+dynFile.canWrite());
       	 
       	 //Writer writer = new BufferedWriter(new FileWriter(dynFile));
       	// writer.write(eachTable); 
       	

       	 String ActorName = "";
       	 String eachTable = mstyle.group();
       	 
       	 // for the Actor Name
       	 Pattern actorStyle = Pattern.compile("<span class='fn'>.*?</span>");
            Matcher mstyleactor = actorStyle.matcher(eachTable);
            while(mstyleactor.find())
            {
           	 String actorNamespan =  mstyleactor.group();
           	 Pattern styleactor2 = Pattern.compile("<span class='fn'>");
           	 Pattern styleactor3 = Pattern.compile("</span>");
                Matcher mstyleactor2 = styleactor2.matcher(actorNamespan);
                Matcher mstyleactor3 = styleactor3.matcher(mstyleactor2.replaceAll(""));
                ActorName = mstyleactor3.replaceAll("");
                //System.out.println(" :::::::::::::::::::::::: " + ActorName);
            }
            //writer.write(" Name : " + ActorName + "\n");
            document.add(new Field("Name",ActorName, Field.Store.YES, Field.Index.ANALYZED));
           
            // ActorName Ends
            
       	
       	 Pattern styleTable = Pattern.compile("<tr.*?>.*?</tr>");
            Matcher mstyleTable = styleTable.matcher(eachTable);
            while(mstyleTable.find())
            {
           	 String eachTr = mstyleTable.group();
           	 
           	 Pattern styleTh = Pattern.compile("<th.*?>.*?</th>");
                Matcher mstyleTh = styleTh.matcher(eachTr);
                
                Pattern styleTd = Pattern.compile("<td.*?>.*?</td>");
                Matcher mstyleTd = styleTd.matcher(eachTr);
                
                while(mstyleTh.find())
                {
                 	String leftSideInitial = mstyleTh.group(); // left side
                 	Pattern styleleft2 = Pattern.compile("<th.*?>");
              	    Pattern styleleft3 = Pattern.compile("</th>");
                   Matcher mstyleLeft2 = styleleft2.matcher(leftSideInitial);
                   Matcher mstyleLeft3 = styleleft3.matcher(mstyleLeft2.replaceAll(""));
                   String LeftSideFinal = mstyleLeft3.replaceAll("");
                    
                  // System.out.println("Final left Side ::: " + LeftSideFinal );
                   
                 	while(mstyleTd.find())
                   {
                     	String rightSideInitial =mstyleTd.group(); // right side
                    	Pattern styleright2 = Pattern.compile("<td.*?>");
                  	    Pattern styleright3 = Pattern.compile("</td>");
                       Matcher mstyleRight2 = styleright2.matcher(rightSideInitial);
                       Matcher mstyleRight3 = styleright3.matcher(mstyleRight2.replaceAll(""));
                       
                       Pattern stylerightFinal2 = Pattern.compile("<a.*?>");
                       Matcher mstyleRightFinal2 = stylerightFinal2.matcher(mstyleRight3.replaceAll(""));
                       
                       Pattern stylerightFinal4 = Pattern.compile("</a>");
                       Matcher mstyleRightFinal4 = stylerightFinal4.matcher(mstyleRightFinal2.replaceAll(""));
                                              
                       Pattern stylerightFinal = Pattern.compile("<span.*?>");
                       Matcher mstyleRightFinal = stylerightFinal.matcher(mstyleRightFinal4.replaceAll(""));
                      
                       Pattern stylerightFinal3 = Pattern.compile("</span>");
                       Matcher mstyleRightFinal3 = stylerightFinal3.matcher(mstyleRightFinal.replaceAll(""));
                       
                       String rightSideFinal = mstyleRightFinal3.replaceAll("");
                       
                       //System.out.println("Final right Side ::: " + rightSideFinal);
                      // writer.write(" " + LeftSideFinal + " : " + rightSideFinal + "\n");
                       
                       document.add(new Field(LeftSideFinal,rightSideFinal, Field.Store.YES, Field.Index.ANALYZED));
                       
                   }
                 	 
                }
                   	
                // tr ends
                
            }
            Pattern stylelink = Pattern.compile("<link.*?>.*?</link>");
            Matcher mstylelink = stylelink.matcher(eachTable);
        	while(mstylelink.find())
           {
        		String link = mstylelink.group();
        		
        		Pattern stylelinkFinal = Pattern.compile("<link.*?>");
               Matcher mstylelinkFinal = stylelinkFinal.matcher(link);
              
               Pattern stylelinkFinal3 = Pattern.compile("</link>");
               Matcher mstylelinkFinal3 = stylelinkFinal3.matcher(mstylelinkFinal.replaceAll(""));
              
        		//writer.write(" Link : " + mstylelinkFinal3.replaceAll("") + "\n");
        		document.add(new Field("Link" ,mstylelinkFinal3.replaceAll(""), Field.Store.YES, Field.Index.NO));
           }
          
            
            //writer.close();
            indexer.addDocument(document);
            i++;
        }
        
        indexer.optimize();
        //indexDir.createOutput("C:\\java setup\\lucene_db\\index");
        indexer.close();
        
        IndexSearcher searcher = new IndexSearcher(indexDir);
        //QueryParser parse = new QueryParser(Version.LUCENE_CURRENT, "content", luceneAnalyzer);
        Query query = new TermQuery(new Term("Spouse","amy"));
        //Query query = parse.parse("angelina");
        TopDocs results = searcher.search(query, 1000);
        //Hits hit = searcher.search(query, 10);
        System.out.println("total hits:" + results.totalHits);
        Document res_doc;
        int docId=0;
        ScoreDoc[] hits = results.scoreDocs;
        for(int j = 0; j < hits.length; j++)
        {
            //docId = results.scoreDocs[i].doc;
            docId = hits[j].doc;
            res_doc = searcher.doc(docId);
            //System.out.println("result no "+ i + ":" + res_doc.get("content"));
            System.out.println("result no "+ j + ":" + res_doc.getValues("Name")+ ":::"+res_doc.toString());
        }
     	
	}
	
	// End -- meeli
	
}
