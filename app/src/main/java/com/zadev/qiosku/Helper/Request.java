package com.zadev.qiosku.Helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {

    public String get(String url) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL site = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) site.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(127000);
        connection.setConnectTimeout(127000);
        connection.connect();

        if( connection.getResponseCode() == HttpURLConnection.HTTP_OK )
        {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = br.readLine()) != null)
            {
                sb.append(line);
            }

            if ( br != null )
            {
                br.close();
            }
        }else
        {
            sb.append("");
        }
        if( connection != null )
        {
            connection.disconnect();
        }
        return sb.toString();
    }

    public String post(String url, HashMap<String, String> map) throws IOException {
        StringBuilder sb = new StringBuilder();

        URL site = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) site.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        connection.setReadTimeout(127000);
        connection.setConnectTimeout(127000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();

        OutputStream os = connection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        bw.write(param(map));
        bw.flush();
        bw.close();
        os.close();

        if( connection.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = br.readLine()) != null)
            {
                sb.append(line);
            }
            if( br != null)
            {
                br.close();
            }
        }else
        {
            sb.append("");
        }

        return sb.toString();
    }
    private String param(HashMap<String, String> map)
    {
        StringBuilder sb= new StringBuilder();
        boolean status = true;
        try{
            for(Map.Entry<String, String> list : map.entrySet())
            {
                if ( status )
                    status = false;
                else
                    sb.append("&");
                sb.append(URLEncoder.encode(list.getKey(),"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(list.getKey(), "UTF-8"));
            }
        }catch(Exception e)
        {
            System.out.println("An exception has occurred : "+e.getMessage());
        }
        return sb.toString();
    }
}
