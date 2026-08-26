package bingbong.command;

import bingbong.exception.BingBongException;
import bingbong.storage.Storage;
import bingbong.task.Task;
import bingbong.task.TaskList;
import bingbong.ui.Ui;

/**
 * Command to search for tasks within the tracker using a text keyword. Filters the active task collection and displays
 * matching entries to the user.
 */
public class FindCommand extends Command {

    private final String in;

    /**
     * Constructs an operational finding command instance.
     *
     * @param in The command line user input.
     */
    public FindCommand(String in) {
        this.in = in;
    }

    /**
     * Loops through the active task collection, checks description boundaries, and prints matching results to the
     * terminal interface.
     *
     * @param tasks   The active TaskList tracking current items.
     * @param ui      The user interface formatting output blocks.
     * @param storage The storage file handler managing disk reads and writes.
     * @throws BingBongException If the search keyword is blank or missing.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BingBongException {
        if (in.trim().length() <= 4 || in.substring(4).trim().isEmpty()) {
            throw new BingBongException("Please specify a keyword to find! :(");
        }

        String keyword = in.substring(5).trim();
        int matchCount = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task.getDescription().contains(keyword)) {
                if (matchCount == 0) {
                    ui.print("BingBong shows the matching tasks in your list:");
                }
                matchCount++;
                ui.print(matchCount + ". " + task);
            }
        }

        if (matchCount == 0) {
            ui.print("BingBong found no matching tasks with that keyword!");
        }
    }
}
