package app;

import data_access.*;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.edit_task.EditTaskController;
import interface_adapter.edit_task.EditTaskPresenter;
import interface_adapter.edit_task.EditTaskViewModel;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.manage_team.ManageTeamController;
import interface_adapter.manage_team.ManageTeamPresenter;
import interface_adapter.manage_team.ManageTeamViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.team.TeamViewModel;
import use_case.edit_task.EditTaskDataAccessInterface;
import use_case.edit_task.EditTaskInputBoundary;
import use_case.edit_task.EditTaskInteractor;
import use_case.edit_task.EditTaskOutputBoundary;
import use_case.logged_in.LoggedInInputBoundary;
import use_case.logged_in.LoggedInInteractor;
import use_case.logged_in.LoggedInOutputBoundary;
import use_case.login.LogInInputBoundary;
import use_case.login.LogInInteractor;
import use_case.login.LogInOutputBoundary;
import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInteractor;
import use_case.signup.SignUpOutputBoundary;
import use_case.team.*;
import interface_adapter.team.*;
import use_case.teammateManagement.TeammateManagementInputBoundary;
import use_case.teammateManagement.TeammateManagementInteractor;
import use_case.teammateManagement.TeammateManagementOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final KandoMongoDatabase DataAccessObject = new KandoMongoDatabase();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private TeamView teamView;
    private TeamViewModel teamViewModel;
    private EditTaskViewModel editTaskViewModel;
    private EditTaskView editTaskView;
    private ManageTeamViewModel manageTeamViewModel;
    private ManageTeamView manageTeamView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addEditTaskView() {
        editTaskViewModel = new EditTaskViewModel();
        editTaskView = new EditTaskView(editTaskViewModel);
        cardPanel.add(editTaskView, editTaskView.getViewName());
        return this;
    }


    public AppBuilder addLoggedInViewAndUseCase() {
        loggedInViewModel = new LoggedInViewModel();
        LoggedInDataAccessObject loggedInDataAccessObject = new LoggedInDataAccessObject(DataAccessObject);
        this.teamViewModel = new TeamViewModel();
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(loggedInViewModel,
                new LoggedInState(), viewManagerModel, this.teamViewModel, loginViewModel, loggedInDataAccessObject);
        final LoggedInInputBoundary loggedInInteractor = new LoggedInInteractor(
                loggedInDataAccessObject, loggedInOutputBoundary);
        LoggedInController loggedInController = new LoggedInController(loggedInInteractor);
        loggedInView = new LoggedInView(loggedInViewModel);
        loggedInView.setLoggedInController(loggedInController);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignUpOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignUpInputBoundary userSignupInteractor = new SignUpInteractor(
                new SignUpDataAccessObject(DataAccessObject), signupOutputBoundary, userFactory);

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addTeamView() {
        teamView = new TeamView(teamViewModel);
        cardPanel.add(teamView, teamView.getViewName());
        return this;
    }

    public AppBuilder addTeamUseCase() {
        this.manageTeamViewModel = new ManageTeamViewModel();
        final TeamOutputBoundary teamOutputBoundary = new TeamPresenter(viewManagerModel, loggedInViewModel,
                manageTeamViewModel, teamViewModel, editTaskViewModel);
        final TeamInputBoundary teamInteractor = new TeamInteractor(new TeamDataAccessObject(DataAccessObject), teamOutputBoundary);
        TeamController teamController = new TeamController(teamInteractor);
        teamView.setTeamController(teamController);
        return this;
    }

    public AppBuilder addManageTeamView() {
        manageTeamView = new ManageTeamView(manageTeamViewModel);
        cardPanel.add(manageTeamView, manageTeamView.getViewName());
        return this;
    }

    public AppBuilder addManageTeamUseCase() {
        final TeammateManagementOutputBoundary teammateManagementOutputBoundary = new ManageTeamPresenter(
                viewManagerModel, loggedInViewModel, manageTeamViewModel, teamViewModel);
        final TeammateManagementInputBoundary teammateManagementInteractor = new TeammateManagementInteractor(
                new TeammateManagementDataAccessObject(DataAccessObject), teammateManagementOutputBoundary);
        ManageTeamController manageTeamController = new ManageTeamController(teammateManagementInteractor);
        manageTeamView.setManageTeamController(manageTeamController);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LogInOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LogInInputBoundary loginInteractor = new LogInInteractor(
                new LogInDataAccessObject(DataAccessObject), loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addEditTaskUseCase() {
        final EditTaskOutputBoundary editTaskOutputBoundary = new EditTaskPresenter(editTaskViewModel,
                viewManagerModel, teamViewModel);
        final EditTaskInputBoundary editTaskInteractor = new EditTaskInteractor(
                new TaskDataAccessObject(DataAccessObject), editTaskOutputBoundary);
        EditTaskController editTaskController = new EditTaskController(editTaskInteractor);
        editTaskView.setEditTaskController(editTaskController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}