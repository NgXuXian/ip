import java.time.format.DateTimeParseException;

/**
 * Command to handle creating and adding Todo, Deadline, or Event tasks to the list.
 */

public class AddCommand extends Command {

    private final String in;
    private final Parser.CommandType taskType;

    public AddCommand(String in, Parser.CommandType taskType) {
        this.in = in;
        this.taskType = taskType;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BingBongException {
        Task taskToAdd;
        try {
            switch (taskType) {
                case TODO:
                    if (in.length() <= 5 || in.substring(4).trim().isEmpty()) {
                        throw new BingBongException("The description of a todo cannot be blank. :(");
                    }
                    taskToAdd = new Todo(in.substring(5).trim());
                    break;

                case DEADLINE:
                    String[] deadlineArgs = Parser.parseDeadline(in);
                    taskToAdd = new Deadline(deadlineArgs[0], deadlineArgs[1]);
                    break;

                case EVENT:
                    String[] eventArgs = Parser.parseEvent(in);
                    taskToAdd = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
                    break;

                default:
                    throw new BingBongException("BingBong does not know what that means... :(");
            }
            tasks.add(taskToAdd);
            ui.print("BingBong added this task to the list(" + tasks.size() + " task(s) total):\n" + taskToAdd);
            storage.save(tasks);
        } catch (DateTimeParseException e) {
            throw new BingBongException("BingBong only recognises 'DD/MM/YYYY'!");
        }
    }
}
