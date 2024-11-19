package Controllers.IController.IAppointment;

import Entity.Appointment;
import Entity.Enums.Status;

public interface IPharmacistAppointment {
    void updatePrescriptionStatus(Appointment appointment, int appIndex, Status status);
    Appointment getAppointmentByID(String appID);
    void addNewPrescription(Appointment appointment, String dateIssued, String medicine, String dosage, String instructions);
    void updatePrescriptionStatus(Appointment appointment);

}
