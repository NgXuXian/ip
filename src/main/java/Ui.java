import java.util.Scanner;

/**
 * Handles reading inputs from the user and printing text messages on the screen. It is responsible for everything the
 * user sees.
 */

public class Ui {

    private static final String H_LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        String banner = " ___  _             ___                 \n"
                + "| _ )(_) _ _  ___  | _ ) ___  _ _  ___  \n"
                + "| _ \\| || ' \\/ _ \\ | _ \\/ _ \\| ' \\/ _ \\ \n"
                + "|___/|_||_||_\\_, | |___/\\___/|_||_\\_, | \n"
                + "             |__/                 |__/  \n";
        System.out.println(banner + H_LINE + "\nBingBong! What can I do for you?\n" + H_LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showHLine() {
        System.out.println(H_LINE);
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("BingBong cannot load your previous list: ");
    }

    public void showBye() {
        System.out.println("Bye. BingBong misses you!");
    }

}
