package data;

import log.Logger;
import log.MESSAGE;
import org.wso2.carbon.ml.commons.domain.Feature;
import org.wso2.carbon.ml.commons.domain.MLModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by wso2123 on 8/11/16.
 */
public class DataRow {
    private ArrayList<String> features;
    private ArrayList<String> data;

    public DataRow(String headerStr, String dataStr){
        this.features = new ArrayList<String>();
        this.data = new ArrayList<String>();
        addFeatures(headerStr);
        addData(dataStr);

        if(features.size() != data.size()){
            Logger.log(MESSAGE.WARNING, "Header count data count mismatch", "DataRow -> Constructor");
        }else{
            Logger.log(MESSAGE.SUCCESS, "Data row created", "DataRow -> Constructor");
        }
    }

    public DataRow(ArrayList<String> features, ArrayList<String> data){
        this.features = features;
        this.data = data;
        if(features.size() != data.size()){
            Logger.log(MESSAGE.WARNING, "Header count data count mismatch", "DataRow -> Constructor");
        }else{
            Logger.log(MESSAGE.SUCCESS, "Data row created", "DataRow -> Constructor");
        }
    }

    public void addFeatures(String str){
        //Headers seperated by commas
        String[] headerArray = str.split(",");
        Collections.addAll(this.features, headerArray);
    }

    public void addFeatures(ArrayList<String> headers){
        this.features = headers;
    }

    public void addData(String str){
        //Data seperated by commas
        String[] dataarray = str.split(",");
        Collections.addAll(this.data, dataarray);
    }

    public void addData(ArrayList<String> data){
        this.data = data;
    }

    public synchronized void removeFeature(String featureName){
        int index = features.indexOf(featureName);
        if(index == -1){
            Logger.log(MESSAGE.WARNING, featureName + " feature cannot find", "DataRow -> removeFeature");
        }else{
            this.features.remove(index);
            this.data.remove(index);
            Logger.log(MESSAGE.SUCCESS, featureName + " feature removed", "DataRow -> removeFeature");
        }
    }

    public void automaticRemoveUnmatchingFeatures(MLModel mlModel){
        ArrayList<String> modelFeatures = new ArrayList<String>();
        ArrayList<String> dataRowFeatures = new ArrayList<String>();

        for(int i=0; i<mlModel.getFeatures().size(); i++){
            Feature feature = mlModel.getFeatures().get(i);
            modelFeatures.add(feature.getName());
        }

        for(String feature: this.features){
            dataRowFeatures.add(feature);
        }

        for(String feature: dataRowFeatures){
            if(!modelFeatures.contains(feature)){
                removeFeature(feature);
            }
        }
    }

    public String getData(int index){
        if(index >= 0 && index < this.features.size()){
            return this.data.get(index);
        }
        return null;
    }

    public String getData(String featureName){
        int index = this.features.indexOf(featureName);
        if(index == -1){
            Logger.log(MESSAGE.ERROR, featureName + " feature name cannot be found", "DataRow -> getData");
            return null;
        }
        return this.data.get(index);
    }

    public ArrayList<String> getFeatures(){
        return this.features;
    }

    public String[] toArray(){
        String[] array = new String[this.features.size()];
        for(int i=0; i<this.features.size(); i++){
            array[i] = this.data.get(i);
        }
        return array;
    }

    public void display(){
        for(int i=0; i<this.features.size(); i++){
            System.out.println("["+ features.get(i)+"] : " + data.get(i));
        }
    }
}
