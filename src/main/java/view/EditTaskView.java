package view;

import adapters.edit_task.EditTaskController;
import adapters.edit_task.EditTaskState;
import adapters.edit_task.EditTaskViewModel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for editing task.
 */
public class EditTaskView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final String VIEWNAME = "edit task";
    private final transient EditTaskViewModel editTaskViewModel;

    private final JTextField titleField = new JTextField(20);
    private final JTextArea descriptionField = new JTextArea(5, 20);

    private final JComboBox<String> statusDropdown =
            new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});

    private final JButton saveButton;

    private transient EditTaskController editTaskController;

    public EditTaskView(EditTaskViewModel viewModel) {
        this.editTaskViewModel = viewModel;
        this.editTaskViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Edit Task");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel titleInfo = new LabelTextPanel(
                new JLabel("Title"), titleField);
        final LabelTextPanel descriptionInfo = new LabelTextPanel(
                new JLabel("Description"), new JScrollPane(descriptionField));
        final LabelTextPanel statusInfo = new LabelTextPanel(
                new JLabel("Status"), statusDropdown
        );

        final JPanel buttons = new JPanel();
        saveButton = new JButton("Save");
        final JButton cancelButton = new JButton("Cancel");
        final JButton deleteButton = new JButton("Delete");
        buttons.add(saveButton);
        buttons.add(cancelButton);
        buttons.add(deleteButton);

        saveButton.addActionListener(evt -> {
            final EditTaskState state = editTaskViewModel.getState();
            if (evt.getSource().equals(saveButton)) {
                editTaskController.execute(state.getTeamID(), "placeholder", state.getTaskId(),
                        state.getTitle(), state.getDescription(), state.getStatus());
            }
        });

        cancelButton.addActionListener(evt -> editTaskController.switchToTeamView());

        deleteButton.addActionListener(evt -> editTaskController.deleteCurrentTask());

        addFieldListeners();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(titleInfo);
        this.add(descriptionInfo);
        this.add(buttons);
        this.add(statusInfo);
    }

    private void addFieldListeners() {
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            public void helper() {
                    final EditTaskState state = editTaskViewModel.getState();
                    state.setTitle(titleField.getText());
                    editTaskViewModel.setState(state);
                }

            public void insertUpdate(DocumentEvent e) {
                helper();
            }

            public void removeUpdate(DocumentEvent e) {
                helper();
            }

            public void changedUpdate(DocumentEvent e) {
                helper();
            }
        });

        descriptionField.getDocument().addDocumentListener(new DocumentListener() {
            public void helper() {
                final EditTaskState state = editTaskViewModel.getState();
                state.setDescription(descriptionField.getText());
                editTaskViewModel.setState(state);
            }

            public void insertUpdate(DocumentEvent e) {
                helper();
            }

            public void removeUpdate(DocumentEvent e) {
                helper();
            }

            public void changedUpdate(DocumentEvent e) {
                helper();
            }
        });
        statusDropdown.addActionListener(evt -> {
            final EditTaskState state = editTaskViewModel.getState();
            final int selected = statusDropdown.getSelectedIndex();
            state.setStatus(selected);
            editTaskViewModel.setState(state);
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // not needed
    }

    /**
     * Changes the property.
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        final EditTaskState state = (EditTaskState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
        setFields(state);
    }

    private void setFields(EditTaskState state) {
        titleField.setText(state.getTitle());
        descriptionField.setText(state.getDescription());
        statusDropdown.setSelectedIndex(state.getStatus());
    }

    public String getViewName() {
        return VIEWNAME;
    }

    public void setEditTaskController(EditTaskController controller) {
        this.editTaskController = controller;
    }
}
