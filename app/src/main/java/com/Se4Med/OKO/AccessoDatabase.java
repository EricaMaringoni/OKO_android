package com.Se4Med.OKO;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class AccessoDatabase {

    String prova_url;
    public final String URL;
    String finale;


    public AccessoDatabase(String prova_url) {
        this.prova_url = prova_url;
        URL = "http://10.0.2.2:9997//se4med_servlet/Se4MedDataRegServlet?action=" + prova_url;

        GetXMLTask task = new GetXMLTask();
        //task.execute(new String[] { URL });
        try{
            String res = task.execute(new String[] { URL }).get();

            finale = res;
        }catch (ExecutionException | InterruptedException ei) {
            ei.printStackTrace();
        }
    }

    private class GetXMLTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);

            }
            return output;
        }

        private String getOutputFromUrl(String url) {

            StringBuffer output = new StringBuffer("");
            try {

                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            return output.toString();
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {

            InputStream stream = null;
            java.net.URL url = new URL(urlString);

            URLConnection connection = url.openConnection();

            try {

                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String output) {
            finale = output;
        }
    }
}








