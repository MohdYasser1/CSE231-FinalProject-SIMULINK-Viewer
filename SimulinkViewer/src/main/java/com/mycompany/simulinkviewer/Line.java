package com.mycompany.simulinkviewer;

import java.util.ArrayList;

/**
 *
 * @author mohdyassser
 */
public class Line{
    //We need to count the number of branches in each line
    private static int Num;
    private int src;
    private ArrayList<Branch> branches = new ArrayList<Branch>();
    private int dst;
    private int lengthX;
    private int lengthY;
    private int numberOfBranch = 0;
    private boolean isBranch = false; //The default is there is no branch

    public Line(String content){
        Num++;
        src = Integer.parseInt(content.substring(content.indexOf("Name=\"Src\">")+11,content.indexOf("#out",content.indexOf("Name=\"Src\">")+11)));
        //Check whether thers is a branch or not
        int index = 0;
        //Collect all the branches
        while(content.indexOf("<Branch>", index) != -1){
            isBranch = true;
            numberOfBranch ++;
            branches.add(new Branch(content.substring(index = content.indexOf("<Branch>",index), content.indexOf("</Branch>", index) + 9)));
            index = content.indexOf("<Branch>", index) + 1;
        }
        //If there is no branchs get the distination 
        //We will draw the line from the block to the block
        if (isBranch == false) {
            dst = Integer.parseInt(content.substring(content.indexOf("Dst")+5,content.indexOf("#in",content.indexOf("Dst")+5)));
        }
        //if there is a branch get the length of the main branch
        else{
            lengthX = Integer.parseInt(content.substring(content.indexOf("Points")+9,content.indexOf(",",content.indexOf("Points")+9)));
            lengthY = Integer.parseInt(content.substring(content.indexOf(",")+2,content.indexOf("]",content.indexOf(","))));
        }
}
    class Branch{
        private int dst;
        private int offsetX;
        private int offsetY;
        public boolean isOffset = false; 

        public Branch(String Branchcontent){
            
            //Get the distantion of the branch there should be one distanation to a branch
            dst = Integer.parseInt(Branchcontent.substring(Branchcontent.indexOf("\"Dst\">")+6,Branchcontent.indexOf("#in",Branchcontent.indexOf("\"Dst\">")+6)));
            //The branch is offset from the source 
            if(Branchcontent.indexOf("Points") != -1){
                isOffset = true;
                //Get the offset in X and Y coordinates
                offsetX = Integer.parseInt(Branchcontent.substring(Branchcontent.indexOf("Points")+9,Branchcontent.indexOf(",",Branchcontent.indexOf("Points")+9)));
                offsetY = Integer.parseInt(Branchcontent.substring(Branchcontent.indexOf(",",Branchcontent.indexOf("Points")+9)+2,Branchcontent.indexOf("]",Branchcontent.indexOf("Points")+9)));
            }
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
