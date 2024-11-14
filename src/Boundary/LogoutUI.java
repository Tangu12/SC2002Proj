package Boundary;

import Services.InputService;

public class LogoutUI {
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
