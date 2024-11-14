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
	
    public void addStaffMember(HospitalStaff staff) {
        staffManagementService.addStaffMember(staff);
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
    
    public void updateStaffMember(String userID, String newName, int age, Gender gender, Domain newRole, Department dep) {
    	Optional<? extends HospitalStaff> staffToUpdate = findStaffById(userID);
        if (staffToUpdate.isPresent()) {
        	HospitalStaff staff = staffToUpdate.get();
            staffManagementService.updateStaffMember(staff, newName, age, gender, newRole, dep);
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
    
    public ArrayList<Appointment> getAppointmentsByDoctorID(String doctorID) {
		ArrayList<Appointment> filteredAppointments = new ArrayList<>();
		for(Appointment appointment : AppointmentList.getInstance().getAppointmentList()) {
			if(appointment.getDocID().equals(doctorID)) filteredAppointments.add(appointment);
		}

		return filteredAppointments;
	}
    
    public boolean isMedicineInInventory(String name) {
        return findMedicineByName(name) != null;
    }

    // Helper method to find a medicine by its name
    public Medicine findMedicineByName(String name) {
        for (Medicine medicine : MedicationInventory.getInventory()) {
            if (medicine.getNameOfMedicine().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }
    
    public void increaseCurrentStock(int stock, int selection) {
    	medicalInventoryService.incrementStock(MedicationInventory.getInventory().get(selection-1).getNameOfMedicine(), stock);
    }
    
    public void addMedicine(String name, int stock, int alertLevel) {
    	Medicine med = new Medicine(name, stock, alertLevel);
    	medicalInventoryService.addMedicineToInventory(med);
    }
    
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
    
    public void updateMedicine(Medicine med, int newStock, int newAlertLevel) {
    	medicalInventoryService.updateMedicine(med);
    }
    
    public void decrementStock(Medicine med, int usedAmount) {
    	medicalInventoryService.decrementStock(med.getNameOfMedicine(), usedAmount);
    }

    public ArrayList<String> getAllLockedUserIDs() {
        return accountManager.getAllLockedUserIDs();
    }

    public ArrayList<String> getAllUserIDs() {
        return accountManager.getAllUserIDs();
    }

    public void lockAccount(String userID) {
        accountManager.lockAccount(userID);
    }

    public void unlockAccount(String userID){
        accountManager.unlockAccount(userID);
    }
}
