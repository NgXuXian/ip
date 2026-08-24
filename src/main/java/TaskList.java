import java.util.ArrayList;

/**
 * Keeps track of current list of tasks in memory. It handles adding, deleting, and counting the tasks in the list.
 */

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> loadedTasks) {
        this.tasks = loadedTasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public Task remove(int index) {
        return this.tasks.remove(index);
    }
}
