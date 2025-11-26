package app;

import javax.swing.*;

public class    Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSignupView()
                .addLoginView()
                .addLoggedInViewAndUseCase()
                .addLoginUseCase()
                .addSignupUseCase()
                .addTeamViewAndUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}