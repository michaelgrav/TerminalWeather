import org.json.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TerminalWeather {
    static String city;
    static String state;

    public static void main(String[] args) throws IOException {
        String apikey = args[0];

        //printJSONFile(weatherJSON);
        TerminalOutput output = new TerminalOutput(apikey);

        Scanner reader = new Scanner(System.in);
        System.out.println("Would you like the current or future weather? Type current, future, or exit");
        String usrInput = reader.nextLine();

        if (usrInput.equalsIgnoreCase("future")) {
            output.futureWeather();
        } else if (usrInput.equalsIgnoreCase("current")) {
            output.currentWeather();
        } else {
            System.exit(0);
        }

    }
}

/*
    Future stuff
    - ask user for api key and store it
    - let user get weather by inputting the state and city

 */
