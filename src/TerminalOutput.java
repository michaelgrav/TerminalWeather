import org.json.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TerminalOutput {
    private final String apikey;

    public TerminalOutput(String key) {
        apikey = key;
    }

    // EVERYTHING TO DO WITH THE CURRENT WEATHER STARTS HERE

    /** Method used to print the current weather to the console */
    public void currentWeather() throws IOException {
        String city, state;
        Scanner reader = new Scanner(System.in);
        System.out.print("Please input your city and two digit state code: ");
        city = reader.next();
        state = reader.next();

        clearScreen();

        String theURL = "https://api.openweathermap.org/data/2.5/weather?q=";
        theURL += city + "," + ("US-" + state) + "&units=imperial&appid=" + apikey;

        URL apiurl = new URL(theURL);
        JSONObject json = getJsonContents(apiurl);

        System.out.println("The current temperature in " + city + ", " + state + " is "
                + json.getJSONObject("main").getDouble("temp")
                + " but it feels like " + json.getJSONObject("main").getDouble("feels_like"));

        parseCurrentWeatherCode(json.getJSONArray("weather").getJSONObject(0).getInt("id"));

        System.exit(0);
    }

    private void parseCurrentWeatherCode(int weatherCode) {
        if (200 <= weatherCode && weatherCode < 300) {
            System.out.println("Looks like there's thunderstorms out! Be careful! ( ☆ᗜ☆)ϞϞ");
        } else if (300 <= weatherCode && weatherCode < 400) {
            System.out.println("Looks like there's a slight drizzle out! 、ヽ｀、ヽ｀个c(ﾟ∀ﾟ∩)｀ヽ、｀ヽ、");
        } else if (500 <= weatherCode && weatherCode < 600) {
            System.out.println("Looks like there's rain outside! Grab a coat! ｀、ヽ｀ヽ｀、ヽ(ノ＞＜)ノ ｀、ヽ｀☂ヽ｀、");
        } else if (600 <= weatherCode && weatherCode < 700) {
            System.out.println("Looks like there's snow outside! Grab your boots! ㆆᴗㆆ)*✲ﾟ*");
        } else if (700 <= weatherCode && weatherCode < 800) {
            System.out.println("Somethings funky in the atmosphere, working on that functionality!");
        } else if (weatherCode == 800) {
            System.out.println("Clear skies ahead! ☞(⌐■ಎ■)☞  ☀️");
        } else if (800 < weatherCode) {
            System.out.println("Looks like its cloudy out! ⛅");
        }
    }

    // EVERYTHING TO DO WITH THE CURRENT WEATHER ENDS HERE

    // EVERYTHING TO DO WITH THE FUTURE WEATHER STARTS HERE

    public void futureWeather() {
        System.out.println("Functionality not added yet!");
        System.exit(0);
    }

    // EVERYTHING TO DO WITH THE FUTURE WEATHER ENDS HERE

    /** Method used to pull the JSON file from the openweatherAPI and return it as a JSONObject */
    private static JSONObject getJsonContents(URL url) throws IOException {
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

    private static void parseResponseCode(int rCode) {
        switch (rCode) {
            case 429:
                throw new RuntimeException("Exceeded the amount of allowed requests!");
            case 404:
                throw new RuntimeException("Format of API request was incorrect");
            case 401:
                throw new RuntimeException("Something is wrong with the API key");
        }
    }

    // Clear the console with ANSI escape codes. Note, this only will work on UNIX systems
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // static void printJSONFile(JSONObject json) { System.out.println(json.write(new StringWriter()).toString()); }
}
