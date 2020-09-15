// Name: Sam Kinsella
// Project 1 CS4760
// This project is designed to work with a directory. It countc how many files are
// in any subdirectories and aquires their stats and block size. This version is
// also able to handle user input in a few areas.
// Date: 9/14/20

import java.io.*;
import java.util.*;

class mydu{

  //global variables
  public static int sumOfPositiveReturns = 0;

  //use recursion to traverse the given directory in Java usin DFS
  public static int depthfirstapply(File root) throws java.io.IOException{



    //get a list of all the files and directories present in root
    File[] listOfFilesAndDirectories = root.listFiles();

    //listFiles() returns a non-null array if root denotes a directory
    if (listOfFilesAndDirectories != null){
      for (File path : listOfFilesAndDirectories){
        //if file denotes a directory, recur for it
        if (path.isDirectory()){
          System.out.println("\nDirectory: " + path);
          depthfirstapply(path);
        }
        //if file denotes a file
        else if(path.isFile()){
          System.out.println("\nFile: " + path);
          sizepathfun(path);
          sumOfPositiveReturns++;
        }
        //if no file or directory exists
        else{
          sumOfPositiveReturns = -1;
        }
      }
    }
    return sumOfPositiveReturns;
  }

  //printer for the process object
  public static void printResults(Process process) throws java.io.IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line = "";
    while((line = reader.readLine()) != null){
      System.out.println(line);
    }
  }

  //printer for the process2 object
  public static void printResults2(Process process) throws java.io.IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line = "";
    while((line = reader.readLine()) != null){
      System.out.println(line);
    }
  }

  //function to call command line arguments for the stats and block size of a given file
  public static void sizepathfun(File path) throws java.io.IOException {

    //process to get the stats for the file
    System.out.println("\nsizepathfun() is finding the stats of the file...");
    Process process = Runtime.getRuntime().exec("stat "+ path);
    printResults(process);

    //process to get the block size for the file
    System.out.println("\nsizepathfun() is finding the block size of the file...");
    Process process2 = Runtime.getRuntime().exec("du -h "+ path);
    printResults2(process2);
  }

  //function to get the user input directory
  public static String getUserDir(){

    System.out.println("Please enter in a custom root directory? y/n");
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.nextLine();

    return userInput;
  }

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
          sumOfPositiveReturns++;
        }
        //if file denotes a file
        else{
          System.out.println("File: " + file);
          Process process = Runtime.getRuntime().exec("stat "+ file);
          printResults(process);
          sumOfPositiveReturns++;
        }
      }
    }
  }


  public static void main(String[] args) {

    if (args.length == 0){
      System.out.println("Proper usage is: java mydu [argument]. Please check README.txt for valid arguments.");
      System.exit(0);
    }

    //Local root directory
    String dir = "/Users/SamKinsella/desktop/4670proj1/root";
    File rootDir = new File(dir);

    System.out.println("For this build of the project, a custom directory has been supplied. " +
    "The directory that is currently being used is: " + rootDir + "\n");

    //scanner object for user Input
    char userInput = ' ';
    System.out.println("Would the user like to enter in a custom root directory? y/n");
    Scanner scanner = new Scanner(System.in);
    userInput = scanner.next().charAt(0);

    do{
      if(userInput == 'n'){
        System.out.println("The supplied directory will be used.");
        break;
      }
      else if(userInput == 'y'){
        System.out.println("Please enter your directory: ");
        dir = getUserDir();
        rootDir = new File(dir);

        System.out.println("The new directory that will be used is: " + rootDir);
        break;
      }
      else{
        System.out.println("Would the user like to enter in a custom root directory? y/n");
        userInput = scanner.next().charAt(0);
      }
    }while(userInput != 'y');


    switch(args[0]){
      case "-h": //help argument
        System.out.println("MyDu: User invoked the 'help' argument. For a list of " +
        "valid arguments, please check README.txt");
        break;

      case "-a": //the code will run as intended

        System.out.println("The function depthfirstapply() is now traversing the given root directory...");

        //recursivly print all files present in the root directory
        try {
          System.out.println("Sum of positive returns: " + depthfirstapply(rootDir));
        }
        catch(IOException e) {
          e.printStackTrace();
        }
        break;

      case "showtreesize":
        System.out.println("The function listFilesRecursive() is now traversing the given root directory...");

        //recursivly print all files present in the root directory
        try {
          listFilesRecursive(rootDir);
        }
        catch(IOException e) {
          e.printStackTrace();
        }
        System.out.println("Tree size is: " + sumOfPositiveReturns);
        break;

      default:
        System.out.println("Quitting");
        break;
    }
  }
}
