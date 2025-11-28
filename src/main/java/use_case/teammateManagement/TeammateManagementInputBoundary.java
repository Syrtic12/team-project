package use_case.teammateManagement;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface TeammateManagementInputBoundary {

    /**
     * Executes the login use case.
     * @param teammateManagementInputData the input data
     */
    void execute(TeammateManagementInputData teammateManagementInputData);
    void switchToTeamView();
}