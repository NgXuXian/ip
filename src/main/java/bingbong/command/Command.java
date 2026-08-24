package bingbong.command;

import bingbong.exception.BingBongException;
import bingbong.storage.Storage;
import bingbong.task.TaskList;
import bingbong.ui.Ui;

/**
 * Represents an executable command in the chatbot application. All specific commands must extend this class and
 * implement execute.
 */

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BingBongException;

    public boolean isExit() {
        return false;
    }
}
