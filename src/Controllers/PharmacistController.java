package Controllers;

import Entity.Appointment;
import Entity.Enums.Status;
import Entity.Medicine;
import Services.AppointmentService;
import Services.MedicalInventoryService;

import java.util.ArrayList;

public class PharmacistController {
    private MedicalInventoryService medicalInventoryService;
    private AppointmentService appointmentService;

    public PharmacistController(MedicalInventoryService medicalInventoryService,AppointmentService appointmentService) {
        this.medicalInventoryService = medicalInventoryService;
        this.appointmentService = appointmentService;
    }

    public void viewAppointmentOutcomeRecord(){

    }

    /*
    Uses MedicalInventoryService to updatePrescriptionStatus
    */
    // TODO Implement UpdatePrescriptionStatus
    public void updatePrescriptionStatus(Appointment appointment,  int appIndex, Status status){
        appointmentService.updateAppointmentStatus(appointment,appIndex,status);
    }

    /*
    Uses MedicalInventoryService to return an arrayList of medicines currently in the inventory
    */
    public ArrayList<Medicine> getMedicationInventory(){
        return medicalInventoryService.viewInventory();
    }

    /*
    Uses MedicalInventoryService to submit a replenishmentRequest
    */
    public void submitReplenishmentRequest(String medicineName,int requestAmount){
        medicalInventoryService.submitReplenishmentRequest(medicineName,requestAmount);
    }

}
