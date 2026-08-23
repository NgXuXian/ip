import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class BingBong {

    private enum CommandType {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, UNKNOWN
    }

    public static CommandType getCommandType(String in) {
        String commandWord = in.split(" ", 2)[0];
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

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
        Storage storage = new Storage("." + File.separator + "data" + File.separator + "bingbong.txt");
        ArrayList<Task> tasks = storage.load();

        while (true) {
            String input = scanner.nextLine();
            CommandType type = getCommandType(input);

            // Exit when user inputs "bye"
            if (type == CommandType.BYE) {
                break;
            }

            // Stores and displays user's text inputs
            System.out.println(horizontalLine);

            try {
                if (type == CommandType.LIST) {
                    System.out.println("BingBong shows your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i).toString());
                    }

                } else if (type == CommandType.MARK) {
                    if (input.length() <= 5) {
                        throw new BingBongException("Please enter a valid number! :(");
                    }
                    int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new BingBongException("That task slot does not exist in your list! :(");
                    }
                    tasks.get(taskIndex).mark();
                    System.out.println("BingBong marks this task as done:");
                    System.out.println(tasks.get(taskIndex).toString());

                    storage.save(tasks);

                } else if (type == CommandType.UNMARK) {
                    if (input.length() <= 7) {
                        throw new BingBongException("Please enter a valid number! :(");
                    }
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new BingBongException("That task slot does not exist in your list! :(");
                    }
                    tasks.get(taskIndex).unmark();
                    System.out.println("BingBong marks this task as not done yet:");
                    System.out.println(tasks.get(taskIndex).toString());

                    storage.save(tasks);

                } else if (type == CommandType.TODO) {
                    if (input.length() <= 5 || input.substring(4).trim().isEmpty()) {
                        throw new BingBongException("The description of a todo cannot be blank. :(");
                    }
                    String desc = input.substring(5);
                    tasks.add(new Todo(desc));
                    System.out.println("BingBong added this task to the list(" + tasks.size() + " task(s) total): ");
                    System.out.println(tasks.get(tasks.size() - 1).toString());

                    storage.save(tasks);

                } else if (type == CommandType.DEADLINE) {
                    if (input.length() <= 9 || input.substring(8).trim().isEmpty()) {
                        throw new BingBongException("The description of a deadline cannot be blank. :(");
                    }
                    String content = input.substring(9);
                    int byIndex = content.indexOf(" /by ");
                    if (byIndex == -1) {
                        throw new BingBongException("A deadline must include a target timing using '/by'.");
                    }
                    String desc = content.substring(0, byIndex).trim();
                    String by = content.substring(byIndex + 4).trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        throw new BingBongException("Missing fields. BingBong needs the deadline description and target time of the deadline. :(");
                    }

                    try {
                        tasks.add(new Deadline(desc, by));
                        System.out.println("BingBong added this task to the list(" + tasks.size() + " task(s) total): ");
                        System.out.println(tasks.get(tasks.size() - 1).toString());
                        storage.save(tasks);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
                    }

                } else if (type == CommandType.EVENT) {
                    if (input.length() <= 6 || input.substring(5).trim().isEmpty()) {
                        throw new BingBongException("The description of an event cannot be blank. :(");
                    }
                    String content = input.substring(6);
                    int fromIndex = content.indexOf(" /from ");
                    int toIndex = content.indexOf(" /to ");
                    if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                        throw new BingBongException("An event requires valid time constraints using '/from' and '/to'.");
                    }
                    String desc = content.substring(0, fromIndex).trim();
                    String from = content.substring(fromIndex + 6, toIndex).trim();
                    String to = content.substring(toIndex + 4).trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new BingBongException("Missing fields. BingBong needs the event description, start and end parameters.");
                    }

                    try {
                        tasks.add(new Event(desc, from, to));
                        System.out.println("BingBong added this task to the list(" + tasks.size() + " task(s) total): ");
                        System.out.println(tasks.get(tasks.size() - 1).toString());
                        storage.save(tasks);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
                    }

                } else if (type == CommandType.DELETE) {
                    if (input.length() <= 7) {
                        throw new BingBongException("Please enter a valid number! :(");
                    }
                    int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new BingBongException("That task slot does not exist in your list! :(");
                    }
                    Task removedTask = tasks.remove(taskIndex);
                    System.out.println("BingBong removed this task from the list(" + tasks.size() + " task(s) remaining): ");
                    System.out.println(removedTask.toString());

                    storage.save(tasks);

                } else {
                    throw new BingBongException("BingBong does not know what that means... :(");
                }
            } catch (BingBongException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number! :(");
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


