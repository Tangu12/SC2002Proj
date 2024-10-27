import java.util.Scanner;

public class Patient extends User {
    public Patient(String name, String hospitalId,domain domain) {
        super(name, hospitalId, domain);
    }

    public void homePage() {
        int choice;
        System.out.println( "Choose the number of function:\n"
                + "(1) Schedule Appointment\n"
                + "(2) Reschedule Appointment\n"
                + "(3) Cancel Appointment\n"
                + "(4) View Available Appointment Slots\n"
                + "(5) View your scheduled Appointments\n"
                + "(6) View Appointment Outcome Records\n");
        choice = InputScanner.sc.nextInt();

        switch(choice) {

        }
    }
}

