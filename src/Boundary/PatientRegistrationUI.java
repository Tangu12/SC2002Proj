package Boundary;

public class PatientRegistrationUI {
    /*
}
    public static void registrationUI() {
        int choice = 0;
        String choice_confirmation = "";
        boolean confirm_choice = false;
//        Domain userDomain = null;
//        User newUser = null;

        // Choose Domain
        do {
            System.out.println("Please Enter Your Domain : ");
            System.out.println("(1) Patient");
            System.out.println("(2) Doctor");
            System.out.println("(3) Pharmacist");
            System.out.println("(4) Administrator");



        // Generate new username
        String newUsername = null;
        try {
            newUsername = Credentials.generateUsername(userDomain);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Your assigned Hospital ID will be: " + newUsername);

        // Get user details
        System.out.println("Please enter your full name: ");
        InputScanner.sc.nextLine(); // Clear buffer
        String fullName = InputScanner.sc.nextLine().trim();

        // Get password
        System.out.println("Please enter your password: ");
        String password = InputScanner.sc.nextLine().trim();

        // Get security question and answer

        String securityQuestion = null;

        do {
            System.out.println("Please select a security question: ");

            System.out.println("(1) What is your Favourite Colour?");
            System.out.println("(2) What is your Favourite Food?");
            System.out.println("(3) What is your Favourite Subject?");
            System.out.println("(4) What is your Favourite Show?");

            try {
                choice = InputScanner.sc.nextInt();
            }

            catch (InputMismatchException e) {
                InputScanner.sc.nextLine();
                choice = 0;
            }

            switch (choice) {
                case 1:
                    securityQuestion = "What is your Favourite Colour?";
                    break;
                case 2:
                    securityQuestion = "What is your Favourite Food?";
                    break;
                case 3:
                    securityQuestion = "What is your Favourite Subject?";
                    break;
                case 4:
                    securityQuestion = "What is your Favourite Show?";
                    break;
                default:
                    System.out.println("Please enter a valid choice");
                    continue;
            }
        } while (choice == 0);

        System.out.println("Please enter the answer to your security question: ");
        InputScanner.sc.nextLine();
        String securityAnswer = InputScanner.sc.nextLine().trim();

        // Create appropriate user type based on domain
        switch (userDomain) {
            case PATIENT:
                newUser = new Patient(fullName, newUsername, userDomain, "", 0);
                break;
            case DOCTOR:
                newUser = new Doctor(fullName, newUsername, userDomain, "", 0);
                break;
            case PHARMACIST:
                newUser = new Pharmacist(fullName, newUsername, userDomain,"", 0);
                break;
            case ADMINISTRATOR:
                newUser = new Administrator(fullName, newUsername, userDomain,"", 0);
                break;
        }

        // Generate salt and hash password
        try {
            String workingDir = System.getProperty("user.dir");
            String credentialsPath = workingDir + "/program_files/" + Credentials.credentials;

            String salt = Credentials.generateSalt();
            String hashedPassword = Credentials.hashPassword(password, salt);

            BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsPath, true));

            writer.write(newUsername + "," + hashedPassword + "," + salt + "," + password + "," + securityQuestion + ","
                    + securityAnswer);
            writer.newLine();
            writer.close();

            System.out.println("Account created successfully!");
            return newUser;
        } catch (Exception e) {
            System.out.println("Error creating account");
            return null;
        }

    }
    */
}
