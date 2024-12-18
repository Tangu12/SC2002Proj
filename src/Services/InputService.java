package Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code InputService} class which handles all the logic dealing with User inputs
 */
public class InputService {

    /**
     * Takes in the User input of an integer value and handles its exceptions
     * @return The integer value input by the user.
     */
    public static int inputInteger(){
        try {
            return new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer.");
            return inputInteger();
        }
    }

    /**
     * Takes in the User input of a String and handles its exceptions
     * @return The trimmed string input by the user.
     */
    public static String inputString(){
        return new Scanner(System.in).nextLine().trim();
    }

    /**
     * Takes in the User to input of a valid value for a specified enum type and displays all available enum constants,
     * and returns the corresponding enum constant entered
     * @param enumType The {@code Class} object of the enum type that the user should input a value for.
     * @return The enum constant corresponding to the user's input.
     * @param <T> The type of the enum.
     */
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

    /**
     * Takes in the User input of a date and handles its exceptions
     * @return The input date in the format of formatter
     */
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

    /**
     * Uses Email RegEx to check email input, and returns if email format is correct
     * @param email The email string to be validated.
     *      * @return {@code true} if the email format is valid, {@code false} otherwise.
     *      */
    public static boolean isValidEmail(String email) {
        String emailAndContactRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}/\\d{8}$";
        Pattern pattern = Pattern.compile(emailAndContactRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Method to input a valid email
     * @return The valid email input by the user.
     */
    public static String inputEmail() {
        while (true) {
            System.out.println("Please enter your email and contact in this format user@email.com/94662242: ");
            String email = inputString();

            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Please enter a valid email and contact according to the format.");
            }
        }
    }

}
