

import java.util.Scanner;

public class ScheduleApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Schedule schedule = new Schedule();
		System.out.println("Welcom to Schedule App\n");
		int choice;
		do {
			System.out.println("Choose the number of function:\n"
					+ "(1) Create Appointment Slots (for staff later)\n"
					+ "(2) Schedule Appointment\n"
					+ "(3) Reschedule Appointment\n"
					+ "(4) Cancel Appointment\n"
					+ "(5) View Available Appointment Slots\n"
					+ "(6) View your scheduled Appointments\n"
					+ "(7) View Appointment Outcome Records\n"
					+ "(8) Change the Appointment Status (for staff)\n"
					+ "(9) Change the PaymentStatus (for staff)\n"
					+ "(10) Add or Change the cost (for staff)\n"
					+ "(11) Exit");
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
				case 1:
					System.out.println("Enter Appointment ID: ");
					String appID = sc.nextLine();
					System.out.println("Enter Date and Time for the Appointment Slot: (dd-MM-yyyy HH:mm:ss)");
					String time = sc.nextLine();
					System.out.println("Enter the Doctor ID: ");
					String docID = sc.nextLine();
					System.out.println("Enter the Doctor's Name: ");
					String docName = sc.nextLine();
					Appointment app = new Appointment(appID, time, docID, docName);
					schedule.createAppointmentSlot(app);
					break;
				case 2:
					System.out.println("Enter the Appointment ID you want to book: ");
					String appID1 = sc.nextLine();
					System.out.println("Enter your Patient ID: ");
					String patID = sc.nextLine();
					System.out.println("Enter your Name: ");
					String patName = sc.nextLine();
					System.out.println("Choose number of the purpose of your Appointment:\n"
							+ "(1) CheckUp\n"
							+ "(2) Surgery\n"
							+ "(3) Consultation\n"
							+ "(4) Other\n");
					int choicePur = sc.nextInt();
					purpose pur;
					if(choicePur == 1) pur = purpose.CheckUp;
					else if (choicePur == 2) pur = purpose.Surgery;
					else if (choicePur == 3) pur = purpose.Consultation;
					else pur = purpose.Other;
					schedule.scheduleAppointment(appID1, patID, patName, pur.name());
					break;
				case 3:
					System.out.println("Enter the Appointment ID that you want to change: ");
					String orgAppID = sc.nextLine();
					System.out.println("Enter the new Appointment ID: ");
					String newAppID = sc.nextLine();
					System.out.println("Enter your patient ID: ");
					String patID4 = sc.nextLine();
					schedule.rescheduleAppointment(orgAppID, newAppID, patID4);
					break;
				case 4:
					System.out.println("Enter the Appointment ID that you want to cancel: ");
					String appID2 = sc.nextLine();
					System.out.println("Enter your Patient ID: ");
					String patID1 = sc.nextLine();
					schedule.cancelAppointment(appID2, patID1);
					break;
				case 5:
					schedule.viewAvailAppointmentSlots();
					break;
				case 6:
					System.out.println("Enter your Patient ID: ");
					String patID2 = sc.nextLine();
					schedule.viewScheduledAppointments(patID2);
					break;
				case 7:
					System.out.println("Enter your Patient ID: ");
					String patID3 = sc.nextLine();
					schedule.viewAppointmentOutcomeRecords(patID3);
					break;
				case 8:
					System.out.println("Enter the appointment ID: ");
					String appID3 = sc.nextLine();
					System.out.println("Choose number of the Appointment status to change\n"
							+ "(1) Confirmed\n"
							+ "(2) Cancelled\n"
							+ "(3) Completed\n");
					int statusChoice = sc.nextInt();
					status stat;
					if(statusChoice == 1) stat = status.Confirmed;
					else if (statusChoice == 2) stat = status.Cancelled;
					else if (statusChoice == 3) stat = status.Completed;
					else break;
					schedule.changeAppointmentStatus(appID3, stat);
					break;
				case 9:
					System.out.println("Enter the appointment ID: ");
					String appID4 = sc.nextLine();
					System.out.println("Choose number of the payment status to change\n"
							+ "(1) Prepaid\n"
							+ "(2) Pending\n"
							+ "(3) Completed\n"
							+ "(4) Overdued\n"
							+ "(5) Cancelled\n");
					int paymentChoice = sc.nextInt();
					paymentStatus payStatus;
					if(paymentChoice == 1) payStatus = paymentStatus.Prepaid;
					else if (paymentChoice == 2) payStatus = paymentStatus.Pending;
					else if (paymentChoice == 3) payStatus = paymentStatus.Completed;
					else if (paymentChoice == 4) payStatus = paymentStatus.Overdued;
					else if (paymentChoice == 5) payStatus = paymentStatus.Cancelled;
					else break;
					schedule.changePaymentStatus(appID4, payStatus);
					break;
				case 10:
					System.out.println("Enter the appointment ID: ");
					String appID5 = sc.nextLine();
					System.out.println("Enter the cost: ");
					double cost = sc.nextDouble();
					schedule.addChangeCost(appID5, cost);
					break;
				default:
					System.out.println("Thank you for using our service!!");
					break;
			}
		} while(choice>=1 && choice<=10);
		
		sc.close();
	}

}
