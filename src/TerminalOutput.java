import org.json.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TerminalOutput {
    private final String apikey;

    public TerminalOutput(String key) {
        apikey = key;
    }

    /** Method used to print the current weather to the console */
    void currentWeather() throws IOException {
        String city, state;
        Scanner reader = new Scanner(System.in);
        System.out.print("Please input your city: ");
        city = reader.next();
        System.out.print("Please input your two digit state code: ");
        state = reader.next();

        String theURL = "https://api.openweathermap.org/data/2.5/weather?q=";
        theURL += city + "," + ("US-" + state) + "&units=imperial&appid=" + apikey;

        URL apiurl = new URL(theURL);
        JSONObject json = getJsonContents(apiurl);

        System.out.println("The current temperature in " + city + ", " + state + " is "
                + json.getJSONObject("main").getDouble("temp")
                + " but it feels like " + json.getJSONObject("main").getDouble("feels_like"));

        System.exit(0);
    }

    void futureWeather() {
        System.exit(0);
    }

    /** Method used to pull the JSON file from the openweatherAPI and return it as a JSONObject */
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

    static void parseResponseCode(int rCode) {
        switch (rCode) {
            case 429:
                throw new RuntimeException("Exceeded the amount of allowed requests!");
            case 404:
                throw new RuntimeException("Format of API request was incorrect");
            case 401:
                throw new RuntimeException("Something is wrong with the API key");
        }
    }

    // static void printJSONFile(JSONObject json) { System.out.println(json.write(new StringWriter()).toString()); }
}
