package Boundary.UserUI;

import Application.ApplicationContext;
import Controllers.AdministratorController;
import Controllers.DoctorController;
import Controllers.PatientController;
import Controllers.PharmacistController;
import Entity.User.*;


/**
 * {@code UserMainPageFactory} class has the responsibility to return the domain of the User that is logging in
 */
public class UserMainPageFactory {

    /**
     Creates a new instance of the {@code MainPage} of the domain of the User who is logging in
     * @param user
     * @param applicationContext
     * @return
     */
    public static UserMainPage getHomePage(IUser user, ApplicationContext applicationContext) {
        switch (user.getDomain()) {
            case PATIENT:
                return new PatientMainPage(new PatientController((Patient) user, applicationContext.getAppointmentService(), applicationContext.getPatientAccountService()));
            case PHARMACIST:
                return new PharmacistMainPage(new PharmacistController(applicationContext.getMedicalInventoryService(), applicationContext.getAppointmentService(), (Pharmacist) user));
            case DOCTOR:
                return new DoctorMainPage(new DoctorController((Doctor) user, applicationContext.getAppointmentService()));
            case ADMINISTRATOR:
                return new AdministratorMainPage(new AdministratorController((Administrator) user, applicationContext.getStaffManagementService(), applicationContext.getMedicalInventoryService(), applicationContext.getAccountManager()), applicationContext.getHospitalStaffRegistrationService());
            default:
                throw new IllegalArgumentException("Not Available Domain!");
        }
    }
}
