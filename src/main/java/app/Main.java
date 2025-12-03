package app;

import javax.swing.*;

/**
 * The main class to run the application.
 */
public class Main {
    /**
     * The main method to start the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addSignupView()
                .addLoginView()
                .addLoggedInViewAndUseCase()
                .addLoginUseCase()
                .addSignupUseCase()
                .addEditTaskView()
                .addCreateTaskView()
                .addTeamView()
                .addTeamUseCase()
                .addEditTaskUseCase()
                .addCreateTaskUseCase()
                .addManageTeamView()
                .addManageTeamUseCase()
                .addAssignTaskUseCase()
                .addLeaveTeamUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
