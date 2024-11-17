package Boundary;

import Services.InputService;

/**
 * {@code LogoutUI} class which displays the UI when the user wants to log out
 */
public class LogoutUI {
    /**
     * Displays the UI when the user wants to log out, until the User selects a valid option, Y or N
     * @return True if the User confirms that they want to log out and False if they choose not to log out
     */
    public boolean logoutUI() {
        String input = null;
        do {
            System.out.println("Are you sure you want to quit? (Y/N)");
            input = InputService.inputString();
            if(!input.equals("Y") && !input.equals("N")){
                System.out.println("Please enter a valid option (Y/N)");
            }
        }
        while(!input.equals("Y") && !input.equals("N"));

        if(input.equals("Y")) {
            return true;
        }
        return false;
    }
}
