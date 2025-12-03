package view;

import adapters.ViewManagerModel;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Manages switching between views using a CardLayout.
 */
public class ViewManager implements PropertyChangeListener {
    private final ViewManagerModel viewManagerModel;
    private final CardLayout cardLayout;
    private final JPanel views;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.views = views;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            cardLayout.show(views, viewModelName);
        }
    }
}
