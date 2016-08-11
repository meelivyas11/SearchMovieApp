package PrepareBasicFiles;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
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

public class SearchWordsMainMethod {
	
	// start -- meeli
	public String[][] searchWordsMainMethod(String SearchData) throws IOException
	{
		System.out.println("in searchWordsMainMethod");
		CreateIndexedFiles CreateIndexedFilesobj = new CreateIndexedFiles();
        StringTokenizer st2 = new StringTokenizer(SearchData," ");
        int ForWordCount = 0;
        IndexSearcher[] anArraySercher = new IndexSearcher[st2.countTokens()];
        TopDocs[] anArrayResults = new TopDocs[st2.countTokens()];
        int maxHitSize = 0;
        int NomOfWords = st2.countTokens();
        Directory indexDir = null;
        IndexSearcher searcher = null;
        Document res_doc; 
		try {
			indexDir = FSDirectory.open(new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/index"));
			searcher = new IndexSearcher(indexDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(st2.hasMoreElements()){
        	String SearchWord = st2.nextElement().toString();
        	
        	try {
        		System.out.println("SearchWord.toLowerCase() ::: " + SearchWord.toLowerCase());
        		Query query = new TermQuery(new Term("content",SearchWord.toLowerCase()));
        	    TopDocs results = searcher.search(query, 1000);
        	    System.out.println("results ::: " + results.toString());
        	    int totalHits = results.totalHits;
        	    if(maxHitSize < totalHits)
        	    	maxHitSize = totalHits;
        	    
        	    anArraySercher[ForWordCount] = searcher;
        	    anArrayResults[ForWordCount] = results;
        	   	} catch (IOException e) {
				e.printStackTrace();
			}
        	ForWordCount++;
        		}
        
     // make docId 2D array
        int[][] DocIdArray = new int[ForWordCount][maxHitSize];
		for(int l= 0; l<ForWordCount; l++)
		{
			    //IndexSearcher searcher = anArraySercher[l];
				TopDocs results = anArrayResults[l];
				ScoreDoc[] hits = results.scoreDocs;
			       for(int m = 0; m < hits.length; m++)
			       {
			    	   int docId = hits[m].doc;
			    	   DocIdArray[l][m] = docId;			    	   
			       }
		}
		//  make docId 2D array Ends
        
		// Find the common DOcId
		int[] DocIdInitialList = new int[(maxHitSize)*(NomOfWords)];
		int[][] DocIdRankList = new int[(maxHitSize)*(NomOfWords)][4];
		
		for(int a=0;a<(maxHitSize)*(NomOfWords);a++)
		{
			DocIdRankList[a][1]=0;
			DocIdRankList[a][0]=0;
			DocIdRankList[a][2]=0;
			DocIdRankList[a][3]=0;
		}
		
		int NumOfDocId = 0;
		int tempCount = 0;
		for(int l= 0; l<ForWordCount; l++)
		{
			TopDocs results = anArrayResults[l];
			ScoreDoc[] hits = results.scoreDocs;
			for(int m = 0; m < hits.length; m++)
			{
			  int docId = hits[m].doc;
			  float score = hits[m].score;
			  int scoreFinal =  (int) (score * 100);
			  tempCount++;
			  
			   Search: for(int i =0; i<(maxHitSize)*(NomOfWords);i++)
				{
				  
				  if(docId != DocIdRankList[i][0] && DocIdRankList[i][0] != 0)
					{
						continue;
					}
					else if(DocIdRankList[i][0] == 0)
					{
						DocIdRankList[i][0] = docId;
						DocIdRankList[i][2] = scoreFinal;
						try 
						  {
							res_doc = searcher.doc(docId);
							DocIdRankList[i][3] = Integer.parseInt(res_doc.get("rank"));
						  } 
						  catch (IOException e) 
						  {
							  e.printStackTrace();
						  }						
						DocIdRankList[i][1]++;
						break Search;
					}
					else
					{
						DocIdRankList[i][1]++;
						break Search;
					}
					
				}
			 
				
			  for(int n= 1; n<ForWordCount; n++)
				{
				  TopDocs resultsSecond = anArrayResults[n];
					ScoreDoc[] hitsSecond = resultsSecond.scoreDocs;
					for(int p = 0; p < hitsSecond.length; p++)
					{
						int docIdSecond = hitsSecond[p].doc;
						if(docId == docIdSecond)
						{
							DocIdInitialList[NumOfDocId] = docId;
							NumOfDocId++;
						}
					}
				}
			  
			}
		}
		// Find the common DOcId End
		
		// Number of Occurance 
		int NumOfOccurances = 0;
		
		for(int temp =0; temp <(maxHitSize)*(NomOfWords); temp++)
		  {
			  if(DocIdRankList[temp][0] != 0){
				  int docID = DocIdRankList[temp][0];
				  try 
				  {
					res_doc = searcher.doc(docID);
				  } 
				  catch (IOException e) 
				  {
					  e.printStackTrace();
				  }
				  NumOfOccurances++;
			  }
		  }
		// Number of Occurance End
		// 0-docid ; 1-Occurance ; 2-score ; 3-rank
		// Making Seperate Array
		int[][] ScoreFinal2DArray = new int[NumOfOccurances][2];
		int[][] RankFinal2DArray = new int[NumOfOccurances][2];
		for(int x=0; x< NumOfOccurances; x++)
		{
			ScoreFinal2DArray[x][0] = DocIdRankList[x][0];
			ScoreFinal2DArray[x][1] = DocIdRankList[x][2];
			RankFinal2DArray[x][0] = DocIdRankList[x][0];
			RankFinal2DArray[x][1] = DocIdRankList[x][3]+DocIdRankList[x][1];
		}
		
		
		//Making Seperate Array End -- meeli
		
	// Sorting FA Function -- sangeetha
		String[][] FinalDisplayData =null;
		if(NumOfOccurances != 0)
		{
		
		int min=RankFinal2DArray[0][1],max=RankFinal2DArray[0][1];
		//Normalisation for Rank array

		for(int i=0;i<RankFinal2DArray.length;i++)
		{
			if(RankFinal2DArray[i][1]>max)
			{
				max=RankFinal2DArray[i][1];
			}
			if(RankFinal2DArray[i][1]<min)
			{
				min=RankFinal2DArray[i][1];
			}
		}
		
		if(min != max)
		{// normalize only when min and max are different
			for(int k=0;k<RankFinal2DArray.length;k++)
			{
				RankFinal2DArray[k][1]=((RankFinal2DArray[k][1]-min))/(max-min);
			}
		}
		else
		{
			for(int k=0;k<RankFinal2DArray.length;k++)
			{
				RankFinal2DArray[k][1]=100;
			}
			
		}
		
		
	int[][] FinalSortedList= new int[RankFinal2DArray.length][2];
	
	for (int i=0;i<RankFinal2DArray.length;i++)
	{
		for(int j=0;j<RankFinal2DArray.length;j++)
		{
		if(RankFinal2DArray[i][0]==ScoreFinal2DArray[j][0])
		{
			
			if(ScoreFinal2DArray[j][1]<=RankFinal2DArray[i][1])
			{
				FinalSortedList[i][0]=RankFinal2DArray[i][0];
		
				FinalSortedList[i][1]=(int)ScoreFinal2DArray[j][1];
			
			}
			else
			{
				FinalSortedList[i][0]=RankFinal2DArray[i][0];
				FinalSortedList[i][1]=(int)RankFinal2DArray[j][1];

			}
		}
		}
		
		
		
	}
	// Sorting FA Function End -- sangeetha     
	
	// updated implementation of FA --- parth
	
	 for (int i=0;i<RankFinal2DArray.length;i++)// this loop goes through score
	    {
	        // check if the docID is already present in temp or not
	           Search1: for(int j =0; j<FinalSortedList.length;j++)
	            {
	              if(ScoreFinal2DArray[j][0] != FinalSortedList[j][0] && FinalSortedList[j][0] != 0)
	                {
	                    continue;
	                }
	                else if(FinalSortedList[j][0] == 0)
	                {
	                	// if docID from score array needs to be inserted
	                    for(int k=0;k<RankFinal2DArray.length;k++)
	                    {
	                        if(RankFinal2DArray[j][0]==ScoreFinal2DArray[k][0])
	                        {
	                            
	                            if(ScoreFinal2DArray[k][1]<=RankFinal2DArray[j][1])
	                            {
	                            	FinalSortedList[j][0]=RankFinal2DArray[j][0];
	                        
	                            	FinalSortedList[j][1]=(int)ScoreFinal2DArray[k][1];
	                            
	                            }
	                            else
	                            {
	                            	FinalSortedList[j][0]=RankFinal2DArray[j][0];
	                            	FinalSortedList[j][1]=(int)RankFinal2DArray[k][1];
	                
	                            }
	                        }
	                    }
	                    break Search1;
	                }
	                else
	                {
	                    break Search1;
	                }
	                
	            }
	    }
	
	// end of Implementation of FA --- parth
    int temp ,temp1;
       for(int j = 0; j < FinalSortedList.length -1 ; j++)  
        {          
    	   for(int i=0;i<FinalSortedList.length -1;i++)
    	   {
            if((FinalSortedList[i][1])<(FinalSortedList[i+1][1]))  
                       
            {   
                 temp1=FinalSortedList[i][0];
                 FinalSortedList[i][0]=FinalSortedList[i+1][0];
                 FinalSortedList[i+1][0]=temp1;
                 
                temp = FinalSortedList[i][1];                    
                FinalSortedList[i][1] = FinalSortedList[i+1][1];            
                FinalSortedList[i+1][1] = temp;
                
            }
            }                                        
        } 
       
		
		
		
		//Display of the results -- meeli
      
       String[] result_str_display = new String[FinalSortedList.length];
       int result_size = result_str_display.length;
       if(result_str_display.length > 20)
    	   result_size = 20;
    	   
       for(int i=0; i<result_size; i++)
       {
    	   res_doc = searcher.doc(FinalSortedList[i][0]);
    	   result_str_display[i] = res_doc.get("content");
       }
       
       FinalDisplayData = new String[result_str_display.length][4];
       FinalDisplayData =  displayLinkFinal(result_str_display);
       
      /*  for(int x=0; x< NumOfOccurances; x++)
		{
			 System.out.println("Meeli  Score Array  Display ::" + ScoreFinal2DArray[x][0] +" ::::::::::::: "  + ScoreFinal2DArray[x][1]);
			 System.out.println("Meeli  Rank Array  Display ::" + RankFinal2DArray[x][0] +" ::::::::::::: "  + RankFinal2DArray[x][1]);
		}*/
     //Display of the results -- meeli End 
       
 	//updating of ranks to the new values -- parth
         
       Document doc_arr[] = new Document[NumOfOccurances];
       int old_rank_value = 0;
       int docId;
       int count;
      
       IndexReader indexReader = IndexReader.open(indexDir,false);
     
       for(int i=0; i <NumOfOccurances ; i++)
       {
    	   docId = DocIdRankList[i][0];
    	   count = DocIdRankList[i][1];
    	   String rank;
           res_doc = searcher.doc(docId);

           rank = res_doc.get("rank");
           res_doc.removeField("rank");
           System.out.println("old rank"+rank);
           
           old_rank_value = Integer.parseInt(rank);
           old_rank_value = old_rank_value + count;
           rank = Integer.toString(old_rank_value);
           System.out.println("updated rank::"+rank);
           
           res_doc.add(new Field("rank",rank, Field.Store.YES, Field.Index.NO));
           doc_arr[i] = res_doc;
           //updateIndex(docId,res_doc, count, indexDir);
            indexReader.deleteDocument(docId);
            indexReader.flush();
       }
       indexReader.close();
              
       Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
       IndexWriter indexer = new IndexWriter(indexDir, luceneAnalyzer, false, MaxFieldLength.UNLIMITED);
       for(int i=0; i <NumOfOccurances ; i++)
       {
            count =  DocIdRankList[i][1];
            
            indexer.addDocument(doc_arr[i]);
            indexer.optimize();
            //System.out.println("updated rank::"+doc_arr[i].get("rank"));
                      
       }
       indexer.close();
       indexDir.close();
        //updating of ranks to the new values -- parth End 
		}
		else
		{
			FinalDisplayData = null;
			
		}
       return FinalDisplayData;
		
	}
	// Start -- meeli
	public String[][] displayLinkFinal(String[] DataStringArray)
	{
		String DataString[][] = new String[DataStringArray.length][2];
		System.out.println("Length " + DataString.length);
		for(int i=0;i<DataStringArray.length;i++)
		{
			String data = DataStringArray[i];
			if(data != null){
				System.out.println("Data :: " + data);
				int index=data.lastIndexOf("Link : ");
		        String FinalLink =data.substring(index+6);
		         
		        String dataDisplay = data.substring(0, index-1);
		        
		        DataString[i][0]= dataDisplay;
		        DataString[i][1]= FinalLink; 
			}
			else
			{
				 DataString[i][0]= "Data Not Available";
			     DataString[i][1]= "http://localhost:8080/SearchApp";
			}
			
		}
		return DataString;
        
	}
	// End -- meeli
	
}
