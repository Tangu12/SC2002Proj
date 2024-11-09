package Services;

public class PasswordService {
    public static boolean checkPassword(String hospitalID, String password){
        boolean successfulLogin = false;
        // UserFinderService to find user
        // Check if password column is correct,
        // if yes, flag = True
        // if no, flag = false
        return successfulLogin;
    }


    public static void changePassword(String inputID, String newPassword){
        // UserFinderService to find user
        // Change the column of the prev password to the new password

    }


    public static void hashPassword(){}



    public static boolean askSecurityQuestion(String inputID){
        int i = 0;
        boolean securitycheck = false;
        //Find User in credentials and ask the security qns
        // if wrong answer, increase counter, ask again until i = 3 (while loop)
        System.out.println("Wrong answer. Please try again: ");



        return securitycheck;
    }
}
