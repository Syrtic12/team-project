package adapters.login;

import adapters.ViewManagerModel;
import adapters.logged_in.LoggedInState;
import adapters.logged_in.LoggedInViewModel;
import adapters.signup.SignupViewModel;
import usecase.login.LogInOutputBoundary;
import usecase.login.LogInOutputData;

/**
 * The presenter for the login use case.
 */
public class LoginPresenter implements LogInOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel sigupViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.sigupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(LogInOutputData response) {
        // On success, update the loggedInViewModel's state
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setEmail(response.getEmail());
        loggedInState.setTeams(response.getTeams());
        loggedInState.setUserId(response.getIdx());
        this.loggedInViewModel.firePropertyChange();

        // and clear everything from the LoginViewModel's state
        loginViewModel.setState(new LoginState());
        // switch to the logged in view
        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setError(error);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(sigupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
