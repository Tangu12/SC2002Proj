package Controllers.IController.IAccountManagement;

import Entity.Enums.Department;
import Entity.Enums.Gender;
import Entity.User.HospitalStaff;

import java.util.Optional;

public interface IManageStaff {
    void addStaffMember(HospitalStaff staff, String plainTextPassword, String securityQuestion, String plainTextSecurityAnswer);
    Optional<HospitalStaff> findStaffById(String hospitalId);
    void removeStaffMember(String userID);
    void updateStaffMember(String userID, String newName, int age, Gender gender, Department dep);
}
