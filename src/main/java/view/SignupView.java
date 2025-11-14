package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final SignupViewModel signupViewModel;
    private final String viewName = "sign up";

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;

    }


    private void addUsernameListener(){

    }

    private void addPasswordListener(){

    }

    private void addRepeatPasswordListener(){
    }

    @Override
    public void actionPerformed(ActionEvent evt){

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){

    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller){

    }

}
