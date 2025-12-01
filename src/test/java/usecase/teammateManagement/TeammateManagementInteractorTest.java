package usecase.teammateManagement;

import dataaccess.KandoMongoDatabase;
import dataaccess.TeammateManagementDataAccessObject;
import org.junit.jupiter.api.Test;
import usecase.teammate_management.*;

import static org.junit.jupiter.api.Assertions.*;

class TeammateManagementInteractorTest {

    @Test
    void addTest() {
        TeammateManagementInputData inputData = new TeammateManagementInputData("DH", "691e33af54f5b339af39ebde", "add");
        TeammateManagementDataAccessInterface dataRepository = new TeammateManagementDataAccessObject(new KandoMongoDatabase());

        // This creates a successPresenter that tests whether the test case is as we expect.
        TeammateManagementOutputBoundary successPresenter = new TeammateManagementOutputBoundary() {
            @Override
            public void prepareSuccessView(TeammateManagementOutputData user) {
                // checks if user is now in the team by ID
                System.out.println(dataRepository.getTeamMembers(dataRepository.getTeam("691e33af54f5b339af39ebde")));
                assertTrue(dataRepository.getTeamMembers(dataRepository.getTeam("691e33af54f5b339af39ebde")).contains("692520a2d4205f772fa888c3"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToTeamView() {
                //This doesn't do anything
            }

        };

        TeammateManagementInputBoundary interactor = new TeammateManagementInteractor(dataRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void removeTest() {
        TeammateManagementInputData inputData = new TeammateManagementInputData("DH", "691e33af54f5b339af39ebde", "remove");
        TeammateManagementDataAccessInterface dataRepository = new TeammateManagementDataAccessObject(new KandoMongoDatabase());

        // This creates a successPresenter that tests whether the test case is as we expect.
        TeammateManagementOutputBoundary successPresenter = new TeammateManagementOutputBoundary() {
            @Override
            public void prepareSuccessView(TeammateManagementOutputData user) {
                // checks if user is now in the team by ID
                System.out.println(dataRepository.getTeamMembers(dataRepository.getTeam("691e33af54f5b339af39ebde")));
                assertFalse(dataRepository.getTeamMembers(dataRepository.getTeam("691e33af54f5b339af39ebde")).contains("692520a2d4205f772fa888c3"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToTeamView() {
                //This doesn't do anything
            }
        };

        TeammateManagementInputBoundary interactor = new TeammateManagementInteractor(dataRepository, successPresenter);
        interactor.execute(inputData);
    }
    }

