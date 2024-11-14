package Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static <T extends Enum<T>> T inputEnum(Class<T> enumType) {
        System.out.println("Available options for " + enumType.getSimpleName() + ":");
        for (T constant : enumType.getEnumConstants()) {
            System.out.print(constant.name() + " ");
        }
        System.out.println();

        while (true) {
            try {
                System.out.print("Please enter a value for " + enumType.getSimpleName() + ": ");
                return Enum.valueOf(enumType, inputString().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid option from the list.");
            }
        }
    }

    public static LocalDate inputDate() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.println("Please enter a date in this format (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine().trim();

            try {
                return LocalDate.parse(dateInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the format yyyy-MM-dd.");
            }
        }
    }

    // Uses Email RegEx to check email input, and returns if email format is correct
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    // Method to input a valid email
    public static String inputEmail() {
        while (true) {
            System.out.println("Please enter your email: ");
            String email = inputString();

            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Please enter a valid email");
            }
        }
    }

}
