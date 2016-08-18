package ml;

import data.DataRow;
import log.Logger;
import log.MESSAGE;
import org.wso2.carbon.ml.commons.domain.Feature;
import org.wso2.carbon.ml.commons.domain.MLModel;
import org.wso2.carbon.ml.commons.domain.ModelSummary;
import org.wso2.carbon.ml.core.exceptions.MLModelHandlerException;
import org.wso2.carbon.ml.core.impl.MLModelHandler;
import org.wso2.carbon.ml.core.impl.Predictor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wso2123 on 8/6/16.
 */
public class PredictiveModel {
    private String source;
    private MLModel mlModel;

    public PredictiveModel(String source) throws URISyntaxException, IOException, ClassNotFoundException {
        this.source = source;
        URL resource = PredictiveModel.class.getClassLoader().getResource(source);
        String pathToDownloadedModel = new File(resource.toURI()).getAbsolutePath();
        this.mlModel  = deserializeMLModel(pathToDownloadedModel);
    }

    private MLModel deserializeMLModel(String pathToDownloadedModel) throws IOException, ClassNotFoundException, URISyntaxException {
        FileInputStream fileInputStream = new FileInputStream(pathToDownloadedModel);
        ObjectInputStream in = new ObjectInputStream(fileInputStream);
        MLModel mlModel = (MLModel) in.readObject();
        return mlModel;
    }

    public MLModel getMlModel(){
        return this.mlModel;
    }

    public ArrayList<String> getFeatures(){
        ArrayList<String> features = new ArrayList<String>();
        for(int i=0; i<mlModel.getFeatures().size(); i++){
            Feature feature = mlModel.getFeatures().get(i);
            features.add(feature.getName());
        }
        return features;
    }

    public Object predict(String[] featureValueArray) throws MLModelHandlerException {
        ArrayList<String[]> featureValuesList = new ArrayList<String[]>();
        featureValuesList.add(featureValueArray);
        /*
        if(featureValueArray.length != mlModel.getFeatures().size()){
            Logger.log(MESSAGE.ERROR, "Number of features in the array does not match the number of features in the model.", "PredictiveModel -> predict");
            return null;
        }

        for (String[] featureValues : featureValuesList) {
            if (featureValues.length != mlModel.getFeatures().size()) {
                Logger.log(MESSAGE.ERROR, "Number of features in the array does not match the number of features in the model.", "PredictiveModel -> predict");
                return null;
            }
        }
        */
        Predictor predictor = new Predictor(0, mlModel, featureValuesList);
        List<?> predictions = predictor.predict();

        return predictions.get(0);
    }

    public void displayFeatures(){
        for(int i=0; i<mlModel.getFeatures().size(); i++){
            Feature feature = mlModel.getFeatures().get(i);
            System.out.println(feature.getName());
        }
    }

    public void compareFeatures(ArrayList<String> dataRowFeatures){
        ArrayList<String> modelFeatures = new ArrayList<String>();
        for(int i=0; i<mlModel.getFeatures().size(); i++){
            Feature feature = mlModel.getFeatures().get(i);
            modelFeatures.add(feature.getName());
            if(!dataRowFeatures.contains(feature.getName())){
                //Missing features
                Logger.log(MESSAGE.ERROR, "+" + feature.getName(), "PredictiveModel -> compareFeatures");
            }
        }


        for(int i=0; i<dataRowFeatures.size(); i++){
            if(!modelFeatures.contains(dataRowFeatures.get(i))){
                //Additional features
                Logger.log(MESSAGE.ERROR, "-" + dataRowFeatures.get(i), "PredictiveModel -> compareFeatures");
            }
        }
    }


    public void test() throws MLModelHandlerException {
        MLModelHandler mlModelHandler = new MLModelHandler();
        ModelSummary summary = mlModelHandler.getModelSummary(48);
        System.out.println(summary.getModelSummaryType());
    }

}
