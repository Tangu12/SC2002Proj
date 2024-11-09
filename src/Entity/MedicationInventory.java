package Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationInventory {
    private List<Medicine> inventory;
    private static MedicationInventory instance;

    private MedicationInventory() {
        this.inventory = new ArrayList<>();
    }

    // readInventory
    // checkifInInventory
    // find medicine by name
    // add medicine
    // increment medicine
    // Increment Stock
    // Remove Medicine
    // Update Medicine
    // Check Low stock
    // Process Replenishment
    // submit replenishment request

    // load Inventory from file
    // Save inventory to file


    public static MedicationInventory getInstance() {
        if (instance == null) {
            instance = new MedicationInventory();
        }
        return instance;
    }

}