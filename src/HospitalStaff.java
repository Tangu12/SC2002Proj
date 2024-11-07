import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalStaff {

    private List<Doctor> doctors;
    private List<Pharmacist> pharmacists;
    private List<Administrator> administrators;

    // Singleton instance
    private static HospitalStaff instance;

    // Private constructor
    private HospitalStaff() {
        this.doctors = new ArrayList<>();
        this.pharmacists = new ArrayList<>();
        this.administrators = new ArrayList<>();
    }

    // Method to retrieve the singleton instance
    public static HospitalStaff getInstance() {
        if (instance == null) {
            instance = new HospitalStaff();
        }
        return instance;
    }

    // Method to add a new staff member
    public void addStaffMember() {
        System.out.print("Enter name: ");
        String name = InputScanner.sc.nextLine();
        System.out.print("Enter hospital ID: ");
        String hospitalId = InputScanner.sc.nextLine();
        System.out.print("Enter role (DOCTOR, PHARMACIST, ADMINISTRATOR): ");
        String role = InputScanner.sc.nextLine();
        System.out.print("Enter gender (Male, Female): ");
        String gender = InputScanner.sc.nextLine();  
        System.out.println("Enter Age: ");
        int age = InputScanner.sc.nextInt();

        try {
            domain domainRole = domain.valueOf(role.toUpperCase());
            User newStaff;
            switch (domainRole) {
                case DOCTOR -> {
                    newStaff = new Doctor(name, hospitalId, domainRole, gender, age);
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
        System.out.print("Enter the hospital ID to update: ");
        String hospitalId = InputScanner.sc.nextLine();
        System.out.print("Enter new name: ");
        String newName = InputScanner.sc.nextLine();
        System.out.print("Enter new role (DOCTOR, PHARMACIST, ADMINISTRATOR): ");
        String newRole = InputScanner.sc.nextLine();

        Optional<? extends User> staffToUpdate = findStaffById(hospitalId);
        if (staffToUpdate.isPresent()) {
            User staff = staffToUpdate.get();
            staff.setName(newName);
            try {
                domain newDomain = domain.valueOf(newRole.toUpperCase());
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
        System.out.println("Doctors:");
        doctors.forEach(doc -> System.out.println(" - " + doc.getName()));
        System.out.println("Pharmacists:");
        pharmacists.forEach(pharm -> System.out.println(" - " + pharm.getName()));
        System.out.println("Administrators:");
        administrators.forEach(admin -> System.out.println(" - " + admin.getName()));
    }
}
