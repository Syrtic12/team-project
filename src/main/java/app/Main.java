package app;

import data_access.KandoMongoDatabase;
import data_access.LogInDataAccessObject;
import data_access.SignUpDataAccessObject;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LogInInputBoundary;
import use_case.login.LogInInteractor;
import use_case.login.LogInOutputBoundary;
import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInteractor;
import use_case.signup.SignUpOutputBoundary;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        final JPanel cardPanel = new JPanel();
        final CardLayout cardLayout = new CardLayout();
        final UserFactory userFactory = new UserFactory();
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final KandoMongoDatabase dataAccessObject = new KandoMongoDatabase();
        SignupView signupView;
        LoginView loginView;
        SignupViewModel signupViewModel;
        LoginViewModel loginViewModel;
        ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

        // add sign up view
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        // add login view
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        // add signup use case
        final SignUpOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignUpInputBoundary userSignupInteractor = new SignUpInteractor(
                new SignUpDataAccessObject(dataAccessObject), signupOutputBoundary, userFactory);

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        //add login use case
//        final LogInOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
//                loggedInViewModel, loginViewModel);
//        final LogInInputBoundary loginInteractor = new LogInInteractor(
//                new LogInDataAccessObject(dataAccessObject), loginOutputBoundary);
//
//        LoginController loginController = new LoginController(loginInteractor);
//        loginView.setLoginController(loginController);

        //build
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
