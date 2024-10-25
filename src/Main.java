import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);

    	User.displayLoginInterface(); // return domain of user?
        //User.homePage();
        User.changePassword("D001", "James123");
    }
}