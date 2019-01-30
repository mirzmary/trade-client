package com.tradeclient.trade;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Trade {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String REQUEST_URL = "http://localhost:9889/api/trade/validate";
    private static final String RESOURCE_FILE = "trade.json";

    public static void main(String[] args) throws Exception {
        final Trade http = new Trade();

        http.sendPost();
    }

    // HTTP POST request
    private void sendPost() throws IOException {
        final URL obj = new URL(REQUEST_URL);

        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.addRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        final String requestJson = initAccessMetaData();

        // Send post request
        con.setDoOutput(true);
        final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestJson);
        wr.flush();
        wr.close();

        final int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Trade Validation Request successfully processed with the below validation results");


        }
        final String responseStringJson = IOUtils.toString(con.getInputStream(), "UTF8");

        System.out.println(responseStringJson);
    }

    private String initAccessMetaData() throws IOException {
        try (final InputStream fileInputStream = Trade.class.getResourceAsStream(RESOURCE_FILE)) {
            return IOUtils.toString(fileInputStream, "UTF8");
        }
    }
}
