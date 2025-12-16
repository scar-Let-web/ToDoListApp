import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {

    private List<Task> tasks = new ArrayList<>();

    public TaskManager() {
        loadTasks();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        saveTasks();
    }

    public List<Task> getTasksByCategory(String category) {
        return tasks.stream()
                .filter(t -> t.getCategory().equals(category))
                .sorted()
                .collect(Collectors.toList());
    }

    public void saveTasks() {
        TaskFileHandler.save(tasks);
    }

    public void loadTasks() {
        TaskFileHandler.load(tasks);
    }
}
