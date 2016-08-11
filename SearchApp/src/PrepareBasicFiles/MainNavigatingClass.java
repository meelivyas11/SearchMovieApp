package PrepareBasicFiles;

import java.io.IOException;

public class MainNavigatingClass {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void ResetAllData() throws IOException
	{
		Parser_Meeli parser_Meeli_obj = new Parser_Meeli();
		parser_Meeli_obj.parsingMethod();

		// calling the create index method
		CreateIndexedFilesTopResults topresObj = new CreateIndexedFilesTopResults();
		topresObj.indexedFileTopActorActressList();
		topresObj.indexedFileTopMoviesList();

		CreateIndexedFiles CreateIndexedFilesobj = new CreateIndexedFiles();
		CreateIndexedFilesobj.indexedFileActorActress();
		
	}
	
	public static String[][] DisplayResult(String searchWord) throws IOException
	{
		SearchWordsMainMethod SearchWordsMainMethodObject = new SearchWordsMainMethod();
    	String[][] FinalDisplayData =	SearchWordsMainMethodObject.searchWordsMainMethod(searchWord);
    	
    	return FinalDisplayData;
    
	}
	
	public void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
    	System.out.println("Reached MainNavigatingClass");
    //	if(args[0] == "Reset")
    		ResetAllData();
    	
    	//return DisplayResult(args[1]);
	}

}
