import java.io.File;

import bingbong.command.Command;
import bingbong.exception.BingBongException;
import bingbong.parser.Parser;
import bingbong.storage.Storage;
import bingbong.task.TaskList;
import bingbong.ui.Ui;

/**
 * Main class that controls the chatbot. It connects the UI, storage, and task list together to run the program.
 */

public class BingBong {

    private final Ui ui;

    public BingBong() {
        ui = new Ui();
    }

    public static void main(String[] args) {
        new BingBong().run();
    }

    public void run() {
        ui.showWelcome();

        Storage storage = new Storage("." + File.separator + "data" + File.separator + "bingbong.txt");
        TaskList tasks = new TaskList(storage.load());
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            try {
                Command command = Parser.parse(input);
                ui.showHLine();
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (BingBongException e) {
                ui.showHLine();
                ui.print(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showHLine();
                ui.print("Please enter a valid number! :(");
            }
            ui.showHLine();
        }
    }
}




