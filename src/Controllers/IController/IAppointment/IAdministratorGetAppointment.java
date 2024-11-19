package Controllers.IController.IAppointment;

import Entity.Appointment;

import java.util.ArrayList;

public interface IAdministratorGetAppointment {
    ArrayList<Appointment> getAppointmentsByStatus(String status);
    Appointment getAppointmentByID(String appID);
    ArrayList<Appointment> getAppointmentsByPatientID(String patientID);
    ArrayList<Appointment> getAppointmentsByDoctorID(String doctorID);
}
