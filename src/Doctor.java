import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    public Doctor(String name, String hospitalId, Domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        Schedule schedule = new Schedule();
        int choice;
        do {
            System.out.println( "Choose the number of function:\n"
                    + "(1) Create Appointment Slots\n" // should change to set busy timings, function to choose which appt to accept and decline
                    + "(2) View your scheduled Appointments\n"  // Doctors should be able to do this too..?
                    + "(3) View Appointment Outcome Records\n"
                    + "(4) Change the Appointment Status\n"
                    + "(5) Exit");
            choice = InputScanner.sc.nextInt();
            InputScanner.sc.nextLine();

            switch(choice) {
                case 1:
                    schedule.createAppointmentSlot();
                    System.out.println("Appointment Slots are successfully created!!");
                    break;
                case 2:
                    schedule.viewScheduledAppointments(this.getHospitalId());
                    break;
                case 3:
                    schedule.viewAppointmentOutcomeRecords(this.getHospitalId());
                    break;
                case 4:
                    System.out.println("Enter the appointment ID: ");
                    String appID = InputScanner.sc.nextLine().trim();
                    if(!schedule.checkAppIDExist(appID)) {
                        System.out.println("Only enter available Appointment IDs");
                        break;
                    }
                    System.out.println("Choose number of the Appointment status to change\n"
                            + "(1) Confirmed\n"
                            + "(2) Cancelled\n"
                            + "(3) Completed\n"
                            + "(4) Pending");
                    int statusChoice = InputScanner.sc.nextInt();
                    status stat;
                    if(statusChoice == 1) stat = status.Confirmed;
                    else if (statusChoice == 2) stat = status.Cancelled;
                    else if (statusChoice == 3) stat = status.Completed;
                    else if (statusChoice == 4) stat = status.Pending;
                    else break;
                    schedule.changeAppointmentStatus(appID, stat);
                    break;
                default:
                    System.out.println("Thank you for using our service!!");
                    break;
            }
        } while(choice>=1 && choice<=4);
    }

//    public void listOfDoctors() {
//        List<String> doctors = new ArrayList<>();
//        Schedule schedule = new Schedule();
//        List<String[]> data = schedule.getAllRows();
//        for(String[] row : data) {
//            if (row[4].equals(appID) && (row[0].equals("FALSE") || row[0].equals("false")) && !row[8].equals("Cancelled")) {
//                row[9] = String.valueOf(cost);
//                foundData = true;
//                break;
//            }
//        }


//    }
}


// Method to find current schedule of doctor