package CurrencyConverter;

import CurrencyConverter.Model.Currency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Import the exchange rates from the database
     *
     * @throws IOException If the file not found
     */
    public static Map<String, Currency> importRatesData(String filename) throws IOException {
        File file = new File(filename);
        InputStream input = new FileInputStream(file);
        JsonReader reader = new JsonReader(new InputStreamReader(input));
        Map<String, Currency> currencies = gson.fromJson(reader, new TypeToken<Map<String,
                Currency>>() {}.getType());
        reader.close();
        return currencies;
    }

    /**
     * Export the changed exchange rates to the database
     *
     * @throws IOException If writing to the file fails
     */
    public static void exportRatesData(Map<String, Currency> currencies, String filename) throws IOException {
        File file = new File(filename);

        FileWriter fileWriter = new FileWriter(file);
        JsonWriter jsonWriter = new JsonWriter(fileWriter);
        jsonWriter.setIndent(" ");

        gson.toJson(currencies, new TypeToken<Map<String, Currency>>() {}.getType(), jsonWriter);
        jsonWriter.flush();
        jsonWriter.close();
    }

    /**
     * Load a file that address given by the parameter
     *
     * @param filename The address of file
     * @return A String array that contain the String context
     * @throws FileNotFoundException when the file is not found
     */
    public static String[] loadConfig(String filename) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filename));
        scanner.nextLine();
        // ignore the comma
        return scanner.nextLine().split(",");
    }

    /**
     * Saving the config by given filename
     *
     * @param adminPassword     admin's password
     * @param popularCurrencies an String array of popularCurrencies
     * @param filename          an address of filename that saving for
     * @throws FileNotFoundException when the file address is not found
     */
    public static void saveConfig(String adminPassword, String[] popularCurrencies,
                                  String filename) throws FileNotFoundException {
        //creat the File object that address from filename
        File file = new File(filename);

        //creat PrintWriter object that allows to write in the file
        PrintWriter writer = new PrintWriter(file);
        writer.println("adminPassword,popularCurrencies,,,");
        writer.print(adminPassword + ",");

        //for loop to write context
        for (int i = 1; i <= popularCurrencies.length; i++) {

            // if it is the last context we do not add comma
            if (i < popularCurrencies.length) {
                writer.print(popularCurrencies[i - 1] + ",");
            } else {
                writer.print(popularCurrencies[i - 1]);
            }
        }
        //closing the writer to ensure the file is save finished
        writer.close();
    }

}
