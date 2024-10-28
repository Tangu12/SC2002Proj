public class Doctor extends User {
    public Doctor(String name, String hospitalId, domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        int choice;
        System.out.println( "Choose the number of function:\n"
                + "(1) Create Appointment Slots (for staff later)\n"
                + "(2) View your scheduled Appointments\n"  // Doctors should be able to do this too..?
                + "(3) View Appointment Outcome Records\n"
                + "(4) Change the Appointment Status (for staff)\n"
                + "(5) Change the PaymentStatus (for staff)\n"
                + "(6) Add or Change the cost (for staff)\n"
                + "(7) Exit");
        choice = InputScanner.sc.nextInt();

        switch(choice) {

        }
    }


}


// Method to find current schedule of doctor