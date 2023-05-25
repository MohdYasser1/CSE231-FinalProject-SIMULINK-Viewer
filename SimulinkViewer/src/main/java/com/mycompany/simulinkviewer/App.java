package com.mycompany.simulinkviewer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*; 
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.geometry.*;
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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////   Application  ///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scanner input = new Scanner(System.in);
        String fileContent = getFileContent("src/main/java/com/mycompany/simulinkviewer/Example.mdl");
        // String fileContent = getFileContent(input.nextLine());
        //String fileContent = getFileContent(args[0]);

        //We have 2 ArrayLists, One for blocks and One for lines.
        ArrayList<Block> blocks = createBlocks(fileContent);
        ArrayList<Line> lines = createLines(fileContent);


        Pane pane = new Pane();

        int minLeft = getLeftBorder(blocks);

        //Shifting all the elements to fit in the pane
        int leftShift = minLeft - 100;

        for (int i = 0; i < blocks.size(); i++) {
            int left = blocks.get(i).getLeft() - leftShift;
            int top =  blocks.get(i).getTop();
            int width = blocks.get(i).getRight() - left - leftShift;
            int height = blocks.get(i).getBottom() - top;
            Rectangle rect = new Rectangle(left, top, width, height);            
            rect.setFill(Color.WHITE);
            rect.setStrokeWidth(3.0);
            rect.setStroke(Color.rgb(0, 204, 204));
            pane.getChildren().add(rect);
            Text text = new Text(blocks.get(i).getName());
            
            text.setLayoutX(rect.getX() + (rect.getWidth()-text.getLayoutBounds().getWidth())/2);
            text.setLayoutY(top + width + 20);
 
            pane.getChildren().add(text);
        }
        for (int i = 0; i < lines.size(); i++) {

            // Path arrow = new Path();
                
            int start = 0;
            int end = 0;

            //Get the index of the source
            for (int j = 0; j < blocks.size(); j++) {
                if (lines.get(i).getSrc() == blocks.get(j).getSID()) {
                    start = j;
                    break;
                }
            }
            
      
            // Set the starting point of the arrow
            double startX = blocks.get(start).getRight() - leftShift;
            double startY = blocks.get(start).getTop() + ((blocks.get(start).getBottom() - blocks.get(start).getTop())/2);
            double endX = 0;
            double endY = 0;
            Polygon arrowHead = new Polygon();

            if (!lines.get(i).hasBranch()) {
               
                //Get the index of the distination
                for (int j = 0; j < blocks.size(); j++) {
                    if (lines.get(i).getDst() == blocks.get(j).getSID()) {
                        end = j;
                        break;
                    }
                }
                // Draw the straight arrow line
                endX = blocks.get(end).getLeft() - leftShift -3;
                endY = blocks.get(end).getTop() + ((blocks.get(end).getBottom() - blocks.get(end).getTop())/2);
            
  
                javafx.scene.shape.Line line = new javafx.scene.shape.Line(startX, startY, endX, endY);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLACK);
    
                double arrowHeadSize = 10;

                arrowHead.getPoints().addAll(
                    endX - arrowHeadSize, endY - arrowHeadSize / 2,
                    endX, endY,
                    endX - arrowHeadSize, endY + arrowHeadSize / 2
                );
                arrowHead.setFill(Color.BLACK);
            if (lines.get(i).hasBends()) {
                    if (lines.get(i).bendsX.get(0) < 0){
                        startX = blocks.get(start).getLeft() - leftShift;
                        line = new javafx.scene.shape.Line(startX, startY, endX, endY);
                        line.setStrokeWidth(2);
                        line.setStroke(Color.BLACK);
                    }
                    double startXbend = startX;
                    double startYbend = startY;
                    double endXbend = 0;
                    double endYbend = 0;
                    
                    for (int j = 0; j < lines.get(i).getNumberOfBends(); j++){
                        if ((Math.abs(lines.get(i).bendsX.get(j)) < 10) && (Math.abs(lines.get(i).bendsX.get(j)) > 0)){
                            if (lines.get(i).bendsX.get(j) < 0){
                                endXbend = startXbend + lines.get(i).bendsX.get(j) - 10;
                            } else {
                                endXbend = startXbend + lines.get(i).bendsX.get(j) + 10;
                            }
                        }
                        endYbend = startYbend + lines.get(i).bendsY.get(j);
                        javafx.scene.shape.Line linebend = new javafx.scene.shape.Line(startXbend, startYbend, endXbend, endYbend);
                        linebend.setStrokeWidth(2);
                        linebend.setStroke(Color.BLACK);
                        pane.getChildren().add(linebend);
                        startXbend = endXbend;
                        startYbend = endYbend;
                    }
                    startX = startXbend;
                    startY = startYbend;
                    endY = endYbend;
                    
                    line = new javafx.scene.shape.Line(startX, startY, endX, endY);
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    
                    arrowHeadSize = 10;
                    arrowHead.getPoints().addAll(
                        endX - arrowHeadSize, endY - arrowHeadSize / 2,
                        endX, endY,
                        endX - arrowHeadSize, endY + arrowHeadSize / 2
                    );
                    arrowHead.setFill(Color.BLACK);

                
                }
            pane.getChildren().addAll(line, arrowHead);         
        }

        else if (lines.get(i).hasBranch()){
            for (int j = 0; j < lines.get(i).getBranches().size(); j++) {

                //Get the index of the distination
                for (int k = 0; k < blocks.size(); k++) {
                    if (lines.get(i).getBranches().get(j).getDst() == blocks.get(k).getSID()) {
                        end = k;
                        break;
                    }
                }
                startX = blocks.get(start).getRight() - leftShift;
                startY = blocks.get(start).getTop() + ((blocks.get(start).getBottom() - blocks.get(start).getTop())/2);
                endX =blocks.get(end).getLeft() - leftShift -3;
                endY = startY;
                if (!lines.get(i).getBranches().get(j).isOffset){
                    //Draw the main branch
                    javafx.scene.shape.Line line = new javafx.scene.shape.Line(startX, startY, endX, endY);
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);

                    double arrowHeadSize = 10;
                    arrowHead = new Polygon();
                    arrowHead.getPoints().addAll(
                        endX - arrowHeadSize, endY - arrowHeadSize / 2,
                        endX, endY,
                        endX - arrowHeadSize, endY + arrowHeadSize / 2
                    );
                    arrowHead.setFill(Color.BLACK);
                    pane.getChildren().addAll(line, arrowHead);
                    
                } else {
                    for (int k = 0; k < lines.get(i).getNumberOfBends(); k++){
                        Circle branchPoint = null;
                        double endBendX = 0;
                        double endBendY = 0;
                        if (Math.abs(lines.get(i).bendsX.get(j)) > 0){
                            startX = startX + lines.get(i).bendsX.get(j);
                            endBendX = startX;
                            branchPoint = new Circle(startX, startY, 3); 
                        }
                        if (Math.abs(lines.get(i).bendsY.get(j)) > 0){
                            startY = startY + lines.get(i).bendsY.get(j);
                            endBendY = startY;
                            branchPoint = new Circle(startX , startY, 3);
                        }
                         pane.getChildren().add(branchPoint);
                        if (Math.abs(lines.get(i).getBranches().get(j).getOffsetX()) > 0) {
                            endBendX = endX + lines.get(i).getBranches().get(j).getOffsetX();
                        }
                        if (Math.abs(lines.get(i).getBranches().get(j).getOffsetY()) > 0) {
                            endBendY = endY + lines.get(i).getBranches().get(j).getOffsetY();
                        }   
                        javafx.scene.shape.Line line = new javafx.scene.shape.Line(startX, startY, endBendX, endBendY);
                        line.setStrokeWidth(2);
                        line.setStroke(Color.BLACK);
                        pane.getChildren().add(line);
                        startX = endBendX;
                        startY = endBendY;
                    }    
                    
                    if (Math.abs(lines.get(i).getBranches().get(j).getOffsetX()) > 0) {
                        endX = endX + lines.get(i).getBranches().get(j).getOffsetX();
                    }
                    if (Math.abs(lines.get(i).getBranches().get(j).getOffsetY()) > 0) {
                        endY = endY + lines.get(i).getBranches().get(j).getOffsetY();
                    }
                    if (startX < (blocks.get(end).getRight() - leftShift -3)){
                        javafx.scene.shape.Line line = new javafx.scene.shape.Line(startX, startY, endX, endY);
                        line.setStrokeWidth(2);
                        line.setStroke(Color.BLACK);
                        pane.getChildren().add(line);
                        arrowHead = new Polygon();
                        double arrowHeadSize = 10;

                            arrowHead.getPoints().addAll(
                                endX - arrowHeadSize, endY - arrowHeadSize / 2,
                                endX, endY,
                                endX - arrowHeadSize, endY + arrowHeadSize / 2
                            );
                            arrowHead.setFill(Color.BLACK);
                        pane.getChildren().add(arrowHead);
                    } else {
                        endX =  blocks.get(end).getRight() - leftShift;
                        javafx.scene.shape.Line line = new javafx.scene.shape.Line(startX, startY, endX, endY);
                        line.setStrokeWidth(2);
                        line.setStroke(Color.BLACK);
                        pane.getChildren().add(line);
                        arrowHead = new Polygon();
                        double arrowHeadSize = 10;

                        arrowHead.getPoints().addAll(
                                endX + arrowHeadSize, endY - arrowHeadSize / 2,
                                endX, endY,
                                endX + arrowHeadSize, endY + arrowHeadSize / 2
                        );
                        pane.getChildren().add(arrowHead);
                    }

                }

               
                
            }
        }
}


        Scene scene = new Scene(pane, 640, 480);//What are the window size we want ? --> 640, 480
        /*
        According to the mdl file there is position wish indicates the position on the screen
        with a [left top right bottom] format starting with [0 0 0 0] at the left upper corner
        which may be benificial as the java format also start 0,0 in the upper left 
        I think that this default window is suitable and we will apply the coordinates accordinaly
        */
        stage.setTitle("SIMULINK Viewer");
        stage.setScene(scene);
   
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
    
public static String getFileContent(String filename){
    //Reuse Assignment 6
    try {
        //Checking if we have the right extension
        if(!filename.endsWith(".mdl")){
            throw new NotValidMDLFileException("This file doesn't has the .mdl extension");
        }
        //Input the file
        File inputfile = new File(filename);
        //Buffered read to read the entire file
        BufferedReader readfile = new BufferedReader(new FileReader(inputfile));
        StringBuilder data = new StringBuilder();
        String line;
        //Checking if the file is not empty
        if(inputfile.length() == 0){
            throw new EmptyMDLFileException("The .mdl file is EMPTY");
        }
        while((line = readfile.readLine()) != null){
            data.append(line);
            data.append("\n");
        }
        String fileString = data.toString();
        return fileString;
    } catch (NotValidMDLFileException e) {
            System.out.println("Not valid .mdl file: " +e.getMessage());  
    } catch (FileNotFoundException e){
        System.out.println(e.getMessage());
    } catch (EmptyMDLFileException e){
        System.out.println("Empty arxml file: " +e.getMessage()); 
    } catch (IOException e){
        System.out.println(e.getMessage());
    }
    return null;
}


    public static ArrayList createBlocks(String fileContent){
        ArrayList<Block> blocks = new ArrayList<Block>();
        int index = fileContent.indexOf("<System>");
        while(fileContent.indexOf("<Block BlockType",index) != -1){
            blocks.add(new Block(fileContent.substring(index = fileContent.indexOf("<Block BlockType",index), fileContent.indexOf("</Block>", index) + 8)));
            index = fileContent.indexOf("<Block BlockType", index) + 1;
        }
        //We have an ArrayList of blocks
        return blocks;
    }

    public static ArrayList createLines(String fileContent){
        ArrayList<Line> lines = new ArrayList<Line>();
        int index = fileContent.indexOf("<System>");
        while(fileContent.indexOf("<Line>",index) != -1){
            lines.add(new Line(fileContent.substring(index = fileContent.indexOf("<Line>",index), fileContent.indexOf("</Line>", index) + 8)));
            index = fileContent.indexOf("<Line>", index) + 1;
        }
        //We have an ArrayList of lines
        return lines;
    }


    public static int getLeftBorder(ArrayList blocks) {

        //An ArrayList of left to find the border of the blocks.
        ArrayList<Integer> leftBorder = new ArrayList<Integer>();
        
        for (int i = 0; i < blocks.size(); i++) {
            int left = ((Block) blocks.get(i)).getLeft();
            leftBorder.add(left);    
        }

        return Collections.min(leftBorder);
    }
}


//---------------------- Execptions --------------------------------
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
//--------------------------------------------------------------------
