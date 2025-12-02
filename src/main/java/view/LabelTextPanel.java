package view;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * A simple panel that holds a label and a component side by side.
 *
 * @null This class does not accept null arguments for its constructor.
 */
public class LabelTextPanel extends JPanel {

    /**
     * Constructs a LabelTextPanel with the given label and component.
     *
     * @param label the JLabel to display (must not be null)
     * @param component the JComponent to display next to the label (must not be null)
     */
    LabelTextPanel(JLabel label, JComponent component) {
        this.add(label);
        this.add(component);
    }
}
