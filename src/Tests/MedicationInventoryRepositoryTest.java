package Tests;

import Entity.Medicine;
import Entity.Repository.MedicationInventoryRepository;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MedicationInventoryRepositoryTest {

    private MedicationInventoryRepository repository;
    private String testFilePath = "test_inventory.txt"; // Use a temporary file for testing

    @BeforeEach
    public void setup() {
        repository = new MedicationInventoryRepository(testFilePath);

        // Ensure the file exists or create it
        File file = new File(testFilePath);

        // Cleanup the file before each test
        if (file.exists()) {
            file.delete();
            System.out.println("File deleted: " + file.getAbsolutePath());  // Debug output
        }

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile(); // Create file if it doesn't exist
                System.out.println("File created: " + created);  // Debug output

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFilePath, true))) {
                    writer.write("Medicine Name,Current Stock,Low Stock Alert,Request Amount");
                    writer.newLine();
                } catch (Exception e) {
                    System.out.println("Error accessing test file !!");
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Test
    public void testCreateRecord() {
        Medicine medicine = new Medicine("Aspirin", 100, 20, 50);

        repository.createRecord(medicine);

        Medicine result = repository.readRecord("Aspirin");
        assertNotNull(result);
        assertEquals("Aspirin", result.getNameOfMedicine());
        assertEquals(100, result.getCurrentStock());
        assertEquals(20, result.getLowStockLevelAlert());
        assertEquals(50, result.getRequestAmount());
    }

    @Test
    public void testCreateRecordDuplicate() {
        Medicine medicine = new Medicine("Aspirin", 100, 20, 50);
        repository.createRecord(medicine); // First insert
        repository.createRecord(medicine); // Attempting to insert duplicate

        // Ensure duplicate creation fails and error is logged
        Medicine result = repository.readRecord("Aspirin");
        assertNotNull(result);
    }

    @Test
    public void testReadRecord() {
        Medicine medicine = new Medicine("Paracetamol", 200, 50, 100);
        repository.createRecord(medicine);

        Medicine result = repository.readRecord("Paracetamol");
        assertNotNull(result);
        assertEquals("Paracetamol", result.getNameOfMedicine());
    }

    @Test
    public void testReadNonExistingRecord() {
        Medicine result = repository.readRecord("NonExistingMedicine");
        assertNull(result); // Should return null if the record doesn't exist
    }

    @Test
    public void testUpdateRecord() {
        Medicine medicine = new Medicine("Ibuprofen", 150, 30, 70);
        repository.createRecord(medicine);

        Medicine updatedMedicine = new Medicine("Ibuprofen", 200, 40, 90);
        repository.updateRecord(updatedMedicine);

        Medicine result = repository.readRecord("Ibuprofen");
        assertNotNull(result);
        assertEquals(200, result.getCurrentStock());
        assertEquals(40, result.getLowStockLevelAlert());
        assertEquals(90, result.getRequestAmount());
    }

    @Test
    public void testUpdateRecordNotFound() {
        Medicine medicine = new Medicine("Aspirin", 100, 20, 50);
        repository.updateRecord(medicine); // Attempting to update a non-existing record

        // No exception, but we check if the file was not updated
        Medicine result = repository.readRecord("Aspirin");
        assertNull(result); // No record should be found
    }

    @Test
    public void testDeleteRecord() {
        Medicine medicine = new Medicine("Aspirin", 100, 20, 50);
        repository.createRecord(medicine);

        repository.deleteRecord(medicine);

        Medicine result = repository.readRecord("Aspirin");
        assertNull(result); // Record should be deleted
    }

    @Test
    public void testDeleteNonExistingRecord() {
        Medicine medicine = new Medicine("NonExistingMedicine", 100, 20, 50);
        repository.deleteRecord(medicine);

        // File should be unchanged
        Medicine result = repository.readRecord("NonExistingMedicine");
        assertNull(result); // Should not be found
    }

    @Test
    public void testGetAllMedicine() {
        Medicine medicine1 = new Medicine("Aspirin", 100, 20, 50);
        Medicine medicine2 = new Medicine("Paracetamol", 200, 50, 100);
        repository.createRecord(medicine1);
        repository.createRecord(medicine2);

        ArrayList<Medicine> allMedicines = repository.getAllMedicine();
        assertEquals(2, allMedicines.size());
        assertTrue(allMedicines.stream().anyMatch(m -> m.getNameOfMedicine().equals("Aspirin")));
        assertTrue(allMedicines.stream().anyMatch(m -> m.getNameOfMedicine().equals("Paracetamol")));
    }

    @AfterEach
    public void tearDown() {
        // Cleanup the test file after each test
        //File file = new File(testFilePath);
        //if (file.exists()) {
          //  file.delete();
        //}
    }
}
