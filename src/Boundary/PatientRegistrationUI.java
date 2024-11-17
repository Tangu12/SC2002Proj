package Boundary;

import Controllers.PatientRegistrationController;
import Entity.Enums.BloodType;
import Entity.Enums.Domain;
import Entity.Enums.Gender;
import Entity.Repository.CredentialsRepository;
import Entity.User.Patient;
import Services.InputService;

import java.time.LocalDate;

/**
 * {@code patientRegistrationController}
 */
public class PatientRegistrationUI {
    private PatientRegistrationController patientRegistrationController;

    /**
     * Constructor for {@code patientRegistrationController}
     * @param patientRegistrationController
     */
    public PatientRegistrationUI(PatientRegistrationController patientRegistrationController) {
        this.patientRegistrationController = patientRegistrationController;
    }

    /**
     * Displays the UI when a new {@code Patient} wants to register for an account in the Hospital
     */
    public void registrationUI() {
        System.out.println("Welcome to Hospital Management System !");
        System.out.print("Your assigned PatientID is : ");
        System.out.print(patientRegistrationController.getUserName()+"\n");

        System.out.println("Please enter your password : ");
        String plainTextPassword = InputService.inputString();
        System.out.println("Please enter your Security Question : ");
        String securityQuestion = InputService.inputString();
        System.out.println("Please enter the answer to your Security Question : ");
        String plainTextSecurityAnswer = InputService.inputString().toLowerCase();

        System.out.println("Please enter your name : ");
        String name = InputService.inputString();
        System.out.println("Please enter your date of birth : ");
        LocalDate dob = InputService.inputDate();
        System.out.println("Your Age is : "+patientRegistrationController.calculateAge(dob));

        String contactInfo = InputService.inputEmail();
        System.out.println("Please Select a Gender");
        Gender gender = InputService.inputEnum(Gender.class);
        System.out.println("Please Select a Blood Type");
        BloodType bloodType = InputService.inputEnum(BloodType.class);

        Patient newUser = new Patient(patientRegistrationController.getUserName(),name,
                patientRegistrationController.calculateAge(dob),gender,Domain.PATIENT,contactInfo,dob,bloodType);
        patientRegistrationController.registerUser(newUser,plainTextPassword,securityQuestion,plainTextSecurityAnswer);
    }
}
