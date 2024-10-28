import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // User.changePassword("D001", "Johnny123");
    	Scanner sc = new Scanner(System.in);
    	User user;
    	user = User.displayLoginInterface();// return domain of user
        if (user == null) {
            return;
        }
        User.homePage(user);
        // User.changePassword("D001", "James123");
    }
}