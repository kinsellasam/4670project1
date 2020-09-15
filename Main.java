// Name: Sam Kinsella
// Project 1 CS4760
// This project is designed to work with a directory. It countc how many files are
// in any subdirectories and aquires their stats and block size.
// Date: 9/14/20

import java.io.*;

class Main{

  //use recursion to traverse the given directory in Java usin DFS
  public static void listFilesRecursive(File root) throws java.io.IOException{

    //get a list of all the files and directories present in root
    File[] listOfFilesAndDirectories = root.listFiles();

    //listFiles() returns a non-null array if root denotes a directory
    if (listOfFilesAndDirectories != null){
      for (File file : listOfFilesAndDirectories){
        //if file denotes a directory, recur for it
        if (file.isDirectory()){
          System.out.println("Directory: " + file);
          listFilesRecursive(file);
        }
        //if file denotes a file
        else{
          System.out.println("File: " + file);
          Process process = Runtime.getRuntime().exec("stat "+ file);
          printResults(process);
        }
      }
    }
  }

  //printer for the process object
  public static void printResults(Process process) throws java.io.IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line = "";
    while((line = reader.readLine()) != null){
      System.out.println(line);
    }
  }

  public static void main(String[] args) {
    int sumOfPositiveReturns = 0;

    //Local root directory
    String dir = "*/4670proj1/root";
    File rootDir = new File(dir);

    //recursivly print all files present in the root directory
    try {
      listFilesRecursive(rootDir);
    }
    catch(IOException e) {
      e.printStackTrace();
    }


  }
}
