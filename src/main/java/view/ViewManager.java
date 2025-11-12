package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;

    public ViewManager(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        
    }
}
