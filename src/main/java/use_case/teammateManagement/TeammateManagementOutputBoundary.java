package use_case.teammateManagement;

/**
 * The output boundary for the teammateManagement Use Case.
 */
public interface TeammateManagementOutputBoundary {
    /**
     * Prepares the success view.
     * @param outputData the output data
     */
    void prepareSuccessView(TeammateManagementOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}