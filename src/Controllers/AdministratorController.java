package Controllers;

import java.util.ArrayList;
import java.util.Optional;

import Entity.Appointment;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.HospitalStaff;
import Entity.User.Pharmacist;
import Services.MedicalInventoryService;
import Services.StaffManagementService;
import Services.UserAccount.AccountManager;

public class AdministratorController {
    //public static void operation(){}
    
	private Administrator admin;
	private StaffManagementService staffManagementService;
	private MedicalInventoryService medicalInventoryService;
    private AccountManager accountManager;
	
	public AdministratorController(Administrator admin, StaffManagementService staffManagementService,
                                   MedicalInventoryService medicalInventoryService, AccountManager accountManager) {
		this.admin = admin;
		this.staffManagementService = staffManagementService;
		this.medicalInventoryService = medicalInventoryService;
        this.accountManager = accountManager;
	}
	
	public Administrator getAdministrator() {
		return this.admin;
	}

    public ArrayList<Medicine> getMedicationInventory(){
        return medicalInventoryService.viewInventory();
    }

    public void addStaffMember(HospitalStaff staff, String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer) {
        staffManagementService.addStaffMember(staff, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
    }
    
    public Optional<HospitalStaff> findStaffById(String hospitalId) {
    	return Doctor.getDoctorList().stream().filter(d -> d.getUserID().equals(hospitalId)).map(d -> (HospitalStaff) d).findFirst()
                .or(() -> Pharmacist.getPharmacistList().stream().filter(p -> p.getUserID().equals(hospitalId)).map(p -> (HospitalStaff) p).findFirst())
                .or(() -> Administrator.getAdministratorList().stream().filter(a -> a.getUserID().equals(hospitalId)).map(a -> (HospitalStaff) a).findFirst());
    }
    
    public void removeStaffMember(String userID) {
    	Optional<? extends HospitalStaff> staffToRemove = findStaffById(userID);

        if (staffToRemove.isPresent()) {
        	HospitalStaff staff = staffToRemove.get();
            if (staff instanceof Doctor doctor) {
                staffManagementService.removeStaffMember(doctor);
            } else if (staff instanceof Pharmacist pharmacist) {
                Pharmacist.getPharmacistList().remove(pharmacist);
                staffManagementService.removeStaffMember(pharmacist);
            } else if (staff instanceof Administrator admin) {
                Administrator.getAdministratorList().remove(admin);
                staffManagementService.removeStaffMember(admin);
            }
            System.out.println("Removed staff member: " + staff.getName());
        } else {
            System.out.println("Staff member with ID " + userID + " not found.");
        }
    }
    
    public void updateStaffMember(String userID, String newName, int age, Gender gender, Department dep) {
    	Optional<? extends HospitalStaff> staffToUpdate = findStaffById(userID);
        if (staffToUpdate.isPresent()) {
        	HospitalStaff staff = staffToUpdate.get();
            staffManagementService.updateStaffMember(staff, newName, age, gender, staff.getDomain(), dep);
        } else {
            System.out.println("Staff member with ID " + userID + " not found.");
        }
    }
    
    public ArrayList<Appointment> getAppointmentsByStatus(String status) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();

		for (Appointment row : AppointmentList.getInstance().getAppointmentList()) {
			if (row.getStatusOfApp() != null && row.getStatusOfApp().toString().equalsIgnoreCase(status)) {
				filteredAppointments.add(row);
			}
		}

		return filteredAppointments;
	}
    
    public Appointment getAppointmentByID(String appID) {
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if(appID.equals(appointment.getAppID())) return appointment;
		}
		return null;
	}
    
    public ArrayList<Appointment> getAppointmentsByPatientID(String patientID) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if (appointment.getPatID().equals(patientID))
				filteredAppointments.add(appointment);
		}

		return filteredAppointments;
	}

    /**
     * Returns the array list of appointments that belong to a doctor based on their hospitalID
     * @param doctorID
     * @return
     */
    public ArrayList<Appointment> getAppointmentsByDoctorID(String doctorID) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if(appointment.getDocID().equals(doctorID)) filteredAppointments.add(appointment);
		}

		return filteredAppointments;
	}

    /**
     * Checks if a medicine with a certain name exists in the medicine inventory
     * @param name
     * @return True or False
     */
    public boolean isMedicineInInventory(String name) {
        return findMedicineByName(name) != null;
    }

    /**
     * Finds a medicine based on its name
     * @param name
     * @return The Medicine object
     */
    // Helper method to find a medicine by its name
    public Medicine findMedicineByName(String name) {
        for (Medicine medicine : MedicationInventory.getInventory()) {
            if (medicine.getNameOfMedicine().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }

    /**
     * Increases the number of a specific medicine inside the medicine inventory
     * @param stock
     * @param selection
     */
    public void increaseCurrentStock(int stock, int selection) {
    	medicalInventoryService.incrementStock(MedicationInventory.getInventory().get(selection-1).getNameOfMedicine(), stock);
    }

    /**
     * Adds a new medicine to the medicine inventory with its initial amount and low level alert value
     * @param name
     * @param stock
     * @param alertLevel
     */
    public void addMedicine(String name, int stock, int alertLevel) {
    	Medicine med = new Medicine(name, stock, alertLevel);
    	medicalInventoryService.addMedicineToInventory(med);
    }

    /**
     * Removes a medicine from the medicine inventory
     * @param choice
     */
    public void removeMedicine(int choice) {
        if(choice <= 0 || choice > MedicationInventory.getInventory().size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices: ");
        	return;
        }
        Medicine medicine = findMedicineByName(MedicationInventory.getInventory().get(choice-1).getNameOfMedicine());
        if(medicine != null) {
        	medicalInventoryService.removeMedicineFromInventory(medicine.getNameOfMedicine());
        	System.out.println("Medicine removed: " + MedicationInventory.getInventory().get(choice-1).getNameOfMedicine());
        } else {
            System.out.println("Medicine not found.");
        }
    }

    /**
     *
     * @param med
     * @param newStock
     * @param newAlertLevel
     */
    public void updateMedicine(Medicine med, int newStock, int newAlertLevel) {
    	medicalInventoryService.updateMedicine(med);
    }

    /**
     * Decreases the amount of  a specific medicine inside the medicine inventory
     * @param med
     * @param usedAmount
     */
    public void decrementStock(Medicine med, int usedAmount) {
    	medicalInventoryService.decrementStock(med.getNameOfMedicine(), usedAmount);
    }

    /**
     * Gathers all user accounts that are currently locked
     * @return Array list of all the hospitalIDs that are locked
     */
    public ArrayList<String> getAllLockedUserIDs() {
        return accountManager.getAllLockedUserIDs();
    }

    /**
     * Gathers all the currently existing hospitalIDs of everybody registered at the hospital
     * @return Array list of all the hospitalIDs
     */
    public ArrayList<String> getAllUserIDs() {
        return accountManager.getAllUserIDs();
    }

    /**
     * Gathers all user accounts that are currently locked
     * @return Array list of all the hospitalIDs that are unlocked
     */
    public ArrayList<String> getAllUnlockedUserIDs(){
        return accountManager.getAllUnlockedUserIDs();
    }

    /**
     * Locks the account of a user
     * @param userID
     */
    public void lockAccount(String userID) {
        accountManager.lockAccount(userID);
    }

    /**
     * Unlocks the account of a user
     * @param userID
     */
    public void unlockAccount(String userID){
        accountManager.unlockAccount(userID);
    }

    /**
     * Handles the replenishment request made by the pharmacist
     * @param selection
     * @param amount
     */
    public void processReplenishmentRequests(int selection, int amount) {
    		medicalInventoryService.processReplenishmentRequests(selection, amount);
    }
}
