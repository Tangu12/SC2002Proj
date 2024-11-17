package Entity.Repository;

import Entity.MedicationInventory;
import Entity.Medicine;

import java.io.*;
import java.util.ArrayList;

/**
 * The Repository that accesses and changes everything related to {@code MedicineInventory} in the {@code MedicineInventory} database
 */
public class MedicationInventoryRepository implements IRepository<String,String,Medicine,Medicine>{
    public final String path;

    /**
     * Constructor for the  /**
     *      * Constructor for the {@code MedicalInventoryRepository} which initialises the path of which the {@code MedicalInventoryRepository} reads from
     *      * @param path
     * @param path
     */
    public MedicationInventoryRepository(String path) {
        this.path = path;
    }

    /**
     * Creates a new {@code Medicine} in the {@code MedicineInventory} file
     * @param attributes
     */
    @Override
    public void createRecord(Object... attributes) {
        if (attributes.length != 1 && !(attributes[0] instanceof Medicine)) {
            System.out.println("Error: Incorrect number of attributes provided.");
            return;
        }

        try {
            Medicine record = (Medicine) attributes[0];

            if (readRecord(record.getNameOfMedicine()) != null) {
                System.out.println("Error!! Medicine already exists !!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.write(record.getNameOfMedicine() + "," +
                        record.getCurrentStock() + "," +
                        record.getLowStockLevelAlert() + "," +
                        record.getRequestAmount());
                writer.newLine();
            } catch (Exception e) {
                System.out.println("Error accessing MedicationInventory file !!");
                e.printStackTrace();
            }

        } catch (ClassCastException e) {
            System.out.println("Error: Invalid attribute types provided.");
            e.printStackTrace();
        }
    }

    /**
     * Looks through the {@code MedicineInventory} file to find the {@code Medicine} matching the input, and returns the {@code Medicine}.
     * @param identifier
     * @return
     */
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

    /**
     * Updates the data of a {@code Medicine} inside the {@code MedicineInventory} file
     * @param record
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

    /**
     * Deletes a {@code Medicine} from the {@code MedicineInventory} file
     * @param medicineName
     */
    @Override
    public void deleteRecord(String medicineName){
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
                if (temp.getNameOfMedicine().equalsIgnoreCase(medicineName)) {
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

    /**
     * Returns all {@code Medicine} in the file in an ArrayList
     * @return
     */
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

    /**
     * Overwrites the file and stores the inventory List
     * @param inventory
     */
    public void saveInventoryToFile(ArrayList<Medicine> inventory){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Medicine Name,Current Stock,Low Stock Alert,Request Amount");
            writer.newLine();

            for (Medicine medicine : inventory) {
                writer.write(medicine.getNameOfMedicine() + "," +
                        medicine.getCurrentStock() + "," +
                        medicine.getLowStockLevelAlert() + "," +
                        medicine.getRequestAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory");
            e.printStackTrace();
        }
    }
}
