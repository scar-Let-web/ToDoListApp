public class Task implements Comparable<Task> {

    public enum Status {
        NOT_STARTED,
        MARKED_AS_DONE,
        DONE
    }

    private String title;
    private String category;
    private int priority;
    private Status status;

    public Task(String title, String category, int priority) {
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.status = Status.NOT_STARTED;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        String symbol;
        switch (status) {
            case NOT_STARTED: symbol = "○"; break;
            case MARKED_AS_DONE: symbol = "◐"; break;
            case DONE: symbol = "✔"; break;
            default: symbol = "";
        }
        return symbol + " (" + priority + ") " + title;
    }

    // ===== FILE PERSISTENCE =====
    public String toFileString() {
        return title + "|" + category + "|" + priority + "|" + status;
    }

    public static Task fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid task data: " + line);
        }

        Task task = new Task(
                parts[0],
                parts[1],
                Integer.parseInt(parts[2])
        );
        task.setStatus(Status.valueOf(parts[3]));
        return task;
    }
}
