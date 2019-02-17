package eu.profinit.hlidacStatuLoader.restCommunication;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RestCommunicator {
    public static void POSTRequest(List<JSONObject> results, String datasetId) throws IOException {
        for (JSONObject json : results) {
            final String POST_PARAMS = json.toString();

            URL obj = new URL("https://www.hlidacstatu.cz/api/v1/DatasetItem/" + datasetId + "/" + json.get("Id"));
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();

            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("authorization", "Token cfa2c4c6a6fa4457978d94a9ca9f5b17");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);

            OutputStream os = postConnection.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            int responseCode = postConnection.getResponseCode();

//            System.out.println("POST Response Code :  " + responseCode);
//            System.out.println("POST Response Message : " + postConnection.getResponseMessage());

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
//                System.out.println(response.toString());
            } else {
                System.out.println("POST NOT WORKED");
            }
        }
    }
}
