package Entity.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialsRepository implements IRepository {
    public final String path;

    public CredentialsRepository(String path) {
        this.path = path;
    }

    public void createRecord(){
        List<String[]> data = getAllRows();

    }


    public void readRecord(){

    }


    public void updateRecord(){

    }


    public void deleteRecord(){

    }



    public List<String[]> getAllRows() {
        List<String[]> data = new ArrayList<>();
        //Read the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] values = row.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("File is not created yet!!");
        }
        return data;
    }


    private void overwriteCSV(List<String[]> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for(String[] row : data) writer.write(String.join(",", row) + "\n");
            writer.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
