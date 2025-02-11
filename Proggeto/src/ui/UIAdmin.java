package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UIAdmin extends JFrame {
    private AdminFunctions adminFunctions;

    public UIAdmin() {
        adminFunctions = new AdminFunctions();

        setTitle("Admin - Lista de Usuarios");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultListModel<String> userModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        panel.add(scrollPane, BorderLayout.CENTER);

        List<String> users = adminFunctions.getAllUsers();
        for (String user : users) {
            userModel.addElement(user);
        }

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    new UIUserNotes(selectedUser);
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}
