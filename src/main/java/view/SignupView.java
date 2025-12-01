package view;

import adapters.signup.SignupController;
import adapters.signup.SignupState;
import adapters.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";
    private final SignupViewModel signupViewModel;
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);
    private final JLabel invalidEmailField = new JLabel();
    private final JLabel invalidPasswordField = new JLabel();
    private SignupController signupController = null;
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


    private void addUsernameListener(){
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameField.getText());
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

    }

    private void addEmailListener(){
        emailField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setEmail(emailField.getText());
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

    }

    private void addPasswordListener(){
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

    }

    private void addRepeatPasswordListener(){
        repeatPasswordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordField.getPassword()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent evt){
        signupController.switchToLoginView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller){
        this.signupController = controller;
    }

}
