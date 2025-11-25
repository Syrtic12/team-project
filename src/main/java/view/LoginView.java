package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "login";
    private final LoginViewModel loginViewModel;

    private final JTextField emailField = new JTextField(20);
    private final JLabel emailErrorField = new JLabel();

    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton loginButton;
    private final JButton cancel;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel){
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Login");
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        final LabelTextPanel emailInfo = new LabelTextPanel(
                new JLabel("Email"), emailField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordField);

        final JPanel buttons = new JPanel();
        loginButton = new JButton("Log in");
        buttons.add(loginButton);
        cancel = new JButton("Create An Account");
        buttons.add(cancel);

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loginButton)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getEmail(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginController.switchToSignupView();
            }
        });

        emailField.getDocument().addDocumentListener(new DocumentListener() {

            public void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setEmail(emailField.getText());
                loginViewModel.setState(currentState);
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
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            public void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(passwordField.getText());
                loginViewModel.setState(currentState);
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
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(emailInfo);
        this.add(emailErrorField);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
    }

    public void actionPerformed(ActionEvent evt){
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        final LoginState state = (LoginState) evt.getNewValue();
//        setFields(state);
        System.out.println(state.getError());
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }

    private void setFields(LoginState state) {
        emailField.setText(state.getEmail());
        passwordField.setText(state.getPassword());
    }

    public String getViewName(){
        return viewName;
    }

    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }
}
