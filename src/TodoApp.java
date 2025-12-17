import javax.swing.*;
import java.awt.*;

public class TodoApp extends JFrame {

    private TaskManager manager = new TaskManager();
    private String[] categories = {"Academic", "Personal", "Career"};
    private TaskListPanel[] panels = new TaskListPanel[categories.length];

    public TodoApp() {

        setTitle("To-Do List");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SAVE ON CLOSE (SWING WAY) =====
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                manager.saveTasks();
            }
        });

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        for (int i = 0; i < categories.length; i++) {
            panels[i] = new TaskListPanel();
            tabs.add(categories[i], panels[i]);
        }

        // Input panel
        JPanel input = new JPanel(new FlowLayout());
        JTextField taskField = new JTextField(12);
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        JSpinner prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        JButton addBtn = new JButton("Add");

        input.add(new JLabel("Task"));
        input.add(taskField);
        input.add(new JLabel("Category"));
        input.add(categoryBox);
        input.add(new JLabel("Priority"));
        input.add(prioritySpinner);
        input.add(addBtn);

        // Action buttons
        JPanel actions = new JPanel();

        JComboBox<String> statusBox = new JComboBox<>(new String[]{
                "Not Yet Started", "In Progress", "Done"
        });

        JButton applyStatusBtn = new JButton("Apply Status");
        JButton deleteBtn = new JButton("Delete");

        actions.add(statusBox);
        actions.add(applyStatusBtn);
        actions.add(deleteBtn);

        add(tabs, BorderLayout.CENTER);
        add(input, BorderLayout.NORTH);
        add(actions, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String title = taskField.getText().trim();
            if (!title.isEmpty()) {
                manager.addTask(new Task(
                        title,
                        (String) categoryBox.getSelectedItem(),
                        (int) prioritySpinner.getValue()
                ));
                taskField.setText("");
                refreshAll();
            }
        });

        applyStatusBtn.addActionListener(e -> {
            Task selected = panels[tabs.getSelectedIndex()].getSelectedTask();
            if (selected != null) {
                switch ((String) statusBox.getSelectedItem()) {
                    case "Not Yet Started":
                        selected.setStatus(Task.Status.NOT_STARTED);
                        break;
                    case "In Progress":
                        selected.setStatus(Task.Status.IN_PROGRESS);
                        break;
                    case "Done":
                        selected.setStatus(Task.Status.DONE);
                        break;
                }
                manager.saveTasks();
                refreshAll();
            }
        });

        deleteBtn.addActionListener(e -> {
            Task selected = panels[tabs.getSelectedIndex()].getSelectedTask();
            if (selected != null) {
                manager.removeTask(selected);
                refreshAll();
            }
        });

        refreshAll();
    }

    private void refreshAll() {
        for (int i = 0; i < categories.length; i++) {
            panels[i].updateTasks(manager.getTasksByCategory(categories[i]));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TodoApp().setVisible(true));
    }
}