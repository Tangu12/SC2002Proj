package Controllers;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.Enums.Status;
import Entity.User.Pharmacist;
import Entity.Medicine;
import Services.AppointmentService;
import Services.MedicalInventoryService;

import java.util.ArrayList;

public class PharmacistController {
    private MedicalInventoryService medicalInventoryService;
    private AppointmentService appointmentService;
    private Pharmacist pharmacist;

    public PharmacistController(MedicalInventoryService medicalInventoryService,AppointmentService appointmentService, Pharmacist pharmacist) {
        this.medicalInventoryService = medicalInventoryService;
        this.appointmentService = appointmentService;
        this.pharmacist = pharmacist;
    }
    
    public Pharmacist getPharmacist() {
    	return this.pharmacist;
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
    
    public Appointment getAppointmentByID(String appID) {
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if(appID.equals(appointment.getAppID())) return appointment;
		}
		return null;
	}
    
    public void addNewPrescription(Appointment appointment, String dateIssued, String medicine, String dosage, String instructions) {
    	appointmentService.addNewPrescription(appointment, dateIssued, medicine, dosage, instructions);
    }
    
    public void updatePrescriptionStatus(Appointment appointment, int statusChoice) {
        if (statusChoice == 1) {
            appointment.setStatusOfApp(Status.Completed);
        } else if (statusChoice == 2) {
            appointment.setStatusOfApp(Status.PendingPrescription);
        } else {
            System.out.println("Invalid input. Please enter 1 or 2.");
        }
    }
} 
