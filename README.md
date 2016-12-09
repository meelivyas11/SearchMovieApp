# Search Movie App

## Setting up the Environment

### Software Requirements
 - Git Bash: https://git-scm.com/
 - Eclipse
 - Apache Tomcat: http://apache.spinellicreations.com/tomcat/tomcat-8/v8.0.39/bin/apache-tomcat-8.0.39.zip
 
### Download the Code
 - Clone the repository using `git clone https://github.com/meelivyas11/SearchMovieApp.git` from Git Bash
 - Open the repository 'SearchMovieApp' from eclipse IDE
 - Import 'SearchApp' Project (`File->Import->General->Existing Project into Workspace`) from eclipse IDE
 
### Build
  - Remove the existing external jars and re-add them from `SearchApp Properties -> Java Build Path` 
    <b>Note: Required Jars are present repository `Jars` folder </b>
  - Fix the Target runtime error by creating a new `Apache Tomcat v8.0` server
  - Clean and Build the Project
  
### Running
 - Start the Server
 - Open `http://localhost:8080/SearchApp/` from your web browser
 - Enter the name of the any actor, actress or movie and Search
 - Once you get some search results, you can click the link in the result to redirect to the wiki pages associated to the result
 
