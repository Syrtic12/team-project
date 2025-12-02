
package view;

import adapters.assign_task.AssignTaskController;
import adapters.assign_task.AssignTaskState;
import adapters.assign_task.AssignTaskViewModel;
import adapters.leave_team.LeaveTeamController;
import adapters.team.TeamController;
import adapters.team.TeamState;
import adapters.team.TeamViewModel;
import usecase.team.TaskInfo;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class TeamView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final Integer COLUMNS = 3;
    private static final Integer GAP = 10;
    private static final String VIEW_NAME = "team view";
    private final transient TeamViewModel teamViewModel;
    private transient AssignTaskViewModel assignTaskViewModel;

    private transient TeamController teamController;
    private transient AssignTaskController assignTaskController;
    private transient LeaveTeamController leaveTeamController;

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
    private final JButton leaveTeamButton = new JButton("Leave Team");
    private final JButton backButton = new JButton("Back");
    private String userId;
    private String leaderId;

    private String selectedTaskId;
    private String selectedTaskTitle;

    public TeamView(TeamViewModel viewModel) {
        this.teamViewModel = viewModel;
        this.teamViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        teamNameLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(teamNameLabel);

        final JPanel listsPanel = new JPanel();
        listsPanel.setLayout(new GridLayout(1, COLUMNS, GAP, GAP));

        listsPanel.add(createTaskPanel("Not Started", notStartedList));
        listsPanel.add(createTaskPanel("In Progress", inProgressList));
        listsPanel.add(createTaskPanel("Completed", completedList));

        this.add(listsPanel);

        final JPanel buttons = new JPanel();
        buttons.add(manageTeamButton);
        buttons.add(createTaskButton);
        buttons.add(assignTaskButton);
        buttons.add(leaveTeamButton);
        buttons.add(backButton);

        this.add(buttons);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (teamController == null) {
                    return;
                }
                teamController.openLoggedInView();
            }
        });
        manageTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (teamController == null) {
                    return;
                }
                teamController.openManageTeam(teamId);
            }
        });
        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String invokedBy = userId;
                if (invokedBy == null) {
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

                }
                else {
                    openAssignTaskDialog();
                }
            }
        });

        leaveTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leaveTeamController == null) {
                    return;
                }
                final int confirm = JOptionPane.showConfirmDialog(
                        TeamView.this,
                        "Are you sure you want to leave this team?",
                        "Confirm Leave Team",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                leaveTeamController.execute(teamId, userId);
            }
        });

        notStartedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final String taskId = getSelectedTask(notStartedList);
                    final TaskInfo info = teamViewModel.getState().getNotStartedTasks().get(taskId);
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
                    final String taskId = getSelectedTask(inProgressList);
                    final TaskInfo info = teamViewModel.getState().getInProgressTasks().get(taskId);
                    if (taskId != null) {
                        selectedTaskId = taskId;
                        if (info != null) {
                            selectedTaskTitle = info.getTitle();
                        }
                        else {
                            selectedTaskTitle = "";
                        }
                        teamController.editTask(taskId, teamId, 1, info.getTitle(), info.getDescription());
                    }
                }
            }
        });

        completedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final String taskId = getSelectedTask(completedList);
                    final TaskInfo info = teamViewModel.getState().getCompletedTasks().get(taskId);
                    if (taskId != null) {
                        selectedTaskId = taskId;
                        if (info != null) {
                            selectedTaskTitle = info.getTitle();
                        }
                        else {
                            selectedTaskTitle = "";
                        }
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

        final TeamState state = teamViewModel.getState();
        this.leaderId = state.getLeaderId();

        final JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "AssignTask", true);
        dialog.setLayout(new BorderLayout(GAP, GAP));
        final int width = 350;
        final int height = 150;
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        final JLabel titleLabel = new JLabel("Assign Task: " + selectedTaskTitle);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(GAP));

        final int newheight = 5;
        final JLabel emailLabel = new JLabel("Team Member Email: ");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(emailLabel);
        contentPanel.add(Box.createVerticalStrut(newheight));

        final int newwidth = 300;
        final int newerheight = 25;
        final JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(newwidth, newerheight));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(emailField);

        dialog.add(contentPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = getJpanel(emailField, dialog);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);

    }

    private JPanel getJpanel(JTextField emailField, JDialog dialog) {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JButton assignButton = new JButton("Assign");
        final JButton cancelButton = new JButton("Cancel");

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String email = emailField.getText().trim();
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter a team email.",
                            "Empty Email",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (selectedTaskId != null && leaderId != null) {
                    assignTaskController.execute(selectedTaskId, email, leaderId);
                    final AssignTaskState resultState = assignTaskViewModel.getState();
                    if (resultState.getError() != null) {
                        JOptionPane.showMessageDialog(dialog,
                                "Assignment Failed",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else {
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
        return buttonPanel;
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
        String result = null;

        final String selected = list.getSelectedValue();
        if (selected != null) {
            final int start = selected.lastIndexOf("(");
            final int end = selected.lastIndexOf(")");

            if (start != -1 && end != -1 && end > start) {
                result = selected.substring(start + 1, end);
            }
        }

        return result;
    }

    private JPanel createTaskPanel(String title, JList<String> taskList) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JLabel label = new JLabel(title, SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        final JScrollPane scrollPane = new JScrollPane(taskList);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // not needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final TeamState state = (TeamState) evt.getNewValue();
        this.teamId = state.getTeamName();
        this.userId = state.getUserId();
        teamNameLabel.setText(state.getTeamName());
        clearSelections();
        setTaskLists(state);
    }

    private void setTaskLists(TeamState state) {
        fillModel(notStartedModel, state.getNotStartedTasks());
        fillModel(inProgressModel, state.getInProgressTasks());
        fillModel(completedModel, state.getCompletedTasks());
    }

    private void fillModel(DefaultListModel<String> model, Map<String, TaskInfo> tasks) {
        model.clear();
        for (Map.Entry<String, TaskInfo> entry : tasks.entrySet()) {
            final TaskInfo info = entry.getValue();
            model.addElement(info.getTitle() + " | "
                    + info.getAssignedUsers()
                    + " (" + info.getId() + ")");
        }
    }

    public String getViewName() {
        return VIEW_NAME;
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

    public void setLeaveTeamController(LeaveTeamController controller) {
        this.leaveTeamController = controller;
    }
}
