package data;

import log.Logger;
import log.MESSAGE;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/8/16.
 */
public class DataSet {
    private ArrayList<String> features;
    private ArrayList<ArrayList<String>> data;
    private int rows; //Number of rows in data set
    private int cols; //Number of columns in data set

    //Constructors
    public DataSet(ArrayList<String> features, ArrayList<ArrayList<String>> data){
        this.features = features;
        this.data = data;
        this.rows = data.get(0).size();
        this.cols = data.size();
        Logger.log(MESSAGE.SUCCESS, "Data set created", "DataSet -> Constructor");
    }

    public DataSet(String[] features, String[][] data){
        this.features = new ArrayList<String>();
        this.data = new ArrayList<ArrayList<String>>();

        this.rows = data.length;
        this.cols = data[0].length;

        this.features = new ArrayList<String>(Arrays.asList(features));

        for(int i=0; i<this.cols; i++){
            String[] list = new String[this.rows];
            for(int j=0; j<this.rows; j++){
                list[j] = data[j][i];
            }
            this.data.add(new ArrayList<String>(Arrays.asList(list)));
        }
        Logger.log(MESSAGE.SUCCESS, "Data set created", "DataSet -> Constructor");
    }

    //Add Feature
    public void addFeature(String headerName){
        this.features.add(headerName);
        this.cols = this.features.size();
    }

    public void addFeature(int columnIndex, String headerName){
        this.features.add(columnIndex, headerName);
        this.cols = this.features.size();
    }

    //Add data column
    public void addDataColumn(ArrayList<String> column){
        if(column.size() == this.rows){
            data.add(column);
        }else{
            Logger.log(MESSAGE.ERROR, "Number of rows mismatched with original data set",
                    "DataSet-> addColumn(column)");
        }
    }

    public void addDataColumn(int columnIndex, ArrayList<String> column){
        if(column.size() == this.rows){
            if(columnIndex >= 0 && columnIndex < this.cols){
                data.add(column);
            }else{
                Logger.log(MESSAGE.ERROR, "Column index out of range",
                        "DataSet->addDataColumn(columnIndex, Column)");
            }
        }else{
            Logger.log(MESSAGE.ERROR, "Number of rows mismatched with original data set",
                    "DataSet-> addDataColumn(columnIndex, column)");
        }
    }

    //Getters
    public ArrayList<String> getFeatures() {
        return features;
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public ArrayList<String> getDataColumn(String featureName){
        int index = features.indexOf(featureName);
        if(index == -1){
            return null;
        }
        return data.get(index);
    }

    public int getRowCount() {
        this.rows = this.data.get(0).size();
        return this.rows;
    }

    public int getColumnCount() {
        this.cols = this.features.size();
        return this.cols;
    }

    private void updateDimensions(){
        this.cols = this.features.size();
        this.rows = this.data.get(0).size();
    }

    public DataSet subset(int window){
        updateDimensions();

        final int noOfIDs = 100;

        ArrayList<ArrayList<String>> subsetData = new ArrayList<ArrayList<String>>();
        ArrayList<String> classNumbers = data.get(0);

        for(String sourceHeader: this.features){
            int index = features.indexOf(sourceHeader);
            if(index == -1){
                //Column cannot be found
                Logger.log(MESSAGE.WARNING, sourceHeader +
                        " column cannot be found", "DataSet -> subset");
                continue;
            }
            ArrayList<String> columnData = data.get(index);
            ArrayList<String> subsetColumnData = new ArrayList<String>();

            int loc = 0;
            for(int id=1; id<=noOfIDs; id++){
                int start = 0, end = 0;

                //Find start position
                while(loc < this.rows){
                    if(String.valueOf(id).equals(classNumbers.get(loc))){
                        start = loc;
                        break;
                    }
                    loc++;
                }

                //Find end position
                while(loc < this.rows && String.valueOf(id).equals(classNumbers.get(loc))){
                    end = loc;
                    loc++;
                }

                //Add standard deviation following values
                int pos = start + window - 1;
                while(pos <= end){
                    subsetColumnData.add(columnData.get(pos));
                    pos++;
                }
            }
            subsetData.add(subsetColumnData);
        }
        return new DataSet(this.features, subsetData);
    }

    public DataRow getDataRow(String featureName, String featureValue){
        //Get first row where same feature value occur
        updateDimensions();

        int featureIndex = this.features.indexOf(featureName);
        if(featureIndex == -1){
            Logger.log(MESSAGE.ERROR, featureName + " feature name cannot be found", "DataSet -> getDataRow");
            return null;
        }
        int rowIndex = getDataColumn(featureName).indexOf(featureValue);
        if(rowIndex == -1){
            Logger.log(MESSAGE.ERROR, featureValue + " feature value cannot be found", "DataSet -> getDataRow");
            return null;
        }

        ArrayList<String> dataRow = new ArrayList<String>();

        for(int i=0; i<this.features.size(); i++){
            dataRow.add(this.data.get(i).get(rowIndex));
        }

        return new DataRow(this.features, dataRow);
    }

    public ArrayList<DataRow> getDataRows(String featureName, String featureValue){
        updateDimensions();

        //Get all rows where same feature value occur
        int featureIndex = this.features.indexOf(featureName);
        if(featureIndex == -1){
            Logger.log(MESSAGE.ERROR, featureName + " feature name cannot be found", "DataSet -> getDataRow");
            return null;
        }

        int rowIndex = getDataColumn(featureName).indexOf(featureValue);
        if(rowIndex == -1){
            Logger.log(MESSAGE.ERROR, featureValue + " feature value cannot be found", "DataSet -> getDataRow");
            return null;
        }

        ArrayList<String> columnData = getDataColumn(featureName);
        ArrayList<DataRow> dataRows = new ArrayList<DataRow>();

        while(rowIndex < this.rows){
            ArrayList<String> dataRow = new ArrayList<String>();
            if(featureValue.equals(columnData.get(rowIndex))){
                for(int i=0; i < this.cols; i++){
                    dataRow.add(this.data.get(i).get(rowIndex));
                }
                dataRows.add(new DataRow(this.features, dataRow));
            }
            rowIndex++;
        }
        return dataRows;
    }

    public ArrayList<DataRow> getDataRows(){
        updateDimensions();
        ArrayList<DataRow> dataRows = new ArrayList<DataRow>();
        for(int j=0; j < this.rows; j++){
            ArrayList<String> dataRow = new ArrayList<String>();

            for(int i=0; i < this.cols; i++){
                dataRow.add(this.data.get(i).get(j));
            }
            dataRows.add(new DataRow(this.features, dataRow));
        }
        return dataRows;
    }

    //Feature Engineering
    public void addBinaryClassification(int window){
        updateDimensions();
        final int noOfIDs = 100;

        ArrayList<String> binary = new ArrayList<String>();

        //Filter by id column
        ArrayList<String> idColumn = data.get(0);
        int loc = 0;

        for(int id = 1; id <= noOfIDs; id++){
            int start = 0, end = 0;

            //Find start position
            while(loc < this.rows){
                if(String.valueOf(id).equals(idColumn.get(loc))){
                    start = loc;
                    break;
                }
                loc++;
            }

            //Find end position
            while(loc < this.rows && String.valueOf(id).equals(idColumn.get(loc))){
                end = loc;
                loc++;
            }

            //Add normal behaviour
            int pos = start;
            while(pos < (end-window) + 1){
                binary.add(String.valueOf(0));
                pos++;
            }

            //Add failure behaviour
            while(pos<end+1){
                binary.add(String.valueOf(1));
                pos++;
            }
        }

        //Add features
        addFeature("Binary-Classification");
        //Add data column
        addDataColumn(features.indexOf("Binary-Classification"), binary);
        //Log
        Logger.log(MESSAGE.SUCCESS, "Binary classification complete. Window : " +
                        String.valueOf(window) + " Rows : " + String.valueOf(rows),
                "DataSet -> addBinaryClassification");
    }

    public void addMovingAverage(int window, String[] features, boolean defaultInitials){
        final int noOfIDs = 100;
        ArrayList<String> classNumbers = data.get(0);

        for(String sourceHeader: features){
            String resultHeader = "ma-"+sourceHeader;
            int index = this.features.indexOf(sourceHeader);

            if(index == -1){
                //Column cannot be found
                Logger.log(MESSAGE.WARNING, sourceHeader +
                        " column cannot be found", "DataSet -> addMovingAverage");
                continue;
            }

            ArrayList<String> sensorReadings = data.get(index);
            ArrayList<String> movingAverage = new ArrayList<String>();

            int loc = 0;
            for(int id=1; id<=noOfIDs; id++){
                int start = 0, end = 0;

                //Find start position
                while(loc < this.rows){
                    if(String.valueOf(id).equals(classNumbers.get(loc))){
                        start = loc;
                        break;
                    }
                    loc++;
                }

                //Find end position
                while(loc < this.rows && String.valueOf(id).equals(classNumbers.get(loc))){
                    end = loc;
                    loc++;
                }

                int pos;
                if(defaultInitials){
                    pos = start;
                    //Default values for the initial values
                    while(pos < start+window-1){
                        movingAverage.add(sensorReadings.get(pos));
                        pos++;
                    }
                }

                //Add moving average following values
                pos = start + window - 1;
                while(pos <= end){
                    Double tot = 0.0;
                    for(int i = pos-window+1; i <= pos; i++){
                        tot += Double.parseDouble(sensorReadings.get(i));
                    }
                    movingAverage.add(String.valueOf(tot/window));
                    pos++;
                }
            }

            //Add column header
            addFeature(resultHeader);
            //Add data column
            addDataColumn(this.features.indexOf(resultHeader),movingAverage);
        }
        //Log
        Logger.log(MESSAGE.SUCCESS, "Moving average calculate complete. Window : " +
                        String.valueOf(window) + " Rows : " + String.valueOf(rows),
                "DataSet -> addMovingAverage");
    }

    public void addStandardDeviation(int window, String[] features, boolean defaultInitials){
        final int noOfIDs = 100;
        ArrayList<String> classNumbers = data.get(0);

        for(String sourceHeader: features){
            String resultHeader = "sd-"+sourceHeader;
            int index = this.features.indexOf(sourceHeader);
            if(index == -1){
                //Column cannot be found
                Logger.log(MESSAGE.WARNING, sourceHeader +
                        " column cannot be found", "DataSet -> addStandardDeviation");
                continue;
            }
            ArrayList<String> sensorReadings = data.get(index);
            ArrayList<String> standardDeviation = new ArrayList<String>();

            int loc = 0;
            for(int id=1; id<=noOfIDs; id++){
                int start = 0, end = 0;

                //Find start position
                while(loc < this.rows){
                    if(String.valueOf(id).equals(classNumbers.get(loc))){
                        start = loc;
                        break;
                    }
                    loc++;
                }

                //Find end position
                while(loc < this.rows && String.valueOf(id).equals(classNumbers.get(loc))){
                    end = loc;
                    loc++;
                }

                int pos;
                if(defaultInitials){
                    pos = start;
                    //Default values for the initial values
                    while(pos < start+window-1){
                        //Initial standard deviations are considered as 0
                        standardDeviation.add(String.valueOf(0));
                        pos++;
                    }
                }

                //Add standard deviation following values
                pos = start + window - 1;
                while(pos <= end){
                    Double tot = 0.0; //Total
                    Double avg = 0.0; //Average value
                    Double var = 0.0; //Standard deviation
                    for(int i = pos-window+1; i <= pos; i++){
                        tot += Double.parseDouble(sensorReadings.get(i));
                    }
                    avg = tot / window;
                    for(int i = pos-window+1; i <= pos; i++){
                        var += Math.pow((Double.parseDouble(sensorReadings.get(i))-avg), 2);
                    }
                    standardDeviation.add(String.valueOf(Math.sqrt(var/window)));
                    pos++;
                }
            }

            //Add column header
            addFeature(resultHeader);
            //Add data column
            addDataColumn(this.features.indexOf(resultHeader),standardDeviation);
        }
        //Log
        Logger.log(MESSAGE.SUCCESS, "Standard deviation calculate complete. Window : " +
                        String.valueOf(window) + " Rows : " + String.valueOf(rows),
                "DataSet -> addStandardDeviation");
    }

    public void addMovingMedian(int window, String[] features, boolean defaultInitials){
        final int noOfIDs = 100;
        ArrayList<String> classNumbers = data.get(0);

        for(String sourceHeader: features) {
            String resultHeader = "mm-" + sourceHeader;
            int index = this.features.indexOf(sourceHeader);

            if (index == -1) {
                //Column cannot be found
                Logger.log(MESSAGE.WARNING, sourceHeader +
                        " column cannot be found", "DataSet -> addMovingMedian");
                continue;
            }

            ArrayList<String> sensorReadings = data.get(index);
            ArrayList<String> movingMedian = new ArrayList<String>();

            int loc = 0;
            for (int id = 1; id <= noOfIDs; id++) {
                int start = 0, end = 0;

                //Find start position
                while (loc < this.rows) {
                    if (String.valueOf(id).equals(classNumbers.get(loc))) {
                        start = loc;
                        break;
                    }
                    loc++;
                }

                //Find end position
                while (loc < this.rows && String.valueOf(id).equals(classNumbers.get(loc))) {
                    end = loc;
                    loc++;
                }

                int pos;
                if (defaultInitials) {
                    pos = start;
                    //Default values for the initial values
                    while (pos < start + window - 1) {
                        movingMedian.add(sensorReadings.get(pos));
                        pos++;
                    }
                }


                //Add moving median following values
                pos = start + window - 1;
                while (pos <= end) {
                    Double val = 0.0;
                    ArrayList<Double> tempArray = new ArrayList<Double>();
                    for (int i = pos - window + 1; i <= pos; i++) {
                        val = Double.parseDouble(sensorReadings.get(i));
                        tempArray.add(val);
                    }
                    tempArray.sort((a,b) -> Double.compare(b,a));
                    if(window % 2 == 0){
                        double mean = (tempArray.get(window/2) + tempArray.get(window/2 - 1)) / 2.0;
                        movingMedian.add(String.valueOf(mean));
                    }else{
                        movingMedian.add(String.valueOf(tempArray.get(window/2)));
                    }
                    pos++;
                }
            }

            //Add column header
            addFeature(resultHeader);
            //Add data column
            addDataColumn(this.features.indexOf(resultHeader), movingMedian);
        }
        //Log
        Logger.log(MESSAGE.SUCCESS, "Moving median calculate complete. Window : " +
                        String.valueOf(window) + " Rows : " + String.valueOf(rows),
                "DataSet -> addMovingMedian");
    }

    public void normalize(String features[]){
        final int noOfIDs = 100;
        ArrayList<String> classNumbers = data.get(0);

        for(String sourceHeader: features) {
            String resultHeader = "mm-" + sourceHeader;
            int index = this.features.indexOf(sourceHeader);

            if (index == -1) {
                //Column cannot be found
                Logger.log(MESSAGE.WARNING, sourceHeader +
                        " column cannot be found", "DataSet -> addMovingMedian");
                continue;
            }

            ArrayList<String> sensorReadings = data.get(index);

            int loc = 0;
            for (int id = 1; id <= noOfIDs; id++) {
                int start = 0, end = 0;

                //Find start position
                while (loc < this.rows) {
                    if (String.valueOf(id).equals(classNumbers.get(loc))) {
                        start = loc;
                        break;
                    }
                    loc++;
                }

                double maxValue = Double.parseDouble(sensorReadings.get(start));
                //Find end position
                while (loc < this.rows && String.valueOf(id).equals(classNumbers.get(loc))) {
                    double currentValue = Double.parseDouble(sensorReadings.get(loc));
                    if(maxValue < currentValue){
                        maxValue = currentValue;
                    }
                    end = loc;
                    loc++;
                }

                //Add moving median following values
                int pos = start;
                while (pos <= end) {
                    sensorReadings.set(pos, String.valueOf(Double.parseDouble(sensorReadings.get(pos))/maxValue));
                    pos++;
                }
            }

            //Add data column
            addDataColumn(this.features.indexOf(sourceHeader), sensorReadings);
        }
        //Log
        Logger.log(MESSAGE.SUCCESS, "Data set normalization complete", this.getClass().getName(), "normalize");

    }


    public void addRemainingUsefulLife(){
        final int noOfIDs = 100;

        ArrayList<String> lifeTime = data.get(features.indexOf("Time"));
        ArrayList<String> remainingTime = new ArrayList<String>();
        ArrayList<String> classNumbers = data.get(features.indexOf("UnitNumber"));

        int loc = 0;
        for(int id=1; id<=noOfIDs; id++){
            int start = 0, end = 0;

            //Find start position
            while(loc < this.rows){
                if(String.valueOf(id).equals(classNumbers.get(loc))){
                    start = loc;
                    break;
                }
                loc++;
            }

            //Find end position
            while(loc < this.rows && String.valueOf(id).equals(classNumbers.get(loc))){
                end = loc;
                loc++;
            }

            for(int i=end; i>=start; i--){
                Integer cycle = Integer.parseInt(lifeTime.get(i));
                remainingTime.add(String.valueOf(cycle-1));
            }
        }

        //Add header
        addFeature("RUL");
        //Add data column
        addDataColumn(features.indexOf("RUL"), remainingTime);
    }
}
