/**
 * Command to mark a specific task as completed.
 */

public class MarkCommand extends Command {

    private final String in;

    public MarkCommand(String in) {
        this.in = in;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BingBongException {
        int taskIndex = Parser.parseIndex(in, 5);
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new BingBongException("That task slot does not exist in your list! :(");
        }
        tasks.get(taskIndex).mark();
        ui.print("BingBong marks this task as done:\n" + tasks.get(taskIndex).toString());
        storage.save(tasks);

    }
}
