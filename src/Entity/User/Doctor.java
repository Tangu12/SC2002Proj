package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.MedicalRecord;

import java.util.ArrayList;

/*
 * This is the Doctor object, the doctor has a new attribute which is the department (eg neurological department)
 * */


public class Doctor extends HospitalStaff implements IUser{
    String userID;
    String name;
    int age;
    Gender gender;

    Domain domain = Domain.DOCTOR;
    // Add more attributes as needed
    Department department;

    ArrayList<Patient> doctorPatients;

    // Getters
    @Override
    public String getUserId() {
        return super.getUserID();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public Gender getGender() {
        return this.gender;
    }

    @Override
    public Domain getDomain() {
        return this.domain;
    }

    public Department getDepartment() {return this.department;}

    public ArrayList<Patient> getDoctorPatient() {return this.doctorPatients; }

    // Setters
    @Override
    public void setUserId(String userId) {
        this.userID = userId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public void setDepartment(Department department) {this.department = department;}

    public void setDoctorPatients(ArrayList<Patient> doctorPatients) {this.doctorPatients = doctorPatients;}
}
