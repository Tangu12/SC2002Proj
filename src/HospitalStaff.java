import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalStaff {

    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    private static List<Administrator> administrators = new ArrayList<>();
    private static final String FILE_NAME = "program_files/HospitalStaff.csv";

    // Method to add a new staff member
    public void addStaffMember() {
        System.out.print("Enter name: ");
        String name = InputScanner.sc.nextLine();
        System.out.print("Enter hospital ID: ");
        String hospitalId = InputScanner.sc.nextLine();
        Optional<? extends User> staffToadd = findStaffById(hospitalId);
        if(staffToadd.isPresent()) {
        	System.out.println("ID already exists!!!");
        	return;
        }
        System.out.print("Enter role: \n(1). Doctor\n(2). Pharmacist\n(3). Administrator\n");
        int choice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        String role;
        switch(choice) {
	        case 1:
	        	role = "Doctor";
	        	break;
	        case 2:
	        	role = "PHARMACIST";
	        	break;
	        case 3:
	        	role = "ADMINISTRATOR";
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
        }
        System.out.print("Enter gender:\n(1). Male\n(2). Female\n");
        int choice1 = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        String gender;
        switch(choice1) {
	        case 1:
	        	gender = "Male";
	        	break;
	        case 2:
	        	gender = "Female";
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
	    }
        System.out.println("Enter Age: ");
        int age = InputScanner.sc.nextInt();

        try {
            Domain domainRole = Domain.valueOf(role.toUpperCase());
            User newStaff;
            switch (domainRole) {
                case DOCTOR -> {
                    newStaff = new Doctor(name, hospitalId, domainRole, gender, age);
                    System.out.println("Select the departmen:\n"
                            + "(1) Cardiology\n"
                            + "(2) Neurology\n"
                            + "(3) Oncology\n"
                            + "(4) Dermatology\n"
                            + "(5) Endocrinology\n"
                            + "(6) Gastroenterology\n"
                            + "(7) Nephrology\n"
                            + "(8) Pulmonology\n"
                            + "(9) Rheumatology\n"
                            + "(10) ObstetricsGynecology\n"
                            + "(11) Others\n");
                    int choiceDep = InputScanner.sc.nextInt(); // Exception handling
                    InputScanner.sc.nextLine(); // Buffer
                    String dept;
                    if (choiceDep == 1) dept = department.Cardiology.toString();
                    else if (choiceDep == 2) dept = department.Neurology.toString();
                    else if (choiceDep == 3) dept = department.Oncology.toString();
                    else if (choiceDep == 4) dept = department.Dermatology.toString();
                    else if (choiceDep == 5) dept = department.Endocrinology.toString();
                    else if (choiceDep == 6) dept = department.Gastroenterology.toString();
                    else if (choiceDep == 7) dept = department.Nephrology.toString();
                    else if (choiceDep == 8) dept = department.Pulmonology.toString();
                    else if (choiceDep == 9) dept = department.Rheumatology.toString();
                    else if (choiceDep == 10) dept = department.ObstetricsGynecology.toString();
                    else dept = department.Others.toString();
                    ((Doctor) newStaff).setDepartment(dept);
                    doctors.add((Doctor) newStaff);
                    System.out.println("Doctor added: " + newStaff.getName());
                }
                case PHARMACIST -> {
                    newStaff = new Pharmacist(name, hospitalId, domainRole, gender, age);
                    pharmacists.add((Pharmacist) newStaff);
                    System.out.println("Pharmacist added: " + newStaff.getName());
                }
                case ADMINISTRATOR -> {
                    newStaff = new Administrator(name, hospitalId, domainRole, gender, age);
                    administrators.add((Administrator) newStaff);
                    System.out.println("Administrator added: " + newStaff.getName());
                }
                default -> throw new IllegalArgumentException("Invalid role");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role. Please enter DOCTOR, PHARMACIST, or ADMINISTRATOR.");
        }
    }

    // Method to remove a staff member by hospital ID
    public void removeStaffMember() {
    	displayAllStaff();
        System.out.print("Enter the hospital ID to remove: ");
        String hospitalId = InputScanner.sc.nextLine();
        Optional<? extends User> staffToRemove = findStaffById(hospitalId);

        if (staffToRemove.isPresent()) {
            User staff = staffToRemove.get();
            if (staff instanceof Doctor doctor) {
                doctors.remove(doctor);
            } else if (staff instanceof Pharmacist pharmacist) {
                pharmacists.remove(pharmacist);
            } else if (staff instanceof Administrator admin) {
                administrators.remove(admin);
            }
            System.out.println("Removed staff member: " + staff.getName());
        } else {
            System.out.println("Staff member with ID " + hospitalId + " not found.");
        }
    }

    // Method to update a staff member's information
    public void updateStaffMember() {
    	displayAllStaff();
        System.out.print("Enter the hospital ID to update: ");
        String hospitalId = InputScanner.sc.nextLine();
        System.out.print("Enter new name: ");
        String newName = InputScanner.sc.nextLine();
        System.out.print("Enter role: \n(1). Doctor\n(2). Pharmacist\n(3). Administrator\n");
        int choice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        String newRole;
        switch(choice) {
	        case 1:
	        	newRole = "DOCTOR";
	        	break;
	        case 2:
	        	newRole = "PHARMACIST";
	        	break;
	        case 3:
	        	newRole = "ADMINISTRATOR";
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
        }
        System.out.print("Enter gender:\n(1). Male\n(2). Female\n");
        int choice1 = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        String gender;
        switch(choice1) {
	        case 1:
	        	gender = "Male";
	        	break;
	        case 2:
	        	gender = "Female";
	        	break;
	        default:
	        	System.out.println("Please only select available options!!");
	        	return;
	    }
        System.out.println("Enter Age: ");
        int age = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();
        String dept = "";
        if(newRole.equals("DOCTOR")) {
        	System.out.println("Select the departmen:\n"
                    + "(1) Cardiology\n"
                    + "(2) Neurology\n"
                    + "(3) Oncology\n"
                    + "(4) Dermatology\n"
                    + "(5) Endocrinology\n"
                    + "(6) Gastroenterology\n"
                    + "(7) Nephrology\n"
                    + "(8) Pulmonology\n"
                    + "(9) Rheumatology\n"
                    + "(10) ObstetricsGynecology\n"
                    + "(11) Others\n");
            int choiceDep = InputScanner.sc.nextInt(); // Exception handling
            InputScanner.sc.nextLine(); // Buffer
            if (choiceDep == 1) dept = department.Cardiology.toString();
            else if (choiceDep == 2) dept = department.Neurology.toString();
            else if (choiceDep == 3) dept = department.Oncology.toString();
            else if (choiceDep == 4) dept = department.Dermatology.toString();
            else if (choiceDep == 5) dept = department.Endocrinology.toString();
            else if (choiceDep == 6) dept = department.Gastroenterology.toString();
            else if (choiceDep == 7) dept = department.Nephrology.toString();
            else if (choiceDep == 8) dept = department.Pulmonology.toString();
            else if (choiceDep == 9) dept = department.Rheumatology.toString();
            else if (choiceDep == 10) dept = department.ObstetricsGynecology.toString();
            else dept = department.Others.toString();
        }
        Optional<? extends User> staffToUpdate = findStaffById(hospitalId);
        if (staffToUpdate.isPresent()) {
            User staff = staffToUpdate.get();
            staff.setName(newName);
            staff.setAge(age);
            staff.setGender(gender);
            if(newRole.equals("DOCTOR")) ((Doctor) staff).setDepartment(dept);
            try {
                Domain newDomain = Domain.valueOf(newRole.toUpperCase());
                staff.setDomain(newDomain);
                System.out.println("Updated staff member: " + staff.getName() + " to role " + newDomain);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role. Please enter DOCTOR, PHARMACIST, or ADMINISTRATOR.");
            }
        } else {
            System.out.println("Staff member with ID " + hospitalId + " not found.");
        }
    }

    // Find a staff member by ID
    private Optional<User> findStaffById(String hospitalId) {
        return doctors.stream().filter(d -> d.getHospitalId().equals(hospitalId)).map(d -> (User) d).findFirst()
                .or(() -> pharmacists.stream().filter(p -> p.getHospitalId().equals(hospitalId)).map(p -> (User) p).findFirst())
                .or(() -> administrators.stream().filter(a -> a.getHospitalId().equals(hospitalId)).map(a -> (User) a).findFirst());
    }

    // Display all staff members
    public void displayAllStaff() {
        System.out.println("All Doctors at the hospital:");
        System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(20));

		System.out.println("|" + formatCell("ID", 10)
				+ "|" + formatCell("Doctor Name", 20)
				+ "|" + formatCell("Gender", 10)
				+ "|" + formatCell("Age", 10) 
				+ "|" + formatCell("Department", 20) + "|");

		System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(20) + "+");
		int i = 1;
		for(Doctor doc : doctors) {
			System.out.println("|" + formatCell(doc.getHospitalId(), 10)
					+ "|" + formatCell(doc.getName(), 20)
					+ "|" + formatCell(doc.getGender(), 10)
					+ "|" + formatCell(String.valueOf(doc.getAge()), 10) 
					+ "|" + formatCell(doc.getDepartment().toString(), 20) + "|");
			System.out.println("+" + "-".repeat(10) + "+"
					+ "-".repeat(20) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(20) + "+");
			i++;
		}

        System.out.println("All Pharmacists at the hospital:");
        System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10));

		System.out.println("|" + formatCell("ID", 10)
				+ "|" + formatCell("Pharmacist Name", 20)
				+ "|" + formatCell("Gender", 10)
				+ "|" + formatCell("Age", 10) + "|");

		System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+");
		int j = 1;
		for(Pharmacist ph : pharmacists) {
			System.out.println("|" + formatCell(ph.getHospitalId(), 10)
					+ "|" + formatCell(ph.getName(), 20)
					+ "|" + formatCell(ph.getGender(), 10)
					+ "|" + formatCell(String.valueOf(ph.getAge()), 10) + "|");
			System.out.println("+" + "-".repeat(10) + "+"
					+ "-".repeat(20) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(10) + "+");
			j++;
		}

        System.out.println("All Administrators at the hospital:");
        System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10));

		System.out.println("|" + formatCell("ID", 10)
				+ "|" + formatCell("Administartor Name", 20)
				+ "|" + formatCell("Gender", 10)
				+ "|" + formatCell("Age", 10) + "|");

		System.out.println("+" + "-".repeat(10) + "+"
				+ "-".repeat(20) + "+"
				+ "-".repeat(10) + "+"
				+ "-".repeat(10) + "+");
		int k = 1;
		for(Administrator adm : administrators) {
			System.out.println("|" + formatCell(adm.getHospitalId(), 10)
					+ "|" + formatCell(adm.getName(), 20)
					+ "|" + formatCell(adm.getGender(), 10)
					+ "|" + formatCell(String.valueOf(adm.getAge()), 10) + "|");
			System.out.println("+" + "-".repeat(10) + "+"
					+ "-".repeat(20) + "+"
					+ "-".repeat(10) + "+"
					+ "-".repeat(10) + "+");
			k++;
		}
    }
    
    public static void loadDoctorList() {
    	List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else if(row[2].equals("Doctor"))
			{
				Doctor doctor = new Doctor(row[1], row[0], Domain.DOCTOR, row[3], Integer.valueOf(row[4]));
				doctor.setDepartment(row[5]);
				doctors.add(doctor);
			}
		}
    }
    
    public static void loadPharmacistList() {
    	List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else if(row[2].equals("Pharmacist"))
			{
				Pharmacist pharmacist = new Pharmacist(row[1], row[0], Domain.PHARMACIST, row[3], Integer.valueOf(row[4]));
				pharmacists.add(pharmacist);
			}
		}
    }
    
    public static void loadAdministrator() {
    	List<String[]> data = new ArrayList<>();

		//Read the CSV file
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String row;
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("File is not created yet!!");
		}
		boolean headerRow = true;
		for(String[] row : data) {
			if(headerRow) headerRow = false;
			else if(row[2].equals("Administrator"))
			{
				Administrator administrator = new Administrator(row[1], row[0], Domain.ADMINISTRATOR, row[3], Integer.valueOf(row[4]));
				administrators.add(administrator);
			}
		}
    }
    
    public static List<Doctor> getDoctorList(){
    	return HospitalStaff.doctors;
    }
    
    public static List<Pharmacist> getPharmacistList(){
    	return HospitalStaff.pharmacists;
    }
    
    public static List<Administrator> getAdministratorList(){
    	return HospitalStaff.administrators;
    }
    
    public static void updateHospitalStaffFile(List<Administrator> admList, List<Doctor> docList, List<Pharmacist> phList) {
        List<String[]> data = new ArrayList<>();
        
        String[] values = new String[6];
        values[0] = "Staff ID";
        values[1] = "Name";
        values[2] = "Role";
        values[3] = "Gender";
        values[4] = "Age";
        values[5] = "Department";
        data.add(values);
        
        for(Administrator adm : admList) {
        	String[] row = new String[6];
        	row[0] = adm.getHospitalId();
            row[1] = adm.getName();
            row[2] = "Administrator";
            row[3] = adm.getGender();
            row[4] = String.valueOf(adm.getAge());
            row[5] = "NotApplicable";
            data.add(row);
        }
        
        for(Doctor doc : docList) {
        	String[] row = new String[6];
        	row[0] = doc.getHospitalId();
            row[1] = doc.getName();
            row[2] = "Doctor";
            row[3] = doc.getGender();
            row[4] = String.valueOf(doc.getAge());
            row[5] = doc.getDepartment().toString();
            data.add(row);
        }
        
        for(Pharmacist ph : phList) {
        	String[] row = new String[6];
        	row[0] = ph.getHospitalId();
            row[1] = ph.getName();
            row[2] = "Pharmacist";
            row[3] = ph.getGender();
            row[4] = String.valueOf(ph.getAge());
            row[5] = "NotApplicable";
            data.add(row);
        }
        updateFile(data);
    }
    
    private static void updateFile(List<String[]> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
			for(String[] row : data) writer.write(String.join(",", row) + "\n");
			writer.close();
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
    
    private static String formatCell(String value, int width) {
		if (value == " ") {
			value = "";
		}
		return String.format("%-" + width + "s", value);  // Left-align the text within the specified width
    }
}
