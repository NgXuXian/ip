/**
 * Command to unmark a specific task as incomplete.
 */

public class UnmarkCommand extends Command {

    private final String in;

    public UnmarkCommand(String in) {
        this.in = in;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BingBongException {
        int taskIndex = Parser.parseIndex(in, 7);
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new BingBongException("That task slot does not exist in your list! :(");
        }
        tasks.get(taskIndex).unmark();
        ui.print("BingBong marks this task as not done yet:\n" + tasks.get(taskIndex).toString());
        storage.save(tasks);

    }
}
