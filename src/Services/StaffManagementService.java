package Services;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.HospitalStaff;
import Entity.User.Pharmacist;
import Services.UserAccount.AdministratorAccountService;
import Services.UserAccount.DoctorAccountService;
import Services.UserAccount.PharmacistAccountService;

public class StaffManagementService {
	private DoctorAccountService doctorAccountService;
	private AdministratorAccountService administratorAccountService;
	private PharmacistAccountService pharmacistAccountService;
	private HospitalStaffRegistrationService hospitalStaffRegistrationService;

	public StaffManagementService(DoctorAccountService doctorAccountService,AdministratorAccountService administratorAccountService,
								  PharmacistAccountService pharmacistAccountService, HospitalStaffRegistrationService hospitalStaffRegistrationService) {
		this.doctorAccountService = doctorAccountService;
		this.administratorAccountService = administratorAccountService;
		this.pharmacistAccountService = pharmacistAccountService;
		this.hospitalStaffRegistrationService = hospitalStaffRegistrationService;
	}

	public void addStaffMember(HospitalStaff staff, String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer) {
		switch (staff.getDomain()) {
			case DOCTOR:
				if (staff instanceof Doctor doctor) {
					Doctor.getDoctorList().add(doctor);
					hospitalStaffRegistrationService.registerDoctorAccount(
							doctor, plainTextPassword, securityQuestion, plainTextSecurityAnswer
					);
				} else {
					System.out.println("The staff member is not a Doctor.");
				}
				break;

			case PHARMACIST:
				if (staff instanceof Pharmacist pharmacist) {
					Pharmacist.getPharmacistList().add(pharmacist);
					hospitalStaffRegistrationService.registerPharmacistAccount(
							pharmacist, plainTextPassword, securityQuestion, plainTextSecurityAnswer
					);
				} else {
					System.out.println("The staff member is not a Pharmacist.");
				}
				break;

			case ADMINISTRATOR:
				if (staff instanceof Administrator admin) {
					Administrator.getAdministratorList().add(admin);
					hospitalStaffRegistrationService.registerAdministratorAccount(
							admin, plainTextPassword, securityQuestion, plainTextSecurityAnswer
					);
				} else {
					System.out.println("The staff member is not an Administrator.");
				}
				break;

			default:
				System.out.println("Error!! Domain not recognised.");
				break;
		}
	}
	
	public void removeStaffMember(HospitalStaff staff) {
		switch(staff.getDomain()) {
		case DOCTOR:
			Doctor.getDoctorList().remove((Doctor) staff);
			removeDoctorAccount(staff.getUserID());
			break;
		case PHARMACIST:
			Pharmacist.getPharmacistList().remove((Pharmacist) staff);
			removePharmacistAccount(staff.getUserID());
			break;
		case ADMINISTRATOR:
			Administrator.getAdministratorList().remove((Administrator) staff);
			removeAdministratorAccount(staff.getUserID());
			break;
		default:
			break;
		}
	}
	
	public void updateStaffMember(HospitalStaff staff, String newName, int age, Gender gender, Domain newRole, Department dept) {
		staff.setName(newName);
        staff.setAge(age);
        staff.setGender(gender);
		staff.updateDepartment(newRole,dept);
        try {
            staff.setDomain(newRole);
            System.out.println("Updated staff member: " + staff.getName());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role. Please enter DOCTOR, PHARMACIST, or ADMINISTRATOR.");
        }
	}

	public String getUserName(Domain domain){
		return hospitalStaffRegistrationService.getUserName(domain);
	}

	public void removeDoctorAccount(String userID){
		doctorAccountService.deleteUserAccount(userID);
	}

	public void removePharmacistAccount(String userID){
		pharmacistAccountService.deleteUserAccount(userID);
	}

	public void removeAdministratorAccount(String userID){
		administratorAccountService.deleteUserAccount(userID);
	}
}
