package interface_adapter.login;

import use_case.login.LogInInputBoundary;
import use_case.login.LogInInputData;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void controllerCallsInteractorWithInputData() throws Exception {
        final AtomicReference<LogInInputData> received = new AtomicReference<>();

        LogInInputBoundary fakeInteractor = new LogInInputBoundary() {
            @Override
            public void execute(LogInInputData loginInputData) {
                received.set(loginInputData);
            }
        };

        final LoginController controller = new LoginController(fakeInteractor);
        controller.execute("me@example.com", "secret");

        final LogInInputData data = received.get();
        assertNotNull(data, "Interactor should have been called with LogInInputData");

        // LogInInputData has package-private getters; use reflection to read them
        final java.lang.reflect.Method getEmail = LogInInputData.class.getDeclaredMethod("getEmail");
        getEmail.setAccessible(true);
        final String email = (String) getEmail.invoke(data);

        final java.lang.reflect.Method getPassword = LogInInputData.class.getDeclaredMethod("getPassword");
        getPassword.setAccessible(true);
        final String password = (String) getPassword.invoke(data);

        assertEquals("me@example.com", email);
        assertEquals("secret", password);
    }
}
