import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TaskListPanel extends JPanel {

    private DefaultListModel<Task> model = new DefaultListModel<>();
    private JList<Task> list = new JList<>(model);

    public TaskListPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void updateTasks(List<Task> tasks) {
        model.clear();
        for (Task task : tasks) {
            model.addElement(task);
        }
    }

    public Task getSelectedTask() {
        return list.getSelectedValue();
    }
}
