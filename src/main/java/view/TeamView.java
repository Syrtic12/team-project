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
                teamController.openLoggedInView();
            }
        });
        manageTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamController.openManageTeam();
            }
        });
        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamController.openCreateTask();
            }
        });

    }

    private String getSelectedTask() {
    }

    private Component createTaskPanel(String notStarted, JList<String> notStartedList) {
    }

    private void setLayout(BoxLayout boxLayout) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    private void setTaskLists(TeamState state) {
    }

    public String getViewName() {
        return viewName;
    }

    public void setTeamController(TeamController controller) {
        this.teamController = controller;
    }
}