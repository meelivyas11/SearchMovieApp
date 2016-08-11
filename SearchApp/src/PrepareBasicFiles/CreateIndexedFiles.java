package PrepareBasicFiles;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.StaleReaderException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class CreateIndexedFiles {

	public void indexedFileActorActress() throws IOException
	{
		 try{
		        File fileDir = new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/data");
		        Directory indexDir = FSDirectory.open(new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/index"));
		        
		        Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
		        @SuppressWarnings("deprecation")
		        IndexWriter indexer = new IndexWriter(indexDir, luceneAnalyzer, true, MaxFieldLength.UNLIMITED);
		        
		        File[] textFiles  = fileDir.listFiles();
		        int new_rank = 0;
		        String new_rank_string;
		        //Add documents to the index
		        for(int i = 0; i < textFiles.length; i++){
		       
		           Reader textReader = new FileReader(textFiles[i]);
		           Document document = new Document();
		           
		           FileInputStream fstream = new FileInputStream(textFiles[i]);
		           DataInputStream in = new DataInputStream(fstream);
		           BufferedReader br = new BufferedReader(new InputStreamReader(in));
		           String strLine;
		           String content ="";
		           
		           while((strLine = br.readLine()) != null)
		           {
		               content += strLine + "\t";
		           }
		           
		           // Find Name Start
		           String NameActorActress = "";
		           StringTokenizer st2 = new StringTokenizer(content,";");	
		           if(st2.hasMoreElements()){
		       			Pattern stylerightFinal5 = Pattern.compile("Name :");
	                    Matcher mstyleRightFinal5 = stylerightFinal5.matcher(st2.nextElement().toString());
	                    String Nametemp= mstyleRightFinal5.replaceAll("").trim();
	                    StringTokenizer st4 = new StringTokenizer(Nametemp," ");
	                    if(st4.hasMoreElements()){
	                    		NameActorActress = st4.nextElement().toString().toLowerCase();
	                    }
		       		}
		           
		           // Find Name End
		           String temp_path;
		           type_of_data data_type = type_of_data.Actor;
		           temp_path = textFiles[i].getPath();
		           if(temp_path.contains("Movie"))
		           {
		        	   data_type = type_of_data.Movie;
		           }
		           new_rank = ranking_val(data_type, NameActorActress);
		           new_rank_string = Integer.toString(new_rank);
		           
		           document.add(new Field("content",content, Field.Store.YES, Field.Index.ANALYZED));
		           document.add(new Field("path",textFiles[i].getPath(), Field.Store.YES, Field.Index.NO));
		           document.add(new Field("rank",new_rank_string, Field.Store.YES, Field.Index.NO));
		           indexer.addDocument(document);
		               
		       }
		       indexer.optimize();
		       indexer.close();
		       
		       
		    } 
		    catch(Exception e)
		    {
		        System.out.println(" Exception : "+e);
		        e.printStackTrace();
		    }
		    
		
	}
	
	// Start by  -- parth	
   int findInTop(String s, String path)
    {
	   // find the string in any of the top lists
        int new_rank=0;
        try
        {
           Directory indexDir = FSDirectory.open(new File(path));
           IndexSearcher searcher = new IndexSearcher(indexDir); //top list directory
          
           Query query = new TermQuery(new Term("content",s));
           
           TopDocs results = searcher.search(query, 10);
           
           //System.out.println("total hits:" + results.totalHits);
           new_rank = results.totalHits;
           
        }
        catch(Exception e)
        {
            System.out.println("inside findintop " + e);
        }
        return new_rank;
    }

    enum type_of_data{
       Actor,
       Actress,
       Movie
   }
   
   // this will return rank of the particular file
   int ranking_val(type_of_data data, String actual_data)
   {
   // if the type is actor add 1, if movie keep it at 0, 
   // check list of top 100 actors add for every actor found
   // check list of top 250 movies add for movies
   // 
       int curr_rank = 0, final_rank = 0;
       if(type_of_data.Actor == data)
       {
           curr_rank = findInTop(actual_data,FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-index-actor");
           curr_rank++;
       }
       else if(type_of_data.Actress == data)
       {
           curr_rank = findInTop(actual_data, FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-index-actor");
           curr_rank++;
       }
       else
       {
           curr_rank = findInTop(actual_data,FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/top-index-movie");
       }
       
       final_rank = curr_rank;
       return final_rank;
   }
	
   void updateIndex(int docId, int count) throws CorruptIndexException, LockObtainFailedException, IOException{
       String content, path, rank;
       int old_rank_value = 0;
       Document document = new Document();
       Directory indexDir = FSDirectory.open(new File(FilePathData.getAbsoluteDiskPath()+"JavaSetupFiles/lucene_db/index"));
       Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
        //new IndexWriter(indexDir, luceneAnalyzer, false, null);
        @SuppressWarnings("deprecation")
       IndexWriter indexer = new IndexWriter(indexDir, luceneAnalyzer, true, MaxFieldLength.UNLIMITED);
       
       IndexReader indexReader;
       indexReader = IndexReader.open(indexDir,false);
       Document res_doc = indexReader.document(docId);
       
       content = res_doc.get("content");
       path = res_doc.get("path");
       rank = res_doc.get("rank");
       old_rank_value = Integer.parseInt(rank);
       old_rank_value = old_rank_value + count;
       rank = Integer.toString(old_rank_value);
       
       try {
		indexReader.deleteDocument(docId);
		indexReader.close();
	} catch (StaleReaderException e) {
		e.printStackTrace();
	} catch (CorruptIndexException e) {
		e.printStackTrace();
	} catch (LockObtainFailedException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
       // delete the document that needs to be updated
       
       // reinsert the document that was just deleted with the new 

       document.add(new Field("content",content, Field.Store.YES, Field.Index.ANALYZED));
       document.add(new Field("path",path, Field.Store.YES, Field.Index.NO));
       document.add(new Field("rank",rank, Field.Store.YES, Field.Index.ANALYZED));
       try {
		indexer.addDocument(document);
	} catch (CorruptIndexException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

   }
   
   // End by -- parth
   
}