package Controllers.IController.IAppointment;

import Entity.Appointment;
import Entity.Enums.Department;
import Entity.Enums.Purpose;
import Entity.User.Patient;

import java.util.ArrayList;
import java.util.List;

public interface IPatientAppointment {
    String selectionOfDoctor(Purpose purposeOfVisit, Department appDepartment, ArrayList<Appointment> appointmentList)throws Exception;
    int selectionOfTimeSlot(ArrayList<Appointment> appointmentList, List<Integer> timeSlotsIndices, int selection);
    int getAppIdIndexFromTime(ArrayList<Appointment> appointmentList, List<Integer> possibleAppsIndices, int selection);
    void scheduleAppointment(Patient pat, int appIndex, Purpose pur);
    void cancelAppointment(int oldIndex);
    boolean checkAppIDExist(String appID);

}
