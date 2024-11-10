public class Main {
    public static void main(String[] args) {
        // User.changePassword("D001", "Johnny123");
    	User user;
		Schedule.loadAppointments();
		Patient.loadPatientlist();
		HospitalStaff.loadAdministrator();
		HospitalStaff.loadDoctorList();
		HospitalStaff.loadPharmacistList();
    	user = User.displayLoginInterface();// return domain of user
        if (user == null) {
            return;
        }
        User.homePage(user);
        Schedule.updateAppointmentFile(Schedule.getAppointmentList());
        // User.changePassword("D001", "James123");
    }
}