//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.SecureRandom;
//import java.util.Base64;
//
//public class Credentials {
//    final static String credentials = "credentials.txt";
//
//    public static String generateSalt() {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        return Base64.getEncoder().encodeToString(salt);
//    }
//
//    public static String hashPassword(String password, String salt) throws Exception {
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        md.update(salt.getBytes());  // Add the unique salt
//        byte[] hashedBytes = md.digest(password.getBytes());
//
//        return Base64.getEncoder().encodeToString(hashedBytes);
//    }
//
//    public static boolean verifyCredentials(String inputID, String inputPassword) {
//        boolean validPassword = false;
//        String line;
//
//        String workingDir = System.getProperty("user.dir");
//        String credentialsPath = workingDir + "/program_files/" + credentials;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
//            while ((line = br.readLine()) != null) {
//
//                if (line.contains(inputID)) {
//                    String[] credentials = line.split(",");
//                    String password = credentials[1].trim();
//                    String salt = credentials[2].trim();
//                    //String securityQuestion = credentials[4].trim();
//                    //String answer = credentials[5].trim();
//
//                    if (password.equals(hashPassword(inputPassword, salt))) {
//                        validPassword = true; // Authentication successful
//                        break;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("File Error !!");
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return validPassword;
//    }
//
//    public static boolean checkValidUser(String inputID) {
//        // Flag to check for valid user
//        boolean validUser = false;
//        String line;
//
//        String workingDir = System.getProperty("user.dir");
//        String credentialsPath = workingDir + "/program_files/" + credentials;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
//            while ((line = br.readLine()) != null) {
//                String[] credentials = line.split(",");
//                String username = credentials[0].trim();
//
//                if (username.equals(inputID)) {
//                    validUser = true; // Authentication successful
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return validUser;
//    }
//
//    public static boolean askSecurityQuestion(String inputID) {
//        String line;
//
//        String workingDir = System.getProperty("user.dir");
//        String credentialsPath = workingDir + "/program_files/" + credentials;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
//            while ((line = br.readLine()) != null) {
//
//                if (line.contains(inputID)) {
//                    String[] credentials = line.split(",");
//                    String sercurityQuestion = credentials[4].trim();
//                    System.out.println(sercurityQuestion);
//                    String answer  = InputScanner.sc.nextLine().trim();
//                    if (answer.equals(credentials[5].trim())) {
//                        return true;
//                    }
//                    else return false;
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("File Error !!");
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//            }
//        return false;
//    }
//}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Credentials {
    final static String credentials = "credentials.txt";

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes()); // Add the unique salt
        byte[] hashedBytes = md.digest(password.getBytes());

        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public static boolean verifyCredentials(String inputID, String inputPassword) {
        boolean validPassword = false;
        String line;

        String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/program_files/" + credentials;

        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
            while ((line = br.readLine()) != null) {

                if (line.contains(inputID)) {
                    String[] credentials = line.split(",");
                    String password = credentials[1].trim();
                    String salt = credentials[2].trim();
                    // String securityQuestion = credentials[4].trim();
                    // String answer = credentials[5].trim();

                    if (password.equals(hashPassword(inputPassword, salt))) {
                        validPassword = true; // Authentication successful
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File Error !!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return validPassword;
    }

    public static boolean checkValidUser(String inputID) {
        // Flag to check for valid user
        boolean validUser = false;
        String line;

        String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/program_files/" + credentials;

        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                String username = credentials[0].trim();

                if (username.equals(inputID)) {
                    validUser = true; // Authentication successful
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return validUser;
    }

    public static boolean askSecurityQuestion(String inputID) {
        String line;

        String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/program_files/" + credentials;

        try (BufferedReader br = new BufferedReader(new FileReader(credentialsPath))) {
            while ((line = br.readLine()) != null) {

                if (line.contains(inputID)) {
                    String[] credentials = line.split(",");
                    String securityQuestion = credentials[4].trim();
                    System.out.println(securityQuestion);
                    String answer = InputScanner.sc.nextLine().trim();
                    if (answer.equals(credentials[5].trim())) {
                        return true;
                    } else
                        return false;
                }
            }
        } catch (IOException e) {
            System.out.println("File Error !!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to determine number of digits needed
    private static String getIdFormat(int maxUsers) {
        int digits = String.valueOf(maxUsers).length();
        return "%s%0" + digits + "d";  // This creates format like "%s%04d" for 9999
    }

    public static String generateUsername(Domain userDomain) throws IOException {
        // Look through file and count number of instances based on user types
        int count = 0;
        String line;
        String workingDir = System.getProperty("user.dir");
        String credentialsPath = workingDir + "/program_files/credentials.txt";
        String hospitalID = null;

        BufferedReader br = new BufferedReader(new FileReader(credentialsPath));

        switch (userDomain) {
            case PATIENT:
                while ((line = br.readLine()) != null) {
                    String[] credentials = line.split(",");
                    String username = credentials[0].trim();
                    if (username.startsWith(User.PATIENT_PREFIX)) {
                        count++;
                    }
                }
                if (count < User.MAX_USERS) {
                    hospitalID = String.format(getIdFormat(User.MAX_USERS), User.PATIENT_PREFIX, count + 1);
                } else {
                    throw new RuntimeException("Maximum patient ID (P" + User.MAX_USERS + ") reached");
                }
                break;

            case PHARMACIST:
                while ((line = br.readLine()) != null) {
                    String[] credentials = line.split(",");
                    String username = credentials[0].trim();
                    if (username.startsWith(User.PHARMACIST_PREFIX)) {
                        count++;
                    }
                }
                if (count < User.MAX_USERS) {
                    hospitalID = String.format(getIdFormat(User.MAX_USERS), User.PHARMACIST_PREFIX, count + 1);
                } else {
                    throw new RuntimeException("Maximum pharmacist ID (PH" + User.MAX_USERS + ") reached");
                }
                break;

            case DOCTOR:
                while ((line = br.readLine()) != null) {
                    String[] credentials = line.split(",");
                    String username = credentials[0].trim();
                    if (username.startsWith(User.DOCTOR_PREFIX)) {
                        count++;
                    }
                }
                if (count < User.MAX_USERS) {
                    hospitalID = String.format(getIdFormat(User.MAX_USERS), User.DOCTOR_PREFIX, count + 1);
                } else {
                    throw new RuntimeException("Maximum doctor ID (D" + User.MAX_USERS + ") reached");
                }
                break;

            case ADMINISTRATOR:
                while ((line = br.readLine()) != null) {
                    String[] credentials = line.split(",");
                    String username = credentials[0].trim();
                    if (username.startsWith(User.ADMIN_PREFIX)) {
                        count++;
                    }
                }
                if (count < User.MAX_USERS) {
                    hospitalID = String.format(getIdFormat(User.MAX_USERS), User.ADMIN_PREFIX, count + 1);
                } else {
                    throw new RuntimeException("Maximum administrator ID (A" + User.MAX_USERS + ") reached");
                }
                break;
        }

        br.close();
        return hospitalID;
    }

    public static String formatName(String username) {
        String formattedName = null;



        return formattedName;
    }
}

