
package com.mycompany.simulinkviewer;

/**
 *
 * @author mohdyassser
 */
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Class Block is done and works correctly :)
public class Block{
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
        name = content.substring(content.indexOf("Name=")+6,content.indexOf("\"",content.indexOf("Name=")+6));
        
        int startIndex = content.indexOf("\"Position\">[")+12;
        int endIndex=content.indexOf(",",startIndex);
        left = Integer.parseInt(content.substring(startIndex,endIndex));
        startIndex = endIndex + 2;
        endIndex=content.indexOf(",",startIndex);
        top = Integer.parseInt(content.substring(startIndex,endIndex));
        startIndex = endIndex + 2;
        endIndex=content.indexOf(",",startIndex);
        right = Integer.parseInt(content.substring(startIndex,endIndex));
        startIndex = endIndex + 2;
        endIndex=content.indexOf("]",startIndex);
        bottom = Integer.parseInt(content.substring(startIndex,endIndex));        
    }
    
    public static int getNum(){
        return Num;
    }

    public int getSID() {
        return SID;
    }

    public String getName() {
        return name;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
