package Services;

import java.util.Scanner;

public class InputService {

    public static int inputInteger(){ // will this deal with the buffers?
        try {
            return new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer.");
            return inputInteger();
        }
    }

    public static String inputString(){
        return new Scanner(System.in).nextLine().trim();
    }


    public static void inputEnum(){}
}
