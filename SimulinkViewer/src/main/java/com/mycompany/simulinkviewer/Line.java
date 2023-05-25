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
    private int numberOfPoints = 0;
    ArrayList<Integer> bendsX = new ArrayList<Integer>();
    ArrayList<Integer> bendsY = new ArrayList<Integer>();

    public Line(String content){
        Num++;
        src = Integer.parseInt(content.substring(content.indexOf("Name=\"Src\">")+11,content.indexOf("#out",content.indexOf("Name=\"Src\">")+11)));
        //Check whether thers is a branch or not
        int index = 0;
        //Collect all the branches
        while(content.indexOf("<Branch>", index) != -1){
            numberOfBranch ++;
            branches.add(new Branch(content.substring(index = content.indexOf("<Branch>",index), content.indexOf("</Branch>", index) + 9)));
            index = content.indexOf("<Branch>", index) + 1;
        }

        //If there is no branchs get the distination 
        //We will draw the line from the block to the block
        index = 0;
        if(content.indexOf("Points", index) != -1){
            numberOfPoints ++;
            index = content.indexOf("Points", index);


            while (content.indexOf(";", index) != -1){
                index = content.indexOf(";", index) + 1;
                numberOfPoints ++;
            }

            index = content.indexOf("Points");
            for (int i = 0; i < numberOfPoints ; i++){
                if (i > 0){
                    index = content.indexOf(";", index) + 1;
                }
                if (i == 0){
                    bendsX.add(Integer.parseInt(content.substring(content.indexOf("Points") + 9,content.indexOf(",",content.indexOf("Points")))));
                } else {
                    bendsX.add(Integer.parseInt(content.substring(index + 1,content.indexOf(",",index))));
                }
                if (i == (numberOfPoints -1)) {
                    bendsY.add(Integer.parseInt(content.substring(content.indexOf(",", index)+2, content.indexOf("]",index))));
                } else if (i == 0){
                    bendsY.add(Integer.parseInt(content.substring(content.indexOf(",", content.indexOf("Points"))+2,content.indexOf(";",content.indexOf("Points")))));
                }else {
                    bendsY.add(Integer.parseInt(content.substring(content.indexOf(",", index)+2,content.indexOf(";",index))));
                }
            }


        }

        if (numberOfBranch == 0) {
            dst = Integer.parseInt(content.substring(content.indexOf("Dst")+5,content.indexOf("#in",content.indexOf("Dst")+5)));
        }
    }
    public boolean hasBranch() {
        return numberOfBranch != 0;
    }

    public boolean hasBends(){
        return numberOfPoints!= 0;
    }
    
    public static int getNum() {
        return Num;
    }

    public int getSrc() {
        return src;
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }

    public int getDst() {
        return dst;
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public int getNumberOfBranch() {
        return numberOfBranch;
    }

    public int getNumberOfBends() {
        return numberOfPoints;
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

    public int getDst() {
        return dst;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
