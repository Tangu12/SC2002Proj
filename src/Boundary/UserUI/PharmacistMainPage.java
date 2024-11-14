package Boundary.UserUI;


import Controllers.PharmacistController;
import Entity.MedicationInventory;
import Entity.Medicine;
import Services.AppointmentService;
import Services.InputService;
import Services.MedicalInventoryService;

import java.util.ArrayList;

public class PharmacistMainPage {
    private PharmacistController pharmacistController;

    public PharmacistMainPage(PharmacistController pharmacistController, MedicalInventoryService medicalInventoryService,AppointmentService appointmentService) {
        this.pharmacistController = new PharmacistController(medicalInventoryService,appointmentService);
    }

    public void homePage(){
        int choice;
        do {
            System.out.println("\nChoose the number of functions:\n"
                    + "(1) View Appointment Outcome Record\n"
                    + "(2) Update Prescription Status\n"
                    + "(3) View Medication Inventory\n"
                    + "(4) Submit Replenishment Request\n"
                    + "(5) Logout\n"
            );
            choice = InputService.inputInteger();
            switch (choice) {
                case 1 -> viewAppointmentOutcomeRecord();
                case 2 -> updatePrescriptionStatus();
                case 3 -> viewMedicationInventory();
                case 4 -> submitReplenishmentRequest();
                case 5 -> System.out.println("Logging out.");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    public void viewAppointmentOutcomeRecord(){

    }
    public void updatePrescriptionStatus(){

    }

    /*
    Prints out a Medication Inventory Table
    */
    public void viewMedicationInventory(){
        int i = 1;

        if (pharmacistController.getMedicationInventory().isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            System.out.println("+" + "-".repeat(3) + "+"
                    + "-".repeat(30) + "+"
                    + "-".repeat(15));

            System.out.println("|" + formatCell("No.", 3)
                    + "|" + formatCell("Medicine Name", 30)
                    + "|" + formatCell("Current Stock", 15) + "|");

            System.out.println("+" + "-".repeat(3) + "+"
                    + "-".repeat(30) + "+"
                    + "-".repeat(15) + "+");
            for (Medicine medicine : pharmacistController.getMedicationInventory()) {
                //System.out.println(i + ". " + medicine);
                System.out.println("|" + formatCell(String.valueOf(i), 3)
                        + "|" + formatCell(medicine.getNameOfMedicine(), 30)
                        + "|" + formatCell(String.valueOf(medicine.getCurrentStock()), 15) + "|");
                System.out.println("+" + "-".repeat(3) + "+"
                        + "-".repeat(30) + "+"
                        + "-".repeat(15) + "+");
                i++;
            }
        }
    }

    /*
    Updates the replenishment column in the MedicationInventory File
    */
    public void submitReplenishmentRequest(){

    }

    private static String formatCell(String value, int width) {
        if (value == " ") {
            value = "";
        }
        return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
    }
}
