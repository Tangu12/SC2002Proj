package Entity.User;

import Entity.Enums.Department;
import Entity.Enums.Domain;
import Entity.Enums.Gender;

import java.util.ArrayList;

/**
 * {@code Pharmacist} class which inherits from {@code HospitalStaff}, with the {@code Patient}'s medical History
 */
public class Pharmacist extends HospitalStaff{

    // Add more attributes as needed
    //ArrayList<MedicalRecord> medicalHistory;
    private static ArrayList<Pharmacist> pharmacistList = new ArrayList<>();

    /**
     * Constructor for {@code Pharmacist}
     * @param userID The unique identifier for the {@code Pharmacist}. This is typically an ID assigned to the pharmacist in the hospital system (e.g., "PH12345").
     * @param name The name of the {@code Pharmacist}. This is the full name of the pharmacist (e.g., "Alice Smith").
     * @param age The age of the {@code Pharmacist}. This is the pharmacist's age in years (e.g., 45).
     * @param gender The gender of the {@code Pharmacist}. This is a value from the {@code Gender} enum, such as `MALE`, `FEMALE`, `OTHER`.
     * @param domain The domain/role of the {@code Pharmacist}. This is always set to `PHARMACIST` for this class.
     */
    public Pharmacist(String userID, String name, int age, Gender gender, Domain domain) {
        super(userID, name, age, gender, Domain.PHARMACIST);
    }

    /**
     *
     * @return The {@code Pharmacist} list of a {@code Pharmacist}
     */
    public static ArrayList<Pharmacist> getPharmacistList() {
    	return Pharmacist.pharmacistList;
    }

	@Override
	public void updateDepartment(Domain newRole, Department dept) {
		//null code
	}
}
