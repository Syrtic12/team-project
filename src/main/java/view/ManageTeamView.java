package view;

import interface_adapter.login.LoginState;
import interface_adapter.manage_team.ManageTeamController;
import interface_adapter.manage_team.ManageTeamState;
import interface_adapter.manage_team.ManageTeamViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ManageTeamView extends JPanel implements PropertyChangeListener {
    private final String viewName = "manage team";
    private final ManageTeamViewModel manageTeamViewModel;
    private final JTextField newMemberField = new  JTextField(20);
    private final JList<String> membersList;
    private final DefaultListModel<String> membersListModel;
    private ManageTeamController manageTeamController = null;
    private final JButton addButton;
    private final JButton removeButton;
    private final JButton backButton;
    private final JButton disbandButton;

    public ManageTeamView(ManageTeamViewModel manageTeamViewModel) {
        this.manageTeamViewModel = manageTeamViewModel;

        manageTeamViewModel.addPropertyChangeListener(this);

        final JLabel titleLabel = new JLabel(ManageTeamViewModel.TITLE_LABEL);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        final LabelTextPanel addMemberInfo = new LabelTextPanel(new JLabel(ManageTeamViewModel.ADD_MEMBER_LABEL), newMemberField);

        membersListModel = new DefaultListModel<>();
        membersList = new JList<>(membersListModel);
        JScrollPane listScrollPane = new JScrollPane(membersList);
        listScrollPane.setPreferredSize(new java.awt.Dimension(250, 200));
        final JPanel membersPanel = new JPanel();
        membersPanel.add(new JLabel(ManageTeamViewModel.MEMBERS_LABEL));
        membersPanel.add(listScrollPane);

        final JPanel buttons = new JPanel();
        addButton = new JButton(ManageTeamViewModel.ADD_BUTTON_LABEL);
        buttons.add(addButton);

        removeButton = new JButton(ManageTeamViewModel.REMOVE_BUTTON_LABEL);
        buttons.add(removeButton);

        backButton = new JButton(ManageTeamViewModel.BACK_BUTTON_LABEL);
        buttons.add(backButton);

        disbandButton = new JButton(ManageTeamViewModel.DISBAND_BUTTON_LABEL);
        buttons.add(disbandButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final ManageTeamState currentState = manageTeamViewModel.getState();
                manageTeamController.execute(currentState.getNewMemberEmail(), currentState.getTeamId(),"add");
                newMemberField.setText("");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMember = "";
                selectedMember = membersList.getSelectedValue();
                if (selectedMember != null) {
                    final ManageTeamState currentState = manageTeamViewModel.getState();
                    manageTeamController.execute(selectedMember, currentState.getTeamId(),"remove");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageTeamController.switchToTeamView();
            }
        });

        disbandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageTeamController.disbandTeam();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titleLabel);
        this.add(addMemberInfo);
        this.add(membersPanel);
        this.add(buttons);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ManageTeamState state = (ManageTeamState) evt.getNewValue();
        membersListModel.clear();
        for (String member: state.getMembers()) {
            membersListModel.addElement(member);
        }
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setManageTeamController(ManageTeamController manageTeamController) {
        this.manageTeamController = manageTeamController;
    }
}
