package com.mycompany.simulinkviewer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*; 
import javafx.scene.shape.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;




/*
Ahmed/Omar TODO:
class Block:
    static int Num:number of blocks.
    static int getNum(): a static method which returns the number of blocks.
    int getSID()
    //Getting the position of the block
    //If you have a better idea go ahead
    int getLeft()
    int getTop()
    int getRight()
    int getBottom()

    int getMinLeft(): Gets the minimum left position of a block to place it first and to be a refrence
    
*/
class Block{
    private static int Num;
    private int SID;
    private String name;
    private int left;
    private int top;
    private int right;
    private int bottom;
    
    //Given a blocks content this constructor should divide it into its important parts
    public Block(String content){
        Num++;
        SID = Integer.parseInt(content.substring(content.indexOf("SID=")+5,content.indexOf("\"",content.indexOf("SID=")+5)));
    }
    
    public static int getNum(){
        return Num;
    }
}
public class App extends Application {

    @Override
    public void start(Stage stage) {
        //Create a Stack pane because I think it is the best option
        //-->But we have to make sure that the blocks are not overlapping.
        Pane pane = new Pane();


        // Block Add
        Rectangle blockAdd = new Rectangle(1040, 209, 30, 32);
        pane.getChildren().add(blockAdd);

        // Block Constant
        Rectangle blockConstant = new Rectangle(780, 200, 30, 30);
        pane.getChildren().add(blockConstant);

        // Block Saturation
        Rectangle blockSaturation = new Rectangle(935, 200, 30, 30);
        pane.getChildren().add(blockSaturation);

        // Block Scope
        Rectangle blockScope = new Rectangle(1130, 209, 30, 32);
        pane.getChildren().add(blockScope);

        // Block Unit Delay
        Rectangle blockUnitDelay = new Rectangle(1040, 283, 35, 34);
        pane.getChildren().add(blockUnitDelay);
        
        Scene scene = new Scene(pane, 640, 480);//What are the window size we want ? --> 640, 480
        /*
        According to the mdl file there is position wish indicates the position on the screen
        with a [left top right bottom] format starting with [0 0 0 0] at the left upper corner
        which may be benificial as the java format also start 0,0 in the upper left 
        I think that this default window is suitable and we will apply the coordinates accordinaly
        */
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String fileContent = getFileContent("src/main/java/com/mycompany/simulinkviewer/Example.mdl");
//        String fileContent = getFileContent(input.nextLine());
        //String fileContent = getFileContent(args[0]);
        if(fileContent == null){
            throw new EmptyMDLFileException("The .mdl file is EMPTY");
        }
        createBlocks(fileContent);
        launch();
    }

    public static double translatePosition(double position){
        return 0;
    }
    
    
    public static String getFileContent(String filename){
        //Reuse Assignment 6
        try {
            //Checking if we have the right extension
//            if(false){
//            if(!filename.endsWith(".mdl")){
//                throw new NotValidMDLFileException("This file doesn't has the .mdl extension");
//            }}
            //Input the file
            File inputfile = new File(filename);
            //Buffered read to read the entire file
            BufferedReader readfile = new BufferedReader(new FileReader(inputfile));
            StringBuilder data = new StringBuilder();
            String line;
            //Checking if the file is not empty
//            if(inputfile.length() == 0){
//                throw new EmptyMDLFileException("The .mdl file is EMPTY");
//            }
            while((line = readfile.readLine()) != null){
                data.append(line);
                data.append("\n");
            }
            String fileString = data.toString();
            return fileString;
//        } catch (NotValidMDLFileException e) {
//                System.out.println("Not valid .mdl file: " +e.getMessage()); 
// 
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
//        } catch (EmptyMDLFileException e){
//            System.out.println("Empty arxml file: " +e.getMessage()); 
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
/*
    Do I want to create a retrun typr to return the array list
    How to transfer the Blocks to the Application to draw the blocks
*/
    public static void createBlocks(String fileContent){
        ArrayList<Block> blocks = new ArrayList<Block>();
        int index = fileContent.indexOf("<System>");
        while(fileContent.indexOf("<Block BlockType",index) != -1){
            blocks.add(new Block(fileContent.substring(index = fileContent.indexOf("<Block BlockType",index), fileContent.indexOf("</Block>", index) + 8)));
            index = fileContent.indexOf("<Block BlockType", index) + 1;
        }
        //We have an ArrayList of blocks

    }
}
class NotValidMDLFileException extends Exception{
    public NotValidMDLFileException(String message){
        super(message);
    }
}
//A unchecked exception that triggers when the file is empty
class EmptyMDLFileException extends RuntimeException{
    public EmptyMDLFileException(String message){
        super(message);
    }
}

