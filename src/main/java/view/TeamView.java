
package view;

import adapters.assign_task.AssignTaskController;
import adapters.assign_task.AssignTaskState;
import adapters.assign_task.AssignTaskViewModel;
import adapters.team.TeamController;
import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.team.TaskInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class TeamView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "team view";
    private final TeamViewModel teamViewModel;
    private AssignTaskViewModel assignTaskViewModel;

    private TeamController teamController;
    private AssignTaskController assignTaskController;

    private final JLabel teamNameLabel = new JLabel("");
    private String teamId;
    private final DefaultListModel<String> notStartedModel = new DefaultListModel<>();
    private final DefaultListModel<String> inProgressModel = new DefaultListModel<>();
    private final DefaultListModel<String> completedModel = new DefaultListModel<>();

    private final JList<String> notStartedList = new JList<>(notStartedModel);
    private final JList<String> inProgressList = new JList<>(inProgressModel);
    private final JList<String> completedList = new JList<>(completedModel);

    private final JButton manageTeamButton = new JButton("Manage Team");
    private final JButton createTaskButton = new JButton("Create Task");
    private final JButton assignTaskButton = new JButton("Assign Task");
    private final JButton backButton = new JButton("Back");
    private String userId;
    private String leaderId;

    private String selectedTaskId = null;
    private String selectedTaskTitle = null;


    public TeamView(TeamViewModel viewModel) {
        this.teamViewModel = viewModel;
        this.teamViewModel.addPropertyChangeListener(this);


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        teamNameLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(teamNameLabel);

        JPanel listsPanel = new JPanel();
        listsPanel.setLayout(new GridLayout(1, 3, 10, 10));

        listsPanel.add(createTaskPanel("Not Started", notStartedList));
        listsPanel.add(createTaskPanel("In Progress", inProgressList));
        listsPanel.add(createTaskPanel("Completed", completedList));

        this.add(listsPanel);

        JPanel buttons = new JPanel();
        buttons.add(manageTeamButton);
        buttons.add(createTaskButton);
        buttons.add(assignTaskButton);
        buttons.add(backButton);

        this.add(buttons);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (teamController == null) return;
                teamController.openLoggedInView();
            }
        });
        manageTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (teamController == null) return;
                teamController.openManageTeam(teamId);
            }
        });
        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String invokedBy = userId;   // ⭐ FIXED HERE ⭐

                if (invokedBy == null) {
                    System.out.println("ERROR: invokedBy/userId is NULL");
                    return;
                }

                if (teamId != null) {
                    teamController.createTask(teamId, invokedBy);
                }
            }
        });
        assignTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedTaskId == null) {
                    JOptionPane.showMessageDialog(TeamView.this,
                            "Please select a task first by clicking on it",
                            "No task selected",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                openAssignTaskDialog();
            }
        });


        notStartedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String taskId = getSelectedTask(notStartedList);
                    TaskInfo info = teamViewModel.getState().getNotStartedTasks().get(taskId);
                    if (taskId != null) {
                        selectedTaskId = taskId;
                        selectedTaskTitle = info != null ? info.getTitle() : "";
                        teamController.editTask(taskId, teamId, 0, info.getTitle(), info.getDescription());

                    }
                }
            }
        });

        inProgressList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String taskId = getSelectedTask(inProgressList);
                    TaskInfo info = teamViewModel.getState().getInProgressTasks().get(taskId);
                    if (taskId != null) {
                        selectedTaskId = taskId;
                        selectedTaskTitle = info != null ? info.getTitle() : "";
                        teamController.editTask(taskId, teamId, 1, info.getTitle(), info.getDescription());
                    }
                }
            }
        });

        completedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String taskId = getSelectedTask(completedList);
                    TaskInfo info = teamViewModel.getState().getCompletedTasks().get(taskId);
                    if (taskId != null) {
                        selectedTaskId = taskId;
                        selectedTaskTitle = info != null ? info.getTitle() : "";
                        teamController.editTask(taskId, teamId, 2, info.getTitle(), info.getDescription());
                    }
                }
            }
        });
    }

    public void openAssignTaskDialog() {
        if (selectedTaskId == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a task first.",
                    "No task selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        TeamState state =  teamViewModel.getState();
        this.leaderId = state.getLeaderId();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "AssignTask", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Assign Task: " + selectedTaskTitle);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        JLabel emailLabel = new JLabel("Team Member Email: ");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(emailLabel);
        contentPanel.add(Box.createVerticalStrut(5));

        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(300, 25));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(emailField);

        dialog.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton assignButton = new JButton("Assign");
        JButton cancelButton = new JButton("Cancel");

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter a team email.",
                            "Empty Email",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (selectedTaskId != null && leaderId != null) {
                    assignTaskController.execute(selectedTaskId, email, leaderId);

                    AssignTaskState resultState = assignTaskViewModel.getState();
                    if (resultState.getError() != null) {
                        JOptionPane.showMessageDialog(dialog,
                                "Assignment Failed",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog,
                                "Task assigned successfully",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        buttonPanel.add(assignButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);

    }

    private void clearSelections() {
        notStartedList.clearSelection();
        inProgressList.clearSelection();
        completedList.clearSelection();
        notStartedList.setSelectedIndex(-1);
        inProgressList.setSelectedIndex(-1);
        completedList.setSelectedIndex(-1);
    }


    private String getSelectedTask(JList<String> list) {
        String selected = list.getSelectedValue();
        if (selected == null) return null;

        int start = selected.lastIndexOf("(");
        int end = selected.lastIndexOf(")");

        if (start == -1 || end == -1 || end <= start) {
            return null;
        }

        return selected.substring(start + 1, end);
    }

    private JPanel createTaskPanel(String title, JList<String> taskList) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(taskList);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // not needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        TeamState state = (TeamState) evt.getNewValue();
        this.teamId = state.getTeamName();
        this.userId = state.getUserId();
        teamNameLabel.setText(state.getTeamName());
        clearSelections();
        setTaskLists(state);
        System.out.println("DEBUG TeamView: userId = " + state.getUserId());

    }

    private void setTaskLists(TeamState state) {
        fillModel(notStartedModel, state.getNotStartedTasks());
        fillModel(inProgressModel, state.getInProgressTasks());
        fillModel(completedModel, state.getCompletedTasks());
    }

    private void fillModel(DefaultListModel<String> model, Map<String, TaskInfo> tasks) {
        model.clear();
        for (Map.Entry<String, TaskInfo> entry : tasks.entrySet()) {
            TaskInfo info = entry.getValue();
            model.addElement(info.getTitle() + " | "
                    + info.getAssignedUsers()
                    + " (" + info.getId() + ")");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setTeamController(TeamController controller) {
        this.teamController = controller;
    }

    public void setAssignTaskController(AssignTaskController controller) {
        this.assignTaskController = controller;
    }

    public void setAssignTaskViewModel(AssignTaskViewModel viewModel) {
        this.assignTaskViewModel = viewModel;
    }
}