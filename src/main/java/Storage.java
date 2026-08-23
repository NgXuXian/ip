import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public void save(ArrayList<Task> tasks) {
        try {
            // Create data or folder if missing
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // Overwrite file with current tasks
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).formatToFile() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) {
            return tasks;
        }
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String[] parts = s.nextLine().split(" \\| ");
                Task task = null;
                if (parts[0].equals("T")) {
                    task = new Todo(parts[2]);
                } else if (parts[0].equals("D")) {
                    task = new Deadline(parts[2], parts[3]);
                } else if (parts[0].equals("E")) {
                    task = new Event(parts[2], parts[3], parts[4]);
                }

                if (task != null) {
                    if (parts[1].equals("1")) {
                        task.mark();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file!");
        }
        return tasks;
    }
}
