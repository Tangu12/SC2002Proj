package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

import java.util.ArrayList;

/*
 * This is the Doctor object, the doctor has a new attribute which is the department (eg neurological department)
 * */


public class Doctor extends HospitalStaff{

    private static ArrayList<Doctor> doctorList = new ArrayList<>();
    private Department department;
    
    public Doctor(String userID, String name, int age, Gender gender) {
    	super(userID, name, age, gender, Domain.DOCTOR);
    }
    
    public static void setDoctorList(ArrayList<Doctor> docList) {
    	Doctor.doctorList = docList;
    }
    
    public static ArrayList<Doctor> getDoctorList() {
    	return Doctor.doctorList;
    }
    
    public void setDepartment(Department dep) {
    	this.department = dep;
    }
    
    public Department getDepartment() {
    	return this.department;
    }
}
