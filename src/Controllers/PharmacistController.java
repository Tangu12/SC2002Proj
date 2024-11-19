package Controllers;

import Controllers.IController.IAppointment.IPharmacistAppointment;
import Controllers.IController.IMedicine.IPharmacistMedicine;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Enums.Status;
import Entity.User.Pharmacist;
import Entity.Medicine;
import Services.AppointmentService;
import Services.MedicalInventoryService;

import java.util.ArrayList;

/**
 * {@code PharmacistController} handles all the logic a Pharmacist's Functions
 */
public class PharmacistController implements IPharmacistAppointment, IPharmacistMedicine {
    private MedicalInventoryService medicalInventoryService;
    private AppointmentService appointmentService;
    private Pharmacist pharmacist;

    /**
     * Constructor for {@code PharmacistController}
     * @param medicalInventoryService
     * @param appointmentService
     * @param pharmacist
     */
    public PharmacistController(MedicalInventoryService medicalInventoryService,AppointmentService appointmentService, Pharmacist pharmacist) {
        this.medicalInventoryService = medicalInventoryService;
        this.appointmentService = appointmentService;
        this.pharmacist = pharmacist;
    }

    /**
     * Gets the instance of a {@code Pharmacist}
     * @return The instance of the {@code Pharmacist}
     */
    public Pharmacist getPharmacist() {
    	return this.pharmacist;
    }

    /**
     * Calls updatePrescriptionStatus function from the {@code AppointmentService}
     * @param appointment
     * @param appIndex
     * @param status
     */
    public void updatePrescriptionStatus(Appointment appointment,  int appIndex, Status status){
        appointmentService.updateAppointmentStatus(appointment,appIndex,status);
    }

    /**
     * Uses MedicalInventoryService to return an arrayList of medicines currently in the inventory
     * @return
     */
    public ArrayList<Medicine> getMedicationInventory(){
        return medicalInventoryService.viewInventory();
    }

    /**
     * Calls the submitReplenishmentRequest from the {@code MedicalInventoryService} class
     * @param medicineName
     * @param requestAmount
     */
    public void submitReplenishmentRequest(String medicineName,int requestAmount){
        medicalInventoryService.submitReplenishmentRequest(medicineName,requestAmount);
    }

    /**
     * Gets the {@code Appointment} with the corresponding input AppointmentID
     * @param appID
     * @return The {@code Appointment} with the corresponding input AppointmentID if it exists, null otherwise
     */
    public Appointment getAppointmentByID(String appID) {
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if(appID.equals(appointment.getAppID())) return appointment;
		}
		return null;
	}

    /**
     * Calls the addNewPrescription function of the {@code AppointmentService} class
     * @param appointment
     * @param dateIssued
     * @param medicine
     * @param dosage
     * @param instructions
     */
    public void addNewPrescription(Appointment appointment, String dateIssued, String medicine, String dosage, String instructions) {
    	appointmentService.addNewPrescription(appointment, dateIssued, medicine, dosage, instructions);
    }

    /**
     * Sets the status of the input {@code Appointment} to {@code Completed}
     * @param appointment
     */
    public void updatePrescriptionStatus(Appointment appointment) {
        appointment.setStatusOfApp(Status.Completed);
    }

    /**
     * Calls the decrementStock of the {@code MedicalInventoryService} class
     * @param med
     * @param usedAmount
     */
    public void decrementStock(Medicine med, int usedAmount) {
    		medicalInventoryService.decrementStock(med.getNameOfMedicine(), usedAmount);
    }

    /**
     * Finds a {@code Medicine} with the corresponding input name if it exists
     * @param name
     * @return The {@code Medicine} with the corresponding input name if it exists, null otherwise
     */
    public Medicine findMedicineByName(String name) {
        for (Medicine medicine : MedicationInventory.getInventory()) {
            if (medicine.getNameOfMedicine().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }
} 
