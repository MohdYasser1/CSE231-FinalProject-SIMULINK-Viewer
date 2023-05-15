package com.mycompany.simulinkviewer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*; 
import javafx.scene.shape.*;



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
        launch();
    }

}