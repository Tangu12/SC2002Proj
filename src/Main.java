import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	User user;
    	user = User.displayLoginInterface(); // return domain of user
    	
        User.homePage(user);
        //User.changePassword("D001", "James123");
    }
}