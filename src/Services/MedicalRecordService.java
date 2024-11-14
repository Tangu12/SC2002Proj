package Services;

import Entity.Appointment;
import Entity.MedicalRecord;
import Entity.Repository.AppointmentsRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MedicalRecordService {
    private AppointmentsRepository appointmentsRepository;


    public MedicalRecordService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    public MedicalRecord convertAppointmentToMedicalRecord(Appointment appointment) {
        //DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy");
        MedicalRecord medicalRecord = new MedicalRecord(appointment.getAppID(), appointment.getPatID(), appointment.getAppointOutcomeRecord(), appointment.getMedicine(), appointment.getMedicineIssuedDate().toString(), appointment.getDosage(), appointment.getInstructions());
        return medicalRecord;
    }

    public MedicalRecord findMedicalRecord(String appointmentID) {
        Appointment appointment = appointmentsRepository.findAppointmentsByAppointmentId(appointmentID);
        MedicalRecord medicalRecord = convertAppointmentToMedicalRecord(appointment);
        return medicalRecord;
    }

    public ArrayList<MedicalRecord> findPatientsMedicalRecord(String userID) {
        ArrayList<Appointment> appointments = appointmentsRepository.findAppointmentsByPatientId(userID);
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
        for (Appointment app : appointments) {
            MedicalRecord medicalRecord = convertAppointmentToMedicalRecord(app);
            medicalRecords.add(medicalRecord);
        }
        return medicalRecords;
    }
}
