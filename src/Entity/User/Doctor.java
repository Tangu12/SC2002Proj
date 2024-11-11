package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

import java.util.ArrayList;

/*
 * This is the Doctor object, the doctor has a new attribute which is the department (eg neurological department)
 * */


public class Doctor extends HospitalStaff{

    private static ArrayList<Doctor> doctorList;
    
    public Doctor(String userID, String name, int age, Gender gender, Department department) {
    	super(userID, name, age, gender, Domain.DOCTOR);
    	super.setDepartment(department);
    }
    
    public static void setDoctorList(ArrayList<Doctor> docList) {
    	Doctor.doctorList = docList;
    }
    
    public static ArrayList<Doctor> getDoctorList() {
    	return Doctor.doctorList;
    }
}
