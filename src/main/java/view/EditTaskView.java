package view;

import interface_adapter.edit_task.EditTaskController;
import interface_adapter.edit_task.EditTaskState;
import interface_adapter.edit_task.EditTaskViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditTaskView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "edit task";
    private final EditTaskViewModel editTaskViewModel;

    private final JTextField titleField = new JTextField(20);
    private final JTextArea descriptionField = new JTextArea(5, 20);

    private final JButton saveButton;
    private final JButton cancelButton;

    private EditTaskController editTaskController = null;

    public EditTaskView(EditTaskViewModel viewModel) {
        this.editTaskViewModel = viewModel;
        this.editTaskViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Edit Task");
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        final LabelTextPanel titleInfo = new LabelTextPanel(
                new JLabel("Title"), titleField);
        final LabelTextPanel descriptionInfo = new LabelTextPanel(
                new JLabel("Description"), new JScrollPane(descriptionField));

        final JPanel buttons = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttons.add(saveButton);
        buttons.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                final EditTaskState state = editTaskViewModel.getState();
                if (evt.getSource().equals(saveButton)){
                    editTaskController.execute(state.getTaskId(), state.getDescription(), state.getTitle());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editTaskController.switchToTaskView();
            }
        });

        addFieldListeners();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(titleInfo);
        this.add(descriptionInfo);
        this.add(buttons);

 }

    private void addFieldListeners() {

    }


    public void actionPerformed(ActionEvent evt) {

    }


    public void propertyChange(PropertyChangeEvent evt) {

    }

    private void setFields(EditTaskState state) {

    }


    public String getViewName() {

    }

    public void setEditTaskController(EditTaskController controller) {

    }
}
