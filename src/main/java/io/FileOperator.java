package io;

import data.DataSet;
import log.Logger;
import log.MESSAGE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wso2123 on 8/8/16.
 */
public class FileOperator {
    private ArrayList<String> openFile(String filenName){
        String line = null;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(filenName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines;
        }
        catch(FileNotFoundException ex) {
            Logger.log(MESSAGE.ERROR, "File not found", "FileHandler -> openFile");
        }
        catch(IOException ex) {
            Logger.log(MESSAGE.ERROR, "File cannot read", "FileHandler -> openFile ");
        }
        return null;
    }

    public DataSet openCSVFile(String fileName){
        /*
            * First row the file considered as list of features
            * Following rows contain raw data
         */

        ArrayList<String> lines = openFile(fileName);
        if(lines == null){
            return null;
        }
        if(lines.size() > 1){
            //Feature names
            String header = lines.get(0);
            String[] variableNames = header.split(",");

            //Data set dimensions
            int rows = lines.size()-1;
            int cols = variableNames.length;

            String[] columnData = new String[cols];
            //All data
            String[][] data = new String[rows][cols];

            //Data
            for(int row=1; row<lines.size(); row++){
                columnData = lines.get(row).split(",");
                for(int col=0; col<cols; col++){
                    data[row-1][col] = columnData[col];
                }
            }
            return new DataSet(variableNames, data);
        }else{
            Logger.log(MESSAGE.INFO, "Invalid data set", "Filehandler -> openCSVFile");
        }
        return null;
    }

    public void saveCSVFile(String fileName, DataSet dataSet) throws IOException {
        int rows = dataSet.getRowCount();
        int cols = dataSet.getColumnCount();

        //Print header
        String header = "";
        if (cols == 1) {
            header = dataSet.getFeatures().get(0);
        } else if (cols > 1) {
            header = dataSet.getFeatures().get(0);
            for (int i = 1; i<dataSet.getFeatures().size(); i++) {
                header += "," + dataSet.getFeatures().get(i);
            }
        } else {
            Logger.log(MESSAGE.INFO, "Nothing to print", "FileOperator -> saveCSVFile");
            return;
        }

        FileWriter fileWriter;
        BufferedWriter bufferedWriter;

        //Initialize writing
        //Write headers
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(header);
            bufferedWriter.newLine();
        } catch (IOException ex) {
            Logger.log(MESSAGE.ERROR, "IO Error", "FileOperator -> saveCSVFile");
            return;
        }

        //Write data
        if (cols == 1) {
            for (String rowData : dataSet.getData().get(0)) {
                bufferedWriter.write(rowData);
                bufferedWriter.newLine();
            }
        } else if (cols > 1) {
            for (int j = 0; j < rows; j++) {
                String dataRow = dataSet.getData().get(0).get(j);
                for (int i = 1; i < cols; i++) {
                    dataRow += "," + dataSet.getData().get(i).get(j);
                }
                bufferedWriter.write(dataRow);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            Logger.log(MESSAGE.SUCCESS, fileName + " write complete", "FileOperator -> saveCSVFile");

        }
    }
}
