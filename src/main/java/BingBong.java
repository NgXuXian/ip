import java.util.Scanner;

public class BingBong {
    public static void main(String[] args) {
        String banner = " ___  _             ___                 \n"
                + "| _ )(_) _ _  ___  | _ ) ___  _ _  ___  \n"
                + "| _ \\| || ' \\/ _ \\ | _ \\/ _ \\| ' \\/ _ \\ \n"
                + "|___/|_||_||_\\_, | |___/\\___/|_||_\\_, | \n"
                + "             |__/                 |__/  \n";
        String horizontalLine = "____________________________________________________________";

        System.out.println(banner);
        System.out.println(horizontalLine);
        System.out.println("BingBong! What can I do for you?");
        System.out.println(horizontalLine);

        // Instantiate scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            // Exit when user inputs "bye"
            if (input.equals("bye")) {
                break;
            }

            // Echo user input
            System.out.println(horizontalLine);
            System.out.println(input);
            System.out.println(horizontalLine);
        }

        // "bye" message
        System.out.println(horizontalLine);
        System.out.println("Bye. BingBong hopes to see you again soon!");
        System.out.println(horizontalLine);

        scanner.close();
    }
}


