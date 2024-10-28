import java.util.Scanner;

public class Patient extends User {
    public Patient(String name, String hospitalId,domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        Schedule schedule = new Schedule();
        int choice;
        do {
            System.out.println( "Choose the number of function:\n"
                    + "(1) Schedule Appointment\n"
                    + "(2) Reschedule Appointment\n"
                    + "(3) Cancel Appointment\n"
                    //+ "(4) View Available Appointment Slots\n" // Maybe call this under Schedule appointments?
                    + "(4) View your scheduled Appointments\n"
                    + "(5) View Appointment Outcome Records\n"
                    + "(6) Exit\n");
            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine(); // Buffer
            switch(choice) {
                case 1:
                    System.out.println("Enter your preferred Doctor: "); // print out all doctors from the same specialisation of the purpose of appointment?
                    // group doctors in terms of their department eg. Heart, Neuro, ...
                    // print all doctors
                    System.out.println("Enter your preferred time of appointment: ");
                    // print out all available time slots of the doctor for the rest of the week
                    String appID1 = InputScanner.sc.nextLine().trim();
                    if(!schedule.checkAppIDExist(appID1)) {
                        System.out.println("Only enter available Appointment IDs"); // problem with this statement
                        break;
                    }
                    System.out.println("Choose number of the purpose of your Appointment:\n"
                            + "(1) CheckUp\n"
                            + "(2) Surgery\n"
                            + "(3) Consultation\n"
                            + "(4) Other\n");
                    int choicePur = InputScanner.sc.nextInt();
                    InputScanner.sc.nextLine(); // Buffer
                    purpose pur;
                    if(choicePur == 1) pur = purpose.CheckUp;
                    else if (choicePur == 2) pur = purpose.Surgery;
                    else if (choicePur == 3) pur = purpose.Consultation;
                    else pur = purpose.Other;
                    schedule.scheduleAppointment(appID1, this.getHospitalId(), this.getName(), pur.name());
                    break;
                case 2:
                    System.out.println("Enter the Appointment ID that you want to change: ");
                    String orgAppID = InputScanner.sc.nextLine().trim();
                    if(!schedule.checkAppIDExist(orgAppID)) {
                        System.out.println("Only enter available Appointment IDs");
                        break;
                    }
                    System.out.println("Enter the new Appointment ID: "); // Enter new preferred timeslot
                    String newAppID = InputScanner.sc.nextLine().trim();
                    if(!schedule.checkAppIDExist(newAppID)) {
                        System.out.println("Only enter available Appointment IDs");
                        break;
                    }
                    System.out.println("Enter your patient ID: ");
                    String patID4 = InputScanner.sc.nextLine().trim();
                    schedule.rescheduleAppointment(orgAppID, newAppID, patID4);
                    break;
                case 3:
                    System.out.println("Enter the Appointment ID that you want to cancel: ");
                    String appID2 = InputScanner.sc.nextLine().trim();
                    if(!schedule.checkAppIDExist(appID2)) {
                        System.out.println("Only enter available Appointment IDs");
                        break;
                    }
                    System.out.println("Enter your Patient ID: ");
                    String patID1 = InputScanner.sc.nextLine().trim();
                    schedule.cancelAppointment(appID2, patID1);
                    break;
                case 4:
                    //System.out.println("Enter your Patient ID: ");
                    //String patID2 = InputScanner.sc.nextLine().trim();
                    schedule.viewScheduledAppointments(this.getHospitalId());
                    break;
                default:
                    System.out.println("Thank you for using our service!!");
                    break;
            }
        } while(choice>=1 && choice<=4);
    }
}


