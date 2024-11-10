public class Main {
    public static void main(String[] args) {
        // User.changePassword("D001", "Johnny123");
    	User user;
		Schedule.loadAppointments();
		Patient.loadPatientlist();
		HospitalStaff.loadAdministrator();
		HospitalStaff.loadDoctorList();
		HospitalStaff.loadPharmacistList();
		MedicationInventory.loadInventoryFromFile();
    	user = User.displayLoginInterface();// return domain of user
        if (user == null) {
            return;
        }
        User.homePage(user);
        Schedule.updateAppointmentFile(Schedule.getAppointmentList());
        MedicationInventory.updateInventoryFile(MedicationInventory.getInventory());
        HospitalStaff.updateHospitalStaffFile(HospitalStaff.getAdministratorList(), HospitalStaff.getDoctorList(), HospitalStaff.getPharmacistList());
        // User.changePassword("D001", "James123");
    }
}