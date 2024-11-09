package Entity.Repository;

import Entity.Medicine;

import java.io.*;
import java.util.ArrayList;

public class MedicationInventoryRepository implements IRepository<Medicine,String>{
    public final String path;

    public MedicationInventoryRepository(String path) {
        this.path = path;
    }

    // read medicine(returns whole inventory)
    /*
     * Creates a new record in the medicationInventory File. It checks if the file exists, if not, it appends the record to the end of the file.
     * */
    @Override
    public void createRecord(Medicine record) {

        if(readRecord(record.getNameOfMedicine()) == null) {
            System.out.println("Error!! Medicine already exists !!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(record.getNameOfMedicine() + "," + record.getCurrentStock() + "," + record.getLowStockLevelAlert()+ "," +record.getRequestAmount());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }
    }

    /*
     * Looks through the MedicationInventory file to find the medicine matching the input, and returns the medicine.
     * */
    @Override
    public Medicine readRecord(String identifier){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();

                if (name.equals(identifier)) {
                    // Create and return Medicine Object
                    int currentStock = Integer.parseInt(data[1].trim());
                    int lowStockAlert = Integer.parseInt(data[2].trim());
                    int requestAmount = Integer.parseInt(data[3].trim());

                    return new Medicine(name, currentStock, lowStockAlert, requestAmount);
                }
            }

        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }
        return null;
    }

    //Admin
    // IncreaseParacetomlby5
    // temp = Medicine(Paracetoml,5,...)
    // UpdateRecord(temp)'



    /*
    Loads all medicine into a temporary List. Then goes through the list to find the record that matches and adds it to the temporary List. It then writes the temporary list into the file
    */
    @Override
    public void updateRecord(Medicine record) {
        ArrayList<Medicine> tempMedicineList = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                String name = data[0].trim();
                int currentStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());
                int requestAmount = Integer.parseInt(data[3].trim());

                Medicine temp = new Medicine(name, currentStock, lowStockAlert,requestAmount);

                // Update the record if it matches
                if (temp.getNameOfMedicine().equalsIgnoreCase(record.getNameOfMedicine())) {
                    tempMedicineList.add(record); // Add the updated record
                    isUpdated = true;
                } else {
                    tempMedicineList.add(temp); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Name,CurrentStock,LowStockAlert,RequestAmount\n");
            writer.newLine();

            for (Medicine tempmedicine : tempMedicineList) {
                writer.write(tempmedicine.getNameOfMedicine() + "," + tempmedicine.getCurrentStock() + "," + tempmedicine.getLowStockLevelAlert() + "," + tempmedicine.getRequestAmount());
                writer.newLine();
            }
            if (!isUpdated) {
                System.out.println("Error, medicine not found! File is unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to MedicationInventory file!");
            e.printStackTrace();
        }
    }

    /*
    Loads all medicine into a temporary List. Then goes through the list to find the record that matches DOES NOT add it to the temporaryList. It then writes the temporary list into the file
    */
    @Override
    public void deleteRecord(Medicine record){
        ArrayList<Medicine> tempMedicineList = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                String name = data[0].trim();
                int currentStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());
                int requestAmount = Integer.parseInt(data[3].trim());

                Medicine temp = new Medicine(name, currentStock, lowStockAlert,requestAmount);

                // Update the record if it matches
                if (temp.getNameOfMedicine().equalsIgnoreCase(record.getNameOfMedicine())) {
                    isDeleted = true; // Do not add the record
                } else {
                    tempMedicineList.add(temp); // Add existing record unchanged
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }

        // Once the whole file is read, we copy the file back

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Name,CurrentStock,LowStockAlert,RequestAmount\n");
            writer.newLine();

            for (Medicine tempmedicine : tempMedicineList) {
                writer.write(tempmedicine.getNameOfMedicine() + "," + tempmedicine.getCurrentStock() + "," + tempmedicine.getLowStockLevelAlert() + "," + tempmedicine.getRequestAmount());
                writer.newLine();
            }
            if (!isDeleted) {
                System.out.println("Error, medicine not found! File is unchanged.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to MedicationInventory file!");
            e.printStackTrace();
        }


    }

    public ArrayList<Medicine> getAllMedicine(){
        ArrayList<Medicine> inventory = new ArrayList<>();;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                String name = data[0].trim();
                int currentStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());
                int requestAmount = Integer.parseInt(data[3].trim());

                Medicine temp = new Medicine(name, currentStock, lowStockAlert,requestAmount);

                inventory.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Error accessing MedicationInventory file !!");
            e.printStackTrace();
        }
        return inventory;
    }

}
