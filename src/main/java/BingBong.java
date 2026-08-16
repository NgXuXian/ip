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

        Task[] tasks = new Task[100];
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
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                }
            } else if (input.startsWith("mark")) {
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                tasks[taskIndex].mark();
                System.out.println("BingBong marks this task as done:");
                System.out.println(tasks[taskIndex].toString());
            } else if (input.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                tasks[taskIndex].unmark();
                System.out.println("BingBong marks this task as not done yet:");
                System.out.println(tasks[taskIndex].toString());
            } else if (input.startsWith("todo")) {
                String desc = input.substring(5);
                tasks[taskCount] = new Todo(desc);
                taskCount++;
                System.out.println("BingBong added this task to the list(" + taskCount + " task(s) total): ");
                System.out.println(tasks[taskCount - 1].toString());
            } else if (input.startsWith("deadline")) {
                String content = input.substring(9);
                int byIndex = content.indexOf("/by");
                String desc = content.substring(0, byIndex).trim();
                String by = content.substring(byIndex + 4).trim();
                tasks[taskCount] = new Deadline(desc, by);
                taskCount++;
                System.out.println("BingBong added this task to the list(" + taskCount + " task(s) total): ");
                System.out.println(tasks[taskCount - 1].toString());
            } else if (input.startsWith("event")) {
                String content = input.substring(6);
                int fromIndex = content.indexOf("/from");
                int toIndex = content.indexOf("/to");
                String desc = content.substring(0, fromIndex).trim();
                String from = content.substring(fromIndex + 6, toIndex).trim();
                String to = content.substring(toIndex + 4).trim();
                tasks[taskCount] = new Event(desc, from, to);
                taskCount++;
                System.out.println("BingBong added this task to the list(" + taskCount + " task(s) total): ");
                System.out.println(tasks[taskCount - 1].toString());
            } else {
                tasks[taskCount] = new Todo(input);
                taskCount++;
                System.out.println("BingBong added this task to the list(" + taskCount + " task(s) total): ");
                System.out.println(tasks[taskCount - 1].toString());
            }
            System.out.println(horizontalLine);
        }

        // "bye" message
        System.out.println(horizontalLine);
        System.out.println("Bye. BingBong misses you!");
        System.out.println(horizontalLine);

        scanner.close();
    }
}


