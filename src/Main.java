import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	
    	User user;
    	while ((user = User.welcomeScreenInterface()) != null) {
    	    User.homePage(user);
    	}
    }
}