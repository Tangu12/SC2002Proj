//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class FileIO {

    public String fileName;

    public FileIO(String fileName) {
        this.fileName = fileName;
    }


    public List<String[]> getAllRows() {
        List<String[]> data = new ArrayList<>();

        //Read the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] values = row.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    private void overwriteCSV(List<String[]> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(String[] row : data) writer.write(String.join(",", row) + "\n");
            writer.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static String formatCell(String value, int width) {
        if (value == " ") {
            value = "";
        }
        return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
    }

}
