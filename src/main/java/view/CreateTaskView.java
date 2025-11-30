package view;

import interface_adapter.create_task.CreateTaskController;
import interface_adapter.create_task.CreateTaskState;
import interface_adapter.create_task.CreateTaskViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateTaskView extends JPanel implements ActionListener, PropertyChangeListener{
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
        JLabel titleLabel = new JLabel("Create New Task");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        this.add(titleLabel);
        LabelTextPanel titleInfo = new LabelTextPanel(new JLabel("Title"), titleField);
        this.add(titleInfo);
        LabelTextPanel descriptionInfo = new LabelTextPanel(new JLabel("Description"), new JScrollPane(descriptionField));
        this.add(descriptionInfo);

        JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
        overallPanel.add(titleInfo);
        overallPanel.add(descriptionInfo);
        this.add(overallPanel);

        JPanel buttons = new JPanel();
        buttons.add(createButton);
        buttons.add(backButton);
        this.add(buttons);

        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();

            final CreateTaskState state = createTaskViewModel.getState();

            createTaskController.execute(state.getTeamID(), state.getInvokedBy(), description, title);
        });

        backButton.addActionListener(e -> createTaskController.switchToTeamView());

    }

    @Override
    public void actionPerformed(ActionEvent e){
        // not used
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateTaskState state = (CreateTaskState) evt.getNewValue();
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
