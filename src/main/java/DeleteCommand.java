/**
 * Command to remove a task from the list.
 */

public class DeleteCommand extends Command {

    private final String in;

    public DeleteCommand(String in) {
        this.in = in;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BingBongException {
        int taskIndex = Parser.parseIndex(in, 7);
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new BingBongException("That task slot does not exist in your list! :(");
        }
        Task removedTask = tasks.remove(taskIndex);
        ui.print(
                "BingBong removed this task from the list(" + tasks.size() + " task(s) remaining):\n"
                        + removedTask.toString());
        storage.save(tasks);

    }
}
