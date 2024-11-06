import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	
    	User user =null;
    	do {
    		user = User.welcomeScreenInterface();
    		User.homePage(user);
    	}
        while(user!=null);
    }
}