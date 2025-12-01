package usecase.signup;

import dataaccess.KandoMongoDatabase;
import dataaccess.SignUpDataAccessObject;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpInteractorTest {

    @Test
    void successTest() {
        SignUpInputData inputData = new SignUpInputData("test@gmail.com", "test", "password", "password");
        SignUpDataAccessInterface userRepository = new SignUpDataAccessObject(new KandoMongoDatabase());

        // This creates a successPresenter that tests whether the test case is as we expect.
        SignUpOutputBoundary successPresenter = new SignUpOutputBoundary() {
            @Override
            public void prepareSuccessView(SignUpOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("test@gmail.com", user.getEmail());
                assertTrue(userRepository.emailExists("test@gmail.com"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignUpInputBoundary interactor = new SignUpInteractor(userRepository, successPresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        SignUpInputData inputData = new SignUpInputData("test@gmail.com", "test", "password", "password2");
        SignUpDataAccessInterface userRepository = new SignUpDataAccessObject(new KandoMongoDatabase());

        // This creates a presenter that tests whether the test case is as we expect.
        SignUpOutputBoundary failurePresenter = new SignUpOutputBoundary() {
            @Override
            public void prepareSuccessView(SignUpOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords do not match", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignUpInputBoundary interactor = new SignUpInteractor(userRepository, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() {
        SignUpInputData inputData = new SignUpInputData("test@gmail.com", "test", "password", "password");
        SignUpDataAccessInterface userRepository = new SignUpDataAccessObject(new KandoMongoDatabase());

        UserFactory factory = new UserFactory();
        User user = factory.create("test","test@gmail.com", "password", "Member");
        userRepository.addUser(user);

        // This creates a presenter that tests whether the test case is as we expect.
        SignUpOutputBoundary failurePresenter = new SignUpOutputBoundary() {
            @Override
            public void prepareSuccessView(SignUpOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Email already exists", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignUpInputBoundary interactor = new SignUpInteractor(userRepository, failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }
}