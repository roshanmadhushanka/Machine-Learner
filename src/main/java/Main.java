import connection.ServerConnection;
import data.DataSet;
import io.FileOperator;
import io.Mediator;
import io.RESTClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.ml.core.exceptions.MLModelHandlerException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by wso2123 on 8/6/16.
 */
public class Main {

    private static final Log logger = LogFactory.getLog(Main.class);

    public static void main(String[] args) throws MLModelHandlerException, URISyntaxException, IOException, ClassNotFoundException {
        //PredictiveModel predictiveModel = new PredictiveModel("RandomForest_3.Model.2016-08-11_09-17-48");
        //FileOperator fileOperator = new FileOperator();


        //Generate classification training data set
        //DataSet dataSet = fileOperator.openCSVFile("train1.csv");
        //dataSet.addBinaryClassification(5);
        //String[] headers = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        //dataSet.addMovingAverage(5, headers, true);
        //dataSet.addStandardDeviation(5, headers, true);
        //DataSet subset = dataSet.subset(5);
        //fileOperator.saveCSVFile("subset-Classification-5.csv", dataSet);

        //Add remaining life
        //DataSet dataSet = fileOperator.openCSVFile("train1.csv");
        //dataSet.addRemainingUsefulLife();


        /*
        //Generate testing data set
        FileOperator fileOperator = new FileOperator();
        DataSet testDataSet = fileOperator.openCSVFile("test1.csv");
        String[] testHeaders = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        testDataSet.addMovingAverage(5, testHeaders, true);
        testDataSet.addStandardDeviation(5, testHeaders, true);
        DataSet testSubset = testDataSet.subset(5);
        fileOperator.saveCSVFile("testSubset.csv", testSubset);
        */


        /*
        //Prediction
        //RandomForest_3.Model.2016-08-11_09-17-48

        PredictiveModel predictiveModel = new PredictiveModel("RandomForest_3.Model.2016-08-11_09-17-48");
        predictiveModel.displayFeatures();

        String headerLine = "UnitNumber,Time,Setting1,Setting2,Setting3,Sensor1,Sensor2,Sensor3," +
                "Sensor4,Sensor5,Sensor6,Sensor7,Sensor8,Sensor9,Sensor10,Sensor11,Sensor12,Sensor13," +
                "Sensor14,Sensor15,Sensor16,Sensor17,Sensor18,Sensor19,Sensor20,Sensor21," +
                "Binary-Classification,ma-Sensor1,ma-Sensor2,ma-Sensor3,ma-Sensor4,ma-Sensor5," +
                "ma-Sensor6,ma-Sensor7,ma-Sensor8,ma-Sensor9,ma-Sensor10,ma-Sensor11,ma-Sensor12," +
                "ma-Sensor13,ma-Sensor14,ma-Sensor15,ma-Sensor16,ma-Sensor17,ma-Sensor18,ma-Sensor19," +
                "ma-Sensor20,ma-Sensor21,sd-Sensor1,sd-Sensor2,sd-Sensor3,sd-Sensor4,sd-Sensor5," +
                "sd-Sensor6,sd-Sensor7,sd-Sensor8,sd-Sensor9,sd-Sensor10,sd-Sensor11,sd-Sensor12," +
                "sd-Sensor13,sd-Sensor14,sd-Sensor15,sd-Sensor16,sd-Sensor17,sd-Sensor18,sd-Sensor19," +
                "sd-Sensor20,sd-Sensor21";

        String dataLine = "1,187,-0.0047,-0.0000,100.0,518.67,643.32,1592.10,1427.27,14.62,21.61," +
                "551.08,2388.29,9037.71,1.30,48.23,519.53,2388.28,8115.67,8.5218,0.03,396,2388,100.00," +
                "38.42,23.0822,1,518.67,643.588,1598.6779999999999,1425.904,14.62,21.61,551.534," +
                "2388.2480000000005,9044.328,1.3,48.12,519.85,2388.262,8117.244000000001,8.51206," +
                "0.03,395.8,2388.0,100.0,38.477999999999994,23.07068,0.0,0.30863570759067954," +
                "5.02663465949141,4.365961978762521,0.0,0.0,0.5656005657705913,0.03599999999997736," +
                "4.859606568437807,0.0,0.06870225614926921,0.20129580224138705,0.019390719429732108," +
                "3.3645778338448835,0.009587199799733246,0.0,0.7483314773547882,0.0,0.0," +
                "0.05192301994298886,0.037482497248715066";

        DataRow dataRow = new DataRow(headerLine, dataLine);

        dataRow.removeFeature("Binary-Classification");
        dataRow.removeFeature("ma-Sensor1");
        dataRow.removeFeature("ma-Sensor10");
        dataRow.removeFeature("ma-Sensor16");
        dataRow.removeFeature("ma-Sensor18");
        dataRow.removeFeature("ma-Sensor19");
        dataRow.removeFeature("ma-Sensor5");
        dataRow.removeFeature("ma-Sensor6");
        dataRow.removeFeature("sd-Sensor1");
        dataRow.removeFeature("sd-Sensor16");
        dataRow.removeFeature("sd-Sensor10");
        dataRow.removeFeature("sd-Sensor18");
        dataRow.removeFeature("sd-Sensor19");
        dataRow.removeFeature("sd-Sensor5");
        dataRow.removeFeature("sd-Sensor6");
        dataRow.removeFeature("Sensor1");
        dataRow.removeFeature("Sensor10");
        dataRow.removeFeature("Sensor16");
        dataRow.removeFeature("Sensor18");
        dataRow.removeFeature("Sensor19");
        dataRow.removeFeature("Sensor5");
        dataRow.removeFeature("Sensor6");
        dataRow.removeFeature("Setting3");
        predictiveModel.compareFeatures(dataRow.getFeatures());

        System.out.println(predictiveModel.predict(dataRow.toArray()));
        */

        //Generate RUL training data set
        //FileOperator fileOperator = new FileOperator();
        //DataSet dataset= fileOperator.openCSVFile("train1.csv");
        //dataset.addRemainingUsefulLife();
        //String[] headers = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        //dataset.addMovingAverage(5, headers, true);
        //dataset.addStandardDeviation(5, headers, true);
        //DataSet subset = dataset.subset(5);
        //fileOperator.saveCSVFile("subset-RUL.csv", subset);


        /*
        //Prediction
        //RandomForestRegression.Model.2016-08-12_13-26-39
        PredictiveModel predictiveModel = new PredictiveModel("RandomRegression_2.Model.2016-08-12_10-29-46");


        FileOperator fileOperator = new FileOperator();
        DataSet testData = fileOperator.openCSVFile("testSubset.csv");
        DataSet rulData = fileOperator.openCSVFile("rul1.csv");

        ArrayList<DataRow> rulDataRows = rulData.getDataRows();

        int count = 0;
        double variance = 0.0;
        for(int i=1; i<=100; i++){
            double originalValue = Double.parseDouble(rulDataRows.get(i-1).getData(0));
            ArrayList<DataRow> dataRows = testData.getDataRows("UnitNumber", String.valueOf(i));
            for(DataRow dataRow: dataRows){
                dataRow.automaticRemoveUnmatchingFeatures(predictiveModel.getMlModel());
                System.out.println();
                double predicted = Double.parseDouble(predictiveModel.predict(dataRow.toArray()).toString());
                variance += Math.pow((originalValue-predicted), 2);
                count++;
            }
        }

        System.out.println("Number of test cases : " + String.valueOf(count));
        System.out.println("Mean Squared Error   : " + String.valueOf(Math.sqrt(variance/count)));
        */

        /*
        String headerLine = "UnitNumber,Time,Setting1,Setting2,Setting3,Sensor1,Sensor2,Sensor3," +
                "Sensor4,Sensor5,Sensor6,Sensor7,Sensor8,Sensor9,Sensor10,Sensor11,Sensor12,Sensor13," +
                "Sensor14,Sensor15,Sensor16,Sensor17,Sensor18,Sensor19,Sensor20,Sensor21,ma-Sensor1," +
                "ma-Sensor2,ma-Sensor3,ma-Sensor4,ma-Sensor5,ma-Sensor6,ma-Sensor7,ma-Sensor8," +
                "ma-Sensor9,ma-Sensor10,ma-Sensor11,ma-Sensor12,ma-Sensor13,ma-Sensor14,ma-Sensor15," +
                "ma-Sensor16,ma-Sensor17,ma-Sensor18,ma-Sensor19,ma-Sensor20,ma-Sensor21,sd-Sensor1," +
                "sd-Sensor2,sd-Sensor3,sd-Sensor4,sd-Sensor5,sd-Sensor6,sd-Sensor7,sd-Sensor8," +
                "sd-Sensor9,sd-Sensor10,sd-Sensor11,sd-Sensor12,sd-Sensor13,sd-Sensor14,sd-Sensor15," +
                "sd-Sensor16,sd-Sensor17,sd-Sensor18,sd-Sensor19,sd-Sensor20,sd-Sensor21";

        String dataLine = "1,5,0.0014,0.0000,100.0,518.67,642.51,1587.19,1401.92,14.62,21.61,554.16," +
                "2388.01,9044.55,1.30,47.31,522.15,2388.03,8129.54,8.4031,0.03,390,2388,100.00,38.99," +
                "23.4130,518.67,642.4280000000001,1586.398,1400.662,14.62,21.61,554.2180000000001," +
                "2388.0280000000002,9050.278,1.3,47.358,521.876,2388.0400000000004,8131.5419999999995," +
                "8.40488,0.03,391.8,2388.0,100.0,38.989999999999995,23.39368,0.0,0.4180621963296678," +
                "1.519781563251832,3.7064182170931534,0.0,0.0,0.32786582621555094,0.01599999999995134," +
                "4.888907444408997,0.0,0.12139192724394707,0.2949305002877697,0.01264911064059011," +
                "4.670967351630584,0.02153772504235319,0.0,1.16619037896906,0.0,0.0,0.07211102550927982," +
                "0.01848971606055639";

        DataRow dataRow = new DataRow(headerLine, dataLine);
        dataRow.automaticRemoveUnmatchingFeatures(predictiveModel.getMlModel());


        System.out.println(predictiveModel.predict(dataRow.toArray()));
        */


        //Add median feature
        FileOperator fileOperator = new FileOperator();
        DataSet dataSet = fileOperator.openCSVFile("train1.csv");
        dataSet.addBinaryClassification(30);
        String[] headers = {"Sensor1", "Sensor2","Sensor3", "Sensor4", "Sensor5", "Sensor6", "Sensor7", "Sensor8", "Sensor9", "Sensor10", "Sensor11", "Sensor12", "Sensor13", "Sensor14", "Sensor15", "Sensor16","Sensor17", "Sensor18", "Sensor19", "Sensor20", "Sensor21"};
        dataSet.normalize(headers);
        dataSet.addMovingMedian(5, headers, true);
        dataSet.addMovingAverage(5, headers, true);
        dataSet.addStandardDeviation(5, headers, true);
        DataSet subset = dataSet.subset(5);
        fileOperator.saveCSVFile("0005", subset);


        //ServerConnection serverConnection = new ServerConnection("https://localhost:9443/api/models/52/summary");
        //serverConnection.getJSONObject();
        //Mediator.executeCommand("curl -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW46YWRtaW4=' -v https://localhost:9443/api/models/52/summary -k");
    }
}
