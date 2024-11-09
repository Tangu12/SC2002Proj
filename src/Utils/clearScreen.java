package Utils;
import java.io.IOException;

public class clearScreen {

    private static void clearWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clearUnix() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            clearWindows();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            clearUnix();
        } else {
            System.out.println("Unsupported operating system: " + os);
        }
    }
}
