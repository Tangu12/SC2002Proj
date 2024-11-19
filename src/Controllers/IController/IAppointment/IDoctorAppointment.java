package Controllers.IController.IAppointment;

import Entity.Enums.Department;
import Entity.Enums.Status;

public interface IDoctorAppointment {
    void createAppointmentSlot(String docID, String docName, int plusDays, Department dep, int startTime, int endTime);
    void acceptOrDeclinePendingApp(int index, int decision);
    void updateApptOutcomeRecords(int index, Status status, String notes);
}
