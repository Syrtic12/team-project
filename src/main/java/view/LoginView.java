package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField emailField = new JTextField();
    private final JLabel emailErrorField = new JLabel();

    private final JPasswordField passwordField = new JPasswordField();
    private final JLabel passwordErrorField = new JLabel();

    private final JButton loginButton;
    private final JButton cancel;

    public LoginView(LoginViewModel loginViewModel){
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Login");
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        final LabelTextPanel emailInfo = new LabelTextPanel(
                new JLabel("Email"), emailField);


    }

    public void actionPerformed(ActionEvent e){

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){

    }

    public String getViewName(){
        return viewName;
    }

    public void setLoginController(LoginController loginController){

    }
}
