package PrepareBasicFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class SupportClassMovie {

	public  void movies() throws IOException {

        String content = "";
        int i = 1;
        FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"resource/movie_tables_links.txt");
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
        // Print the content on the console
       	 content += strLine + "\t";
        //System.out.println (strLine);
        }
       
        Pattern style = Pattern.compile("<meeli.*?>.*?</meeli>");
        Matcher mstyle = style.matcher(content);
        while (mstyle.find()) {
       	 
       	 String fileName =  "Movie_" +Integer.toString(i) +".txt";
       	 File dynFile = new File(FilePathData.getAbsoluteDiskPath()+"resource/MovieData/"+fileName);
       	 //boolean fileCreated = dynFile.createNewFile();
       	 //System.out.println("The value os write is :"+dynFile.canWrite());
       	 
       	 Writer writer = new BufferedWriter(new FileWriter(dynFile));
       	// writer.write(eachTable); 
       	

       	 int MovieInnt = 0;
       	 String MovieName = "";
       	 String eachTable = mstyle.group();
       	 
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
                   
                    
                   Pattern styleleftFinal = Pattern.compile("<span.*?>");
                   Matcher mstyleleftFinal = styleleftFinal.matcher(mstyleLeft3.replaceAll(""));
                  
                   Pattern styleleftFinal3 = Pattern.compile("</span>");
                   Matcher mstyleleftFinal3 = styleleftFinal3.matcher(mstyleleftFinal.replaceAll(""));
                   
                   String LeftSideFinal = mstyleleftFinal3.replaceAll("");
                   if(MovieInnt == 0){
                	  // System.out.println("Movie Name ::: " + LeftSideFinal );
                	   MovieName = LeftSideFinal;
                	   writer.write(" Movie Name : " + MovieName + "\n");
                   }
                   
                   MovieInnt++;
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
                       
                       Pattern stylerightFinal5 = Pattern.compile("<img.*?>");
                       Matcher mstyleRightFinal5 = stylerightFinal5.matcher(mstyleRightFinal3.replaceAll(""));
                       
                       
                       String rightSideFinal = mstyleRightFinal5.replaceAll("");
                       
                      // System.out.println(" " + LeftSideFinal + " : " + rightSideFinal + "\n");
                       writer.write(" " + LeftSideFinal + " : " + rightSideFinal + "\n");
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
              
        		writer.write("Link : " + mstylelinkFinal3.replaceAll("") + "\n");
           }
          
            
            writer.close();
            i++;
        }
     	
	}
	
}
