package appschool.wsapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class wsApi {
    private StringBuilder resposta = null;
    private Scanner scanner = null;
    private JSONObject jsondata = null;

    public JSONObject ReturnData(String urlAddress, String urlparameters) {
        try {
            resposta = new StringBuilder();
            URL url = new URL(urlAddress + urlparameters);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setAllowUserInteraction(true);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            jsondata = new JSONObject(resposta.toString());
        } catch (IOException eio) {
        } catch (JSONException ejson) {
        }
        return jsondata;
    }
}