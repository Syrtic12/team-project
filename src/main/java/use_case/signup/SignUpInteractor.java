package use_case.signup;

import data_access.KandoMongoDatabase;
import data_access.SignUpDataAccessObject;
import entity.User;
import entity.UserFactory;

public class SignUpInteractor implements SignUpInputBoundary{
    private final SignUpDataAccessInterface userDataAccessObject;
    private final SignUpOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignUpInteractor(SignUpDataAccessInterface userDataAccessObject,
                            SignUpOutputBoundary userPresenter,
                            UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignUpInputData signupInputData){
        String name = signupInputData.getName();
        String role = signupInputData.getRole();
        String email = signupInputData.getEmail();
        String password = signupInputData.getPassword();
        String repeatPassword = signupInputData.getRepeatPassword();
        if (userDataAccessObject.emailExists(signupInputData.getEmail())){
            userPresenter.prepareFailView("Email already exists");
        } else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords do not match");
        } else if (password.isEmpty()) {
            userPresenter.prepareFailView("Password cannot be empty");
        } else if (name.isEmpty()) {
            userPresenter.prepareFailView("Name cannot be empty");
        } else if (email.isEmpty()){
            userPresenter.prepareFailView("Email cannot be empty");
        } else {
            User newUser = userFactory.create(name, email, password, role);
            User addedUser = userDataAccessObject.addUser(newUser);
            SignUpOutputData signupOutputData = new SignUpOutputData(addedUser.getIdx(), addedUser.getEmail());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }

    public static void main(String[] args) {
        SignUpInteractor interac = new SignUpInteractor(new SignUpDataAccessObject( new KandoMongoDatabase()), null, new UserFactory());
        SignUpInputData data = new SignUpInputData("sushaanpatel@gmail.com", "Sushaan Patel", "Member", "password", "password");
        interac.execute(data);
    }


}
