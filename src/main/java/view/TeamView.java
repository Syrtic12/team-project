package view;

import interface_adapter.team.TeamController;
import interface_adapter.team.TeamState;
import interface_adapter.team.TeamViewModel;

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

    private TeamController teamController;

    private final JLabel teamNameLabel = new JLabel("");

    private final DefaultListModel<String> notStartedModel = new DefaultListModel<>();
    private final DefaultListModel<String> inProgressModel = new DefaultListModel<>();
    private final DefaultListModel<String> completedModel = new DefaultListModel<>();

    private final JList<String> notStartedList = new JList<>(notStartedModel);
    private final JList<String> inProgressList = new JList<>(inProgressModel);
    private final JList<String> completedList = new JList<>(completedModel);

    private final JButton manageTeamButton = new JButton("Manage Team");
    private final JButton createTaskButton = new JButton("Create Task");
    private final JButton backButton = new JButton("Back");


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
                teamController.openManageTeam();
            }
        });
        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (teamController == null) return;
                teamController.openCreateTask();
            }
        });

        notStartedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String taskId = getSelectedTask(notStartedList);
                    if (taskId != null) {
                        teamController.openTask(taskId);
                    }
                }
            }
        });

        inProgressList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String taskId = getSelectedTask(inProgressList);
                    if (taskId != null) {
                        teamController.openTask(taskId);
                    }
                }
            }
        });

        completedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String taskId = getSelectedTask(completedList);
                    if (taskId != null) {
                        teamController.openTask(taskId);
                    }
                }
            }
        });



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

        teamNameLabel.setText(state.getTeamName());
        setTaskLists(state);
    }

    private void setTaskLists(TeamState state) {
        fillModel(notStartedModel, state.getNotStartedTasks());
        fillModel(inProgressModel, state.getInProgressTasks());
        fillModel(completedModel, state.getCompletedTasks());
    }

    private void fillModel(DefaultListModel<String> model, Map<String, String> tasks) {
        model.clear();
        for (Map.Entry<String, String> entry : tasks.entrySet()) {
            model.addElement(entry.getValue() + " (" + entry.getKey() + ")");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setTeamController(TeamController controller) {
        this.teamController = controller;
    }
}