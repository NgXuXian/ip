package bingbong.command;

import bingbong.storage.Storage;
import bingbong.task.TaskList;
import bingbong.ui.Ui;

/**
 * bingbong.command.Command to handle terminating the chatbot application.
 */

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
