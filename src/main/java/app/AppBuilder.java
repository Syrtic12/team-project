package app;

import dataaccess.*;
import dataaccess.KandoMongoDatabase;
import entity.UserFactory;
import adapters.ViewManagerModel;
import adapters.edit_task.EditTaskController;
import adapters.edit_task.EditTaskPresenter;
import adapters.edit_task.EditTaskViewModel;
import adapters.create_task.CreateTaskController;
import adapters.create_task.CreateTaskPresenter;
import adapters.create_task.CreateTaskViewModel;
import adapters.leave_team.LeaveTeamController;
import adapters.leave_team.LeaveTeamPresenter;
import adapters.leave_team.LeaveTeamState;
import adapters.leave_team.LeaveTeamViewModel;
import adapters.logged_in.LoggedInController;
import adapters.logged_in.LoggedInPresenter;
import adapters.logged_in.LoggedInState;
import adapters.logged_in.LoggedInViewModel;
import adapters.login.LoginController;
import adapters.login.LoginPresenter;
import adapters.login.LoginViewModel;
import adapters.manage_team.ManageTeamController;
import adapters.manage_team.ManageTeamPresenter;
import adapters.manage_team.ManageTeamViewModel;
import adapters.signup.SignupController;
import adapters.signup.SignupPresenter;
import adapters.signup.SignupViewModel;
import adapters.assign_task.AssignTaskController;
import adapters.assign_task.AssignTaskPresenter;
import adapters.assign_task.AssignTaskViewModel;
import usecase.assign_task.AssignTaskInputBoundary;
import usecase.assign_task.AssignTaskInteractor;
import usecase.assign_task.AssignTaskOutputBoundary;
import adapters.team.TeamViewModel;
import usecase.edit_task.EditTaskInputBoundary;
import usecase.edit_task.EditTaskInteractor;
import usecase.edit_task.EditTaskOutputBoundary;
import usecase.create_task.CreateTaskInputBoundary;
import usecase.create_task.CreateTaskInteractor;
import usecase.create_task.CreateTaskOutputBoundary;
import usecase.leave_team.LeaveTeamInputBoundary;
import usecase.leave_team.LeaveTeamInteractor;
import usecase.leave_team.LeaveTeamOutputBoundary;
import usecase.logged_in.LoggedInInputBoundary;
import usecase.logged_in.LoggedInInteractor;
import usecase.logged_in.LoggedInOutputBoundary;
import usecase.login.LogInInputBoundary;
import usecase.login.LogInInteractor;
import usecase.login.LogInOutputBoundary;
import usecase.signup.SignUpInputBoundary;
import usecase.signup.SignUpInteractor;
import usecase.signup.SignUpOutputBoundary;
import usecase.team.*;
import adapters.team.*;
import usecase.teammate_management.TeammateManagementInputBoundary;
import usecase.teammate_management.TeammateManagementInteractor;
import usecase.teammate_management.TeammateManagementOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the application by assembling views, view models, controllers, and use cases.
 */
public class AppBuilder {
    private final UserFactory userFactory = new UserFactory();
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final KandoMongoDatabase dataAccessObject = new KandoMongoDatabase();

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
    private CreateTaskViewModel createTaskViewModel;
    private CreateTaskView createTaskView;
    private ManageTeamViewModel manageTeamViewModel;
    private ManageTeamView manageTeamView;
    private AssignTaskViewModel assignTaskViewModel;
    private LoggedInInputBoundary loggedInInputBoundary;
    private LeaveTeamViewModel leaveTeamViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the signup view to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the login view to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the edit task view to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addEditTaskView() {
        editTaskViewModel = new EditTaskViewModel();
        editTaskView = new EditTaskView(editTaskViewModel);
        cardPanel.add(editTaskView, editTaskView.getViewName());
        return this;
    }

    /**
     * Adds the create task view to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addCreateTaskView() {
        createTaskViewModel = new CreateTaskViewModel();
        createTaskView = new CreateTaskView(createTaskViewModel);
        cardPanel.add(createTaskView, createTaskView.getViewName());
        return this;
    }

    /**
     * Adds the logged-in view and its associated use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addLoggedInViewAndUseCase() {

        // 1. Create shared state ONCE
        final LoggedInState sharedState = new LoggedInState();
        this.loggedInViewModel = new LoggedInViewModel(sharedState);

        final LoggedInDataAccessObject dao = new LoggedInDataAccessObject(dataAccessObject);
        this.teamViewModel = new TeamViewModel();

        // 2. Pass sharedState into presenter
        final LoggedInOutputBoundary outputBoundary = new LoggedInPresenter(
                loggedInViewModel,
                sharedState,
                viewManagerModel,
                teamViewModel,
                loginViewModel,
                dao
        );

        // 3. Pass sharedState into interactor
        final LoggedInInputBoundary interactor = new LoggedInInteractor(
                dao,
                outputBoundary,
                sharedState
        );

        final LoggedInController controller = new LoggedInController(interactor);

        loggedInView = new LoggedInView(loggedInViewModel);
        loggedInView.setLoggedInController(controller);
        cardPanel.add(loggedInView, loggedInView.getViewName());

        return this;
    }

    /**
     * Adds the signup use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addSignupUseCase() {
        final SignUpOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignUpInputBoundary userSignupInteractor = new SignUpInteractor(
                new SignUpDataAccessObject(dataAccessObject), signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the team view to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addTeamView() {
        teamView = new TeamView(teamViewModel);
        cardPanel.add(teamView, teamView.getViewName());
        return this;
    }

    /**
     * Adds the team use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addTeamUseCase() {
        this.manageTeamViewModel = new ManageTeamViewModel();
        final TeamOutputBoundary teamOutputBoundary = new TeamPresenter(viewManagerModel, loggedInViewModel,
                manageTeamViewModel, teamViewModel, createTaskViewModel, editTaskViewModel);
        final TeamInputBoundary teamInteractor = new TeamInteractor(new TeamDataAccessObject(dataAccessObject),
                teamOutputBoundary);
        final TeamController teamController = new TeamController(teamInteractor);
        teamView.setTeamController(teamController);
        return this;
    }

    /**
     * Adds the manage team view to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addManageTeamView() {
        manageTeamView = new ManageTeamView(manageTeamViewModel);
        cardPanel.add(manageTeamView, manageTeamView.getViewName());
        return this;
    }

    /**
     * Adds the manage team use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addManageTeamUseCase() {
        final TeammateManagementOutputBoundary teammateManagementOutputBoundary = new ManageTeamPresenter(
                viewManagerModel, loggedInViewModel, manageTeamViewModel, teamViewModel);
        final TeammateManagementInputBoundary teammateManagementInteractor = new TeammateManagementInteractor(
                new TeammateManagementDataAccessObject(dataAccessObject), teammateManagementOutputBoundary);
        final ManageTeamController manageTeamController = new ManageTeamController(teammateManagementInteractor);
        manageTeamView.setManageTeamController(manageTeamController);
        return this;
    }

    /**
     * Adds the login use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addLoginUseCase() {
        final LogInOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LogInInputBoundary loginInteractor = new LogInInteractor(
                new LogInDataAccessObject(dataAccessObject), loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the edit task use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addEditTaskUseCase() {
        final EditTaskOutputBoundary editTaskOutputBoundary = new EditTaskPresenter(editTaskViewModel,
                viewManagerModel, teamViewModel);
        final EditTaskInputBoundary editTaskInteractor = new EditTaskInteractor(
                new TaskDataAccessObject(dataAccessObject), editTaskOutputBoundary);
        final EditTaskController editTaskController = new EditTaskController(editTaskInteractor);
        editTaskView.setEditTaskController(editTaskController);
        return this;
    }

    /**
     * Adds the create task use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addCreateTaskUseCase() {
        final CreateTaskOutputBoundary createTaskOutputBoundary = new CreateTaskPresenter(createTaskViewModel,
                viewManagerModel, teamViewModel, loggedInInputBoundary);
        final CreateTaskInputBoundary createTaskInteractor = new CreateTaskInteractor(
                new TaskDataAccessObject(dataAccessObject), createTaskOutputBoundary);
        final CreateTaskController createTaskController = new CreateTaskController(createTaskInteractor);
        createTaskView.setCreateTaskController(createTaskController);
        return this;
    }

    /**
     * Adds the assign task use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addAssignTaskUseCase() {
        this.assignTaskViewModel = new AssignTaskViewModel();

        final AssignTaskOutputBoundary assignTaskOutputBoundary = new AssignTaskPresenter(assignTaskViewModel,
                teamViewModel);
        final AssignTaskInputBoundary assignTaskInteractor = new AssignTaskInteractor(
                new AssignTaskDataAccessObject(dataAccessObject), assignTaskOutputBoundary);
        final AssignTaskController assignTaskController = new AssignTaskController(assignTaskInteractor, teamViewModel);

        teamView.setAssignTaskController(assignTaskController);
        teamView.setAssignTaskViewModel(assignTaskViewModel);
        return this;
    }

    /**
     * Adds the leave team use case to the application.
     * @return the AppBuilder instance for method chaining
     */
    public AppBuilder addLeaveTeamUseCase() {
        final LeaveTeamState leaveTeamState = new LeaveTeamState();
        this.leaveTeamViewModel = new LeaveTeamViewModel(leaveTeamState);
        final LeaveTeamOutputBoundary leaveTeamOutputBoundary = new LeaveTeamPresenter(leaveTeamViewModel,
                viewManagerModel, loggedInViewModel);
        final LeaveTeamInputBoundary leaveTeamInteractor = new LeaveTeamInteractor(
                new LeaveTeamDataAccessObject(dataAccessObject), leaveTeamOutputBoundary);
        final LeaveTeamController leaveTeamController = new LeaveTeamController(leaveTeamInteractor);
        teamView.setLeaveTeamController(leaveTeamController);
        return this;
    }

    /**
     * Builds the application by assembling all components.
     * @return the constructed JFrame application
     */
    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
