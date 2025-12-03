package view;

import adapters.logged_in.LoggedInController;
import adapters.logged_in.LoggedInState;
import adapters.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The view displayed after a user logs in successfully.
 * Shows a welcome message and provides buttons for creating a team,
 * viewing existing teams, and logging out.
 * Implements propertychangelistener to react to changes
 * in the loggedinviewmodel.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private static final int SIZE = 18;
    private static final int HEIGHT = 10;

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private LoggedInController loggedInController;

    private final JLabel welcomeLabel;
    private final JButton createTeamButton;
    private final JButton viewTeamsButton;
    private final JButton logOutButton;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;

        loggedInViewModel.addPropertyChangeListener(this);

        final JLabel titleLabel = new JLabel("Team Management");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, SIZE));

        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        createTeamButton = new JButton("Create New Team");
        createTeamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewTeamsButton = new JButton("View My Teams");
        viewTeamsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOutButton = new JButton("Log Out");
        logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String teamName = JOptionPane.showInputDialog(
                        LoggedInView.this,
                        "Enter team name: ",
                        "Create new team",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (teamName != null && !teamName.isEmpty()) {
                    loggedInController.createTeam(teamName.trim());
                }
            }
        });

        viewTeamsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final LoggedInState currentState = loggedInViewModel.getState();
                final java.util.List<String> teams = currentState.getTeams();

                if (teams.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LoggedInView.this,
                            "You don't have any teams yet. Create one to get started",
                            "No teams",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                final String[] teamArray = teams.toArray(new String[0]);

                final String selectedTeam = (String) JOptionPane.showInputDialog(
                        LoggedInView.this,
                        "Select a team to view",
                        "Your Teams",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        teamArray,
                        teamArray[0]
                );

                if (selectedTeam != null) {
                    loggedInController.switchToTeamView(selectedTeam);
                }
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final int confirm = JOptionPane.showConfirmDialog(
                        LoggedInView.this,
                        "Are you sure you want to log out",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    loggedInController.logout();
                }
            }
        });

        buttons.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        buttons.add(createTeamButton);
        buttons.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        buttons.add(viewTeamsButton);
        buttons.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        buttons.add(logOutButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        this.add(welcomeLabel);
        this.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        final LoggedInState state = (LoggedInState) e.getNewValue();
        welcomeLabel.setText("Welcome, " + state.getEmail());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }
}
