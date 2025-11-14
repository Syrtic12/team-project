package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "log in";
    public final LoginViewModel loginViewModel;



    public LoginView(){

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
