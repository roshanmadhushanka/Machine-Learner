package io;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wso2123 on 8/16/16.
 */
public class RESTClient {
    public void run(){
        try {

            // create HTTP Client
            HttpClient httpClient = HttpClientBuilder.create().build();

            // Create new getRequest with below mentioned URL
            HttpGet getRequest = new HttpGet("https://localhost:9443/api/models/52/summary");

            // Add additional header to getRequest which accepts application/xml data
            getRequest.addHeader("accept", "application/json");

            // Execute your request and catch response
            HttpResponse response = httpClient.execute(getRequest);

            // Check for HTTP response code: 200 = success
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            // Get-Capture Complete application/xml body response
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            System.out.println("============Output:============");

            // Simply iterate through XML response and show on console.
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void demoGET() {

        Client client = Client.create();

        WebResource webResource = client
                .resource("https://localhost:9443/api/models/52/summary");

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
    }
}
