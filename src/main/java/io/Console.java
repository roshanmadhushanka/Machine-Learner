package io;

/**
 * Created by wso2123 on 8/6/16.
 */
public class Console {
    public static void print(int row, int col, String msg){
        System.out.print(String.format("%c[%d;%df",msg,row,col));
    }
}
