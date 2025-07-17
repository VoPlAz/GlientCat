package org.hex.gcat.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Config {

    public static final JsonObject DEAFULT_CONFIG(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("botToken","null");
        jsonObject.addProperty("ownerID","null");
        jsonObject.addProperty("sqlPm","null");
        return jsonObject;
    }
    public static void makeConfig() throws IOException {
        File config = new File("config.json");
        config.createNewFile();
    }
    public static void writeConfig(JsonObject object) throws IOException{
        File config=new File("config.json");
        config.delete();
        config.createNewFile();
        FileWriter fw = new FileWriter(config);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(object.toString());
        bw.close();
        fw.close();
    }
    public static JsonObject readConfig() throws IOException{
        String content = new String(Files.readAllBytes(Paths.get("config.json")));
        return (JsonObject) JsonParser.parseString(content);
    }
    public static boolean isConfigExist(){
        return new File("config.json").exists();
    }
}
