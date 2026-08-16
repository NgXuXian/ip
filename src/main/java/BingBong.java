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

        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
        int taskCount = 0;


        while (true) {
            String input = scanner.nextLine();

            // Exit when user inputs "bye"
            if (input.equals("bye")) {
                break;
            }

            // Stores and displays user's text inputs
            System.out.println(horizontalLine);
            if (input.equals("list")) {
                System.out.println("BingBong shows your list:");
                for (int i = 0; i < taskCount; i++) {
                    String markStatus = isDone[i] ? "X" : " ";
                    System.out.println((i + 1) + ". [" + markStatus + "] " + tasks[i]);
                }
            } else if (input.startsWith("mark")) {
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                isDone[taskIndex] = true;
                System.out.println("BingBong marks this task as done:");
                System.out.println("[X] " + tasks[taskIndex]);
            }
            else {
                tasks[taskCount] = input;
                isDone[taskCount] = false;
                taskCount++;
                System.out.println("added: " + input);
            }
            System.out.println(horizontalLine);
        }

        // "bye" message
        System.out.println(horizontalLine);
        System.out.println("Bye. BingBong hopes to see you again soon!");
        System.out.println(horizontalLine);

        scanner.close();
    }
}


