package Services;

import java.util.Optional;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.User.Administrator;
import Entity.User.Doctor;
import Entity.User.HospitalStaff;
import Entity.User.Pharmacist;

public class StaffManagementService {
	public void addStaffMember(HospitalStaff staff) {
		switch(staff.getDomain()) {
		case DOCTOR:
			Doctor.getDoctorList().add((Doctor) staff);
			break;
		case PHARMACIST:
			Pharmacist.getPharmacistList().add((Pharmacist) staff);
			break;
		case ADMINISTRATOR:
			Administrator.getAdministratorList().add((Administrator) staff);
			break;
		default:
			break;
		}
	}
	
	public void removeStaffMember(HospitalStaff staff) {
		switch(staff.getDomain()) {
		case DOCTOR:
			Doctor.getDoctorList().remove((Doctor) staff);
			break;
		case PHARMACIST:
			Pharmacist.getPharmacistList().remove((Pharmacist) staff);
			break;
		case ADMINISTRATOR:
			Administrator.getAdministratorList().remove((Administrator) staff);
			break;
		default:
			break;
		}
	}
	
	public void updateStaffMember(HospitalStaff staff, String newName, int age, Gender gender, Domain newRole, Department dept) {
		staff.setName(newName);
        staff.setAge(age);
        staff.setGender(gender);
        if(newRole.equals(Domain.DOCTOR)) ((Doctor) staff).setDepartment(dept);
        try {
            staff.setDomain(newRole);
            System.out.println("Updated staff member: " + staff.getName() + " to role " + newRole.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role. Please enter DOCTOR, PHARMACIST, or ADMINISTRATOR.");
        }
	}
}
