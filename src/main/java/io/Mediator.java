package io;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import log.Logger;
import log.MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by wso2123 on 8/16/16.
 */
public class Mediator {

    public static void executeCommand(String command){
        //Execute curl commands
        final String _methodLocation = "Mediator -> executeCommand";

        try {
            StringBuffer output = new StringBuffer();
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
                output.append(line + "\n");
            }
            // your output that you can use to build your json response:
            output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log(MESSAGE.ERROR, "Invalid command", _methodLocation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
