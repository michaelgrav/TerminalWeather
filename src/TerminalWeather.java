import org.json.*;

import java.io.IOException;
import java.io.StringWriter;
import java.net.*;
import java.util.Scanner;


public class TerminalWeather {
    static String city;
    static String state;

    public static void main(String[] args) throws IOException {
        checkArgs(args);

        String apikey = args[0];
        city = args[1];
        state = args[2];

        String theURL = "https://api.openweathermap.org/data/2.5/weather?q=";
        theURL += city + "," + state + "&units=imperial&appid=" + apikey;

        URL apiurl = new URL(theURL);
        JSONObject weatherJSON = getJsonContents(apiurl);

        //printJSONFile(weatherJSON);

        printWeatherDataToTerm(weatherJSON);
    }

    static void parseResponseCode(int rCode) {
        switch (rCode) {
            case 429:
                throw new RuntimeException("Exceeded the amount of allowed requests!");
            case 404:
                throw new RuntimeException("City not found");
        }
    }

    static void printJSONFile(JSONObject json) {
        System.out.println(json.write(new StringWriter()).toString());
    }

    static void checkArgs(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println("Not enough args!");
            System.exit(-1);
        }
        if (arguments.length > 3) {
            System.out.println("Too many args!");
            System.exit(-1);
        }
    }

    static JSONObject getJsonContents(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");

        connection.connect();
        parseResponseCode(connection.getResponseCode());

        String jsonContents = "";
        Scanner reader = new Scanner(url.openStream());
        while (reader.hasNext()) {
            jsonContents += reader.nextLine();
        }
        reader.close();

        return new JSONObject(jsonContents);
    }

    static void printWeatherDataToTerm(JSONObject json) {
        System.out.println("The current temperature in " + city + ", " + state.replace("US-", "")  + " is " + json.getJSONObject("main").getDouble("temp"));
    }
}

/*
    Future stuff
    - ask user for api key and store it
    - let user get weather by inputting the state and city

 */
