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
     * @param userID
     * @param name
     * @param age
     * @param gender
     * @param domain
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
