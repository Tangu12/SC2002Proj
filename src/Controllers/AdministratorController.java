package Controllers;

import java.util.ArrayList;
import java.util.Optional;

import Controllers.IController.IAppointment.IAdministratorGetAppointment;
import Controllers.IController.IAccountManagement.ILockAccount;
import Controllers.IController.IAccountManagement.IManageStaff;
import Controllers.IController.IMedicine.IAdministratorMedicine;
import Entity.Appointment;
import Entity.AppointmentList;
import Entity.MedicationInventory;
import Entity.Medicine;
import Entity.Enums.Department;
import Entity.Enums.Gender;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.HospitalStaff;
import Entity.User.Pharmacist;
import Services.MedicalInventoryService;
import Services.StaffManagementService;
import Services.UserAccount.AccountManager;

/**
 * {@code AdministratorController} handles all the logic a Administrator's Functions
 */
public class AdministratorController implements ILockAccount, IManageStaff, IAdministratorGetAppointment, IAdministratorMedicine {
    //public static void operation(){}
    
	private Administrator admin;
	private StaffManagementService staffManagementService;
	private MedicalInventoryService medicalInventoryService;
    private AccountManager accountManager;

    /**
     * Constructor for {@code AdministratorController}
     * @param admin the instance of the Administrator
     * @param staffManagementService the instance of the StaffManagementService
     * @param medicalInventoryService the instance of the MedicalInventoryService
     * @param accountManager the instance of the AccountManager
     */
	public AdministratorController(Administrator admin, StaffManagementService staffManagementService,
                                   MedicalInventoryService medicalInventoryService, AccountManager accountManager) {
		this.admin = admin;
		this.staffManagementService = staffManagementService;
		this.medicalInventoryService = medicalInventoryService;
        this.accountManager = accountManager;
	}

    /**
     * Gets the instance of a {@code Administrator}
     * @return The instance of the {@code Administrator}
     */
	public Administrator getAdministrator() {
		return this.admin;
	}

    /**
     * Gets the instance of a {@code MedicationInventory}
     * @return The instance of the {@code MedicationInventory}
     */
    public ArrayList<Medicine> getMedicationInventory(){
        return medicalInventoryService.viewInventory();
    }

    public void addStaffMember(HospitalStaff staff, String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer) {
        staffManagementService.addStaffMember(staff, plainTextPassword, securityQuestion, plainTextSecurityAnswer);
    }

    /**
     * Searches for a hospital staff member by their unique hospital ID across different staff types if they exist
     * @param hospitalId the User's HospitalID
     * @return The {@code HospitalStaff} object if they exist, or an empty {@link Optional} if no staff member with the given ID exists
     */
    public Optional<HospitalStaff> findStaffById(String hospitalId) {
    	return Doctor.getDoctorList().stream().filter(d -> d.getUserID().equals(hospitalId)).map(d -> (HospitalStaff) d).findFirst()
                .or(() -> Pharmacist.getPharmacistList().stream().filter(p -> p.getUserID().equals(hospitalId)).map(p -> (HospitalStaff) p).findFirst())
                .or(() -> Administrator.getAdministratorList().stream().filter(a -> a.getUserID().equals(hospitalId)).map(a -> (HospitalStaff) a).findFirst());
    }

    /**
     * Searches for a hospital staff member by their unique hospital ID across different staff types and removes them if they exist
     * @param userID the User's HospitalID
     */
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

    /**
     * Updates the parameters of {@code HospitalStaff} member if they exist
     * @param userID the User's HospitalID
     * @param newName the User's new name
     * @param age the User's new name age
     * @param gender the User's new name Gender
     * @param dep the User's new Department
     */
    public void updateStaffMember(String userID, String newName, int age, Gender gender, Department dep) {
    	Optional<? extends HospitalStaff> staffToUpdate = findStaffById(userID);
        if (staffToUpdate.isPresent()) {
        	HospitalStaff staff = staffToUpdate.get();
            staffManagementService.updateStaffMember(staff, newName, age, gender, staff.getDomain(), dep);
        } else {
            System.out.println("Staff member with ID " + userID + " not found.");
        }
    }

    /**
     * Gets the list of {@code Appointment}s with a given {@code Appointment} status
     * @param status the Status of a appointment
     * @return A array list of {@code Appointment}s with the corresponding input status
     */
    public ArrayList<Appointment> getAppointmentsByStatus(String status) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();

		for (Appointment row : AppointmentList.getInstance().getAppointmentList()) {
			if (row.getStatusOfApp() != null && row.getStatusOfApp().toString().equalsIgnoreCase(status)) {
				filteredAppointments.add(row);
			}
		}
		return filteredAppointments;
	}

    /**
     * Returns the {@code Appointment} with the corresponding AppointmentID as the input
     * @param appID the AppointmentID of a appointment
     * @return A {@code Appointment} with the corresponding AppointmentID as the input
     */
    public Appointment getAppointmentByID(String appID) {
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if(appID.equals(appointment.getAppID())) return appointment;
		}
		return null;
	}

    /**
     * Gets all the {@code Appointment}s belonging to a {@code Patient} based on their {@code HospitalID}
     * @param patientID the Patient's HospitalID
     * @return A array list of {@code Appointment}s that belong to the corresponding {@code Patient}
     */
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
     * @param doctorID the Doctor's AppointmentID
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
     * @param name Medicine's name
     * @return True or False
     */
    public boolean isMedicineInInventory(String name) {
        return findMedicineByName(name) != null;
    }

    /**
     * Finds a medicine based on its name
     * @param name Medicine's name
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
     * @param stock Current Medicine stock
     * @param selection Index of Medicine in the Medicine list of the chossen Medicine
     */
    public void increaseCurrentStock(int stock, int selection) {
    	medicalInventoryService.incrementStock(MedicationInventory.getInventory().get(selection-1).getNameOfMedicine(), stock);
    }

    /**
     * Adds a new medicine to the medicine inventory with its initial amount and low level alert value
     * @param name Medicine's name
     * @param stock Medicine's stock
     * @param alertLevel Medicine's low level alert
     */
    public void addMedicine(String name, int stock, int alertLevel) {
    	Medicine med = new Medicine(name, stock, alertLevel);
    	medicalInventoryService.addMedicineToInventory(med);
    }

    /**
     * Removes a medicine from the medicine inventory
     * @param choice User's choice
     */
    public void removeMedicine(int choice) {
        if(choice <= 0 || choice > MedicationInventory.getInventory().size()) { //user input unavailable choice
        	System.out.println("Please only select from the available choices: ");
        	return;
        }
        Medicine medicine = findMedicineByName(MedicationInventory.getInventory().get(choice-1).getNameOfMedicine());
        if(medicine != null) {
        	System.out.println("Medicine removed: " + MedicationInventory.getInventory().get(choice-1).getNameOfMedicine());
        	medicalInventoryService.removeMedicineFromInventory(medicine.getNameOfMedicine());
        	medicalInventoryService.saveInventoryToFile();
        } else {
            System.out.println("Medicine not found.");
        }
    }

    /**
     *
     * @param med Medicine's name
     * @param newStock Medicine's new stock
     * @param newAlertLevel Medicine's new low level alert
     */
    public void updateMedicine(Medicine med, int newStock, int newAlertLevel) {
    	med.setCurrentStock(newStock);
    	med.setLowStockLevelAlert(newAlertLevel);
    	medicalInventoryService.updateMedicine(med);
    	medicalInventoryService.saveInventoryToFile();
    }

    /**
     * Decreases the amount of  a specific medicine inside the medicine inventory
     * @param med Medicine's name
     * @param usedAmount Medicine's used amount
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
     * @param userID User's HospitalID
     */
    public void lockAccount(String userID) {
        accountManager.lockAccount(userID);
    }

    /**
     * Unlocks the account of a user
     * @param userID User's HospitalID
     */
    public void unlockAccount(String userID){
        accountManager.unlockAccount(userID);
    }

    /**
     * Handles the replenishment request made by the pharmacist
     * @param selection User's selection
     * @param amount Amount of stock of a certain Medicine that's replenished
     */
    public void processReplenishmentRequests(int selection, int amount) {
    		medicalInventoryService.processReplenishmentRequests(selection, amount);
    		medicalInventoryService.saveInventoryToFile();
    }
}
