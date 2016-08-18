package connection;

import jdk.nashorn.api.scripting.JSObject;
import log.Logger;
import log.MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wso2123 on 8/18/16.
 */
public class ServerConnection {
    private String address;
    private URL url;
    private URLConnection urlConnection;

    public ServerConnection(String address){
        final String sourceMethod = "Constructor";

        this.address = address;
        try {
            this.url = new URL(address);
            this.urlConnection = url.openConnection();
        } catch (MalformedURLException e) {
            Logger.log(MESSAGE.ERROR, "Invalid URL", this.getClass().getName(), sourceMethod);
        } catch (IOException e) {
            Logger.log(MESSAGE.ERROR, "IO Exception", this.getClass().getName(), sourceMethod);
        }
    }

    public URLConnection getUrlConnection(){
        return this.urlConnection;
    }

    public JSObject getJSONObject(){
        if(urlConnection != null){
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    if(line.length() > 0){
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
