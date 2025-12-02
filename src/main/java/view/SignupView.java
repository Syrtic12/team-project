package view;

import adapters.signup.SignupController;
import adapters.signup.SignupState;
import adapters.signup.SignupViewModel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * SignupView is the panel that displays the sign-up form.
 * It handles user input for username, email, password, and repeat password,
 * and communicates with the SignupController.
 *
 * @null This class does not accept null arguments in its constructor.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";
    private final SignupViewModel signupViewModel;
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);
    private final JLabel invalidEmailField = new JLabel();
    private final JLabel invalidPasswordField = new JLabel();
    private SignupController signupController;
    private final JButton signupButton;
    private final JButton cancelButton;
    private final JButton toLoginButton;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        final JLabel titleLabel = new JLabel(SignupViewModel.TITLE_LABEL);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameField);
        final LabelTextPanel emailInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.EMAIL_LABEL), emailField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordField);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordField);

        final JPanel buttons = new JPanel();
        toLoginButton = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(toLoginButton);
        signupButton = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signupButton);
        cancelButton = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        signupButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signupButton)) {
                            final SignupState currentState = signupViewModel.getState();
                            signupController.execute(
                                    currentState.getEmail(),
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );
        toLoginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();
                    }
                }
        );
        cancelButton.addActionListener(this);

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
        addEmailListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titleLabel);
        this.add(emailInfo);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    private DocumentListener createListener(Runnable update) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update.run();
            }
        };
    }

    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(
                createListener(() -> {
                    final SignupState state = signupViewModel.getState();
                    state.setUsername(usernameField.getText());
                    signupViewModel.setState(state);
                })
        );
    }

    private void addEmailListener() {
        emailField.getDocument().addDocumentListener(
                createListener(() -> {
                    final SignupState state = signupViewModel.getState();
                    state.setEmail(emailField.getText());
                    signupViewModel.setState(state);
                })
        );
    }

    private void addPasswordListener() {
        passwordField.getDocument().addDocumentListener(
                createListener(() -> {
                    final SignupState state = signupViewModel.getState();
                    state.setPassword(new String(passwordField.getPassword()));
                    signupViewModel.setState(state);
                })
        );
    }

    private void addRepeatPasswordListener() {
        repeatPasswordField.getDocument().addDocumentListener(
                createListener(() -> {
                    final SignupState state = signupViewModel.getState();
                    state.setRepeatPassword(new String(repeatPasswordField.getPassword()));
                    signupViewModel.setState(state);
                })
        );
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        signupController.switchToLoginView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }

}
