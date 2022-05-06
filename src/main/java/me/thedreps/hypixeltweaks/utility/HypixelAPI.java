package me.thedreps.hypixeltweaks.utility;

import me.thedreps.hypixeltweaks.ConfigHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class HypixelAPI{
    private static JSONObject getJSONObject(String url) throws ParseException, IOException {
        URL ur = new URL(url);
        LogHelper.info(url);
        HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(ur.openStream());

            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }

            scanner.close();

            JSONParser parse = new JSONParser();
            return (JSONObject) parse.parse(inline.toString());
        }
        else if(responseCode == 429)
            throw new RuntimeException("Too many requests! Error 429.");
        else if(responseCode == 204)
            return null;
        else if (responseCode == 403) {
            throw new RuntimeException("Wrong API Key! Error 403.");
        }
        else{
            throw new RuntimeException("Unknown error: Error " + responseCode + ".");
        }
    }

    public static JSONObject getJsonFromJson(JSONObject JSONObject, String get) {
        try {
            return (JSONObject) JSONObject.get(get);
        } catch (ClassCastException e) {
            throw new ClassCastException("The response wasn't an array maybe it was a String?");
        }
        catch(NullPointerException e){
            throw new NullPointerException("The key could not be found");
        }
    }

    public static String getStringFromJson(JSONObject JSONObject, String get) {
        try {
            return (String) JSONObject.get(get);
        } catch (ClassCastException e) {
            throw new ClassCastException("The response wasn't a String maybe it was a Json?");
        }
        catch(NullPointerException e){
            throw new NullPointerException("The key could not be found");
        }
    }

    public static int getIntFromJson(JSONObject JSONObject, String get) {
        try {
            return (int) JSONObject.get(get);
        } catch (ClassCastException e) {
            throw new ClassCastException("The response wasn't a number maybe it was a Json?");
        }
        catch(NullPointerException e){
            throw new NullPointerException("The key could not be found");
        }
    }

    public static double getDoubleFromJson(JSONObject JSONObject, String get) {
        LogHelper.info(get);
        try {
            return Double.parseDouble(JSONObject.get(get).toString());
        } catch (ClassCastException e) {
            throw new ClassCastException("The response wasn't a number maybe it was a Json?");
        }
        catch(NullPointerException e){
            return 0;
//            throw new NullPointerException("The key could not be found");
        }
    }

    public static boolean getBooleanFromJson(JSONObject JSONObject, String get) {
        try {
            return (boolean) JSONObject.get(get);
        } catch (ClassCastException e) {
            throw new ClassCastException("The response wasn't a boolean maybe it was a Json?");
        }
        catch(NullPointerException e){
            throw new NullPointerException("The key could not be found");
        }
    }

    public static JSONObject getPlayerJson(UUID uuid){
        JSONObject obj;
        String key = ConfigHandler.getApiKey();

        try{
            obj = getJSONObject("https://api.hypixel.net/player?uuid=" + uuid.toString() + "&key=" + key);
            if(!getBooleanFromJson(obj, "success")){
                return null;
            }else{
                return obj;
            }
        }catch(NullPointerException | ParseException | IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
