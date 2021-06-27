package com.goduu.stocksstudies.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import io.jsonwebtoken.io.IOException;


@Component
public class Utils {

    /**
     * 
     * Get a JsonObject from a url
     * @param sUrl url string
     * @return The JsonObject
     * @throws JsonIOException
     * @throws JsonSyntaxException
     * @throws IOException
     * @throws java.io.IOException
     */ 
    public JsonObject getJsonFromURL(String sUrl) throws JsonIOException, JsonSyntaxException, IOException, java.io.IOException {
        // Connect to the URL using java's native library
        URL url = new URL(sUrl);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); 
        JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
        return rootobj;
    }

    
    public long getDateFromQuarter(String quarter) throws ParseException{

        String q = quarter.split("Q")[0];
        String year = quarter.split("Q")[1];
        String day = "25";
        String month = q.equals("1") ? "3" : q.equals("1") ? "6" : q.equals("3") ? "9" : "11";
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = df.parse(day + "-" + month + "-" + year);
        return date.getTime();

}
}
