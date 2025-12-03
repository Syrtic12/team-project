package view;

import adapters.create_task.CreateTaskController;
import adapters.create_task.CreateTaskState;
import adapters.create_task.CreateTaskViewModel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View For creating a task.
 */
public class CreateTaskView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create task view";
    private final CreateTaskViewModel createTaskViewModel;
    private CreateTaskController createTaskController;

    private final JTextField titleField = new JTextField(20);
    private final JTextArea descriptionField = new JTextArea(5, 20);
    private final JButton createButton = new JButton("Create");
    private final JButton backButton = new JButton("Back");

    public CreateTaskView(CreateTaskViewModel createTaskViewModel) {
        this.createTaskViewModel = createTaskViewModel;
        this.createTaskViewModel.addPropertyChangeListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel("Create New Task");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        this.add(titleLabel);
        final LabelTextPanel titleInfo = new LabelTextPanel(new JLabel("Title"), titleField);
        this.add(titleInfo);
        final LabelTextPanel descriptionInfo = new LabelTextPanel(new JLabel("Description"),
                new JScrollPane(descriptionField));
        this.add(descriptionInfo);

        final JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
        overallPanel.add(titleInfo);
        overallPanel.add(descriptionInfo);
        this.add(overallPanel);

        final JPanel buttons = new JPanel();
        buttons.add(createButton);
        buttons.add(backButton);
        this.add(buttons);

        createButton.addActionListener(event -> {
            final String title = titleField.getText().trim();
            final String description = descriptionField.getText().trim();

            final CreateTaskState state = createTaskViewModel.getState();

            createTaskController.execute(state.getTeamID(), state.getInvokedBy(), description, title);
        });

        backButton.addActionListener(event -> createTaskController.switchToTeamView());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // not used
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateTaskState state = (CreateTaskState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setCreateTaskController(CreateTaskController createTaskController) {
        this.createTaskController = createTaskController;
    }
}
