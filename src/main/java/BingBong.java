import java.io.File;
import java.time.LocalDate;

/**
 * Main class that controls the chatbot.
 * It connects the UI, storage, and task list together to run the program.
 */

public class BingBong {

    private final Ui ui;

    public BingBong() {
        this.ui = new Ui();
    }

    public void run() {
        ui.showWelcome();

        Storage storage = new Storage("." + File.separator + "data" + File.separator + "bingbong.txt");
        TaskList tasks = new TaskList(storage.load());

        while (true) {
            String input = ui.readCommand();
            Parser.CommandType type = Parser.getCommandType(input);

            // Exit when user inputs "bye"
            if (type == Parser.CommandType.BYE) {
                break;
            }

            ui.showHLine();

            try {
                if (type == Parser.CommandType.LIST) {
                    ui.print("BingBong shows your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.print((i + 1) + ". " + tasks.get(i).toString());
                    }

                } else if (type == Parser.CommandType.MARK) {
                    if (input.length() <= 5) {
                        throw new BingBongException("Please enter a valid number! :(");
                    }
                    int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new BingBongException("That task slot does not exist in your list! :(");
                    }
                    tasks.get(taskIndex).mark();
                    ui.print("BingBong marks this task as done:\n" + tasks.get(taskIndex).toString());
                    storage.save(tasks);

                } else if (type == Parser.CommandType.UNMARK) {
                    if (input.length() <= 7) {
                        throw new BingBongException("Please enter a valid number! :(");
                    }
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new BingBongException("That task slot does not exist in your list! :(");
                    }
                    tasks.get(taskIndex).unmark();
                    ui.print("BingBong marks this task as not done yet:\n" + tasks.get(taskIndex).toString());
                    storage.save(tasks);

                } else if (type == Parser.CommandType.TODO) {
                    if (input.length() <= 5 || input.substring(4).trim().isEmpty()) {
                        throw new BingBongException("The description of a todo cannot be blank. :(");
                    }
                    String desc = input.substring(5);
                    tasks.add(new Todo(desc));
                    ui.print("BingBong added this task to the list(" + tasks.size() + " task(s) total):\n" + tasks.get(tasks.size() - 1).toString());
                    storage.save(tasks);

                } else if (type == Parser.CommandType.DEADLINE) {
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
                        ui.print("BingBong added this task to the list(" + tasks.size() + " task(s) total):\n" + tasks.get(tasks.size() - 1).toString());
                        storage.save(tasks);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
                    }

                } else if (type == Parser.CommandType.EVENT) {
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
                        ui.print("BingBong added this task to the list(" + tasks.size() + " task(s) total):\n" + tasks.get(tasks.size() - 1).toString());
                        storage.save(tasks);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
                    }

                } else if (type == Parser.CommandType.DELETE) {
                    if (input.length() <= 7) {
                        throw new BingBongException("Please enter a valid number! :(");
                    }
                    int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new BingBongException("That task slot does not exist in your list! :(");
                    }
                    Task removedTask = tasks.remove(taskIndex);
                    ui.print("BingBong removed this task from the list(" + tasks.size() + " task(s) remaining):\n" + removedTask.toString());
                    storage.save(tasks);

                } else if (type == Parser.CommandType.DATES) {
                      if (input.length() <= 6 || input.substring(5).trim().isEmpty()) {
                          throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
                      }
                      try {
                          LocalDate queryDate = LocalDate.parse(input.substring(6).trim(), java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                          ui.print("BingBong search results for " +input.substring(6).trim() + ":");
                          boolean hasMatches = false;
                          for (int i = 0; i < tasks.size(); i++) {
                              Task task = tasks.get(i);
                              boolean isMatch = (task instanceof Deadline && ((Deadline)task).getDueDate().equals(queryDate)
                                      || (task instanceof Event && ((Event) task).isOccuringOn(queryDate)));
                              if (isMatch) {
                                  ui.print(" -" + task);
                                  hasMatches = true;
                              }
                          }
                          if (!hasMatches) {
                              ui.print("BingBong does not find any task on this date!");
                          }
                      } catch (java.time.format.DateTimeParseException e) {
                          throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
                      }
                } else {
                    throw new BingBongException("BingBong does not know what that means... :(");
                }
            } catch (BingBongException e) {
                ui.print(e.getMessage());
            } catch (NumberFormatException e) {
                ui.print("Please enter a valid number! :(");
            }
            ui.showHLine();
        }

        // "bye" message
        ui.showHLine();
        ui.showBye();
        ui.showHLine();
    }

    public static void main(String[] args) {
        new BingBong().run();
    }
}


