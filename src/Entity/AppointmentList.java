package Entity;

import java.util.ArrayList;

public class AppointmentList {
	private ArrayList<Appointment> appointmentList;
    private static AppointmentList instance;

    private AppointmentList() {
        this.appointmentList = new ArrayList<>();
    }

    /*
    Singleton, makes sure that there is only one instance in the whole program
    */
    public static AppointmentList getInstance() {
        if (instance == null) {
            instance = new AppointmentList();
        }
        return instance;
    }

    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }
}
