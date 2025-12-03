package usecase.login;

import dataaccess.KandoMongoDatabase;
import dataaccess.LogInDataAccessObject;
import dataaccess.SignUpDataAccessObject;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import usecase.signup.SignUpDataAccessInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class LoginInteractorTest {
    @Test
    void successTest() {
        LogInInputData inputData = new LogInInputData("test@gmail.com", "password");
        KandoMongoDatabase genDAO = new KandoMongoDatabase();
        LogInDataAccessInterface dataAccessObject = new LogInDataAccessObject(genDAO);
        SignUpDataAccessInterface userRepository = new SignUpDataAccessObject(genDAO);

        if (!userRepository.emailExists("test@gmail.com")) {
            UserFactory factory = new UserFactory();
            User user = factory.create("test","test@gmail.com", "password", "Member");
            userRepository.addUser(user);
        }

        LogInOutputBoundary successPresenter = new LogInOutputBoundary() {
            @Override
            public void prepareSuccessView(LogInOutputData user) {
                assertEquals("test@gmail.com", user.getEmail());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignupView() {}
        };

        LogInInputBoundary interactor = new LogInInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureWrongPasswordTest() {
        LogInInputData inputData = new LogInInputData("test@gmail.com", "password1");
        KandoMongoDatabase genDAO = new KandoMongoDatabase();
        LogInDataAccessInterface dataAccessObject = new LogInDataAccessObject(genDAO);
        SignUpDataAccessInterface userRepository = new SignUpDataAccessObject(genDAO);

        if (!userRepository.emailExists("test@gmail.com")) {
            UserFactory factory = new UserFactory();
            User user = factory.create("test","test@gmail.com", "password", "Member");
            userRepository.addUser(user);
        }

        LogInOutputBoundary successPresenter = new LogInOutputBoundary() {
            @Override
            public void prepareSuccessView(LogInOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password.", error);
            }

            @Override
            public void switchToSignupView() {}
        };

        LogInInputBoundary interactor = new LogInInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureNoAccountTest() {
        LogInInputData inputData = new LogInInputData("doesnotexist@gmail.com", "password1");
        KandoMongoDatabase genDAO = new KandoMongoDatabase();
        LogInDataAccessInterface dataAccessObject = new LogInDataAccessObject(genDAO);

        LogInOutputBoundary successPresenter = new LogInOutputBoundary() {
            @Override
            public void prepareSuccessView(LogInOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Account does not exist.", error);
            }

            @Override
            public void switchToSignupView() {}
        };

        LogInInputBoundary interactor = new LogInInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}
