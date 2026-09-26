package resqbridge;

import javax.swing.*;
import java.awt.*;

public class ResQBridgeApp extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel pages = new JPanel();

    public ResQBridgeApp(DataStore store, LoginAccount account) {
        super("ResQBridge - Disaster Relief Logistics System (" + account.role + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 900);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);

        getContentPane().setBackground(Theme.BG_APP);
        setLayout(new BorderLayout());

        Runnable logout = () -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        };

        HeaderPanel header = new HeaderPanel(account, logout);
        add(header, BorderLayout.NORTH);

        String[][] navItems;
        String initialPage;

        switch (account.role) {
            case "Driver":
                navItems = new String[][]{{"Driver Console", "TRUCK"}};
                initialPage = "Driver Console";
                break;
            case "Camp Employee":
                navItems = new String[][]{{"Camp Console", "CAMP"}};
                initialPage = "Camp Console";
                break;
            default: // Admin
                navItems = new String[][]{
                        {"Dashboard", "DASHBOARD"},
                        {"Relief Camps", "CAMP"},
                        {"Donors", "DONOR"},
                        {"Supply Inventory", "INVENTORY"},
                        {"Dispatch", "DISPATCH"},
                        {"Drivers", "DRIVER"},
                        {"Alerts", "ALERT"},
                        {"Messages", "BELL"},
                        {"Reports", "REPORT"},
                        {"Settings", "SETTINGS"},
                };
                initialPage = "Dashboard";
        }

        SidebarPanel sidebar = new SidebarPanel(navItems, initialPage, this::showPage);
        add(sidebar, BorderLayout.WEST);

        pages.setLayout(cardLayout);
        pages.setBackground(Theme.BG_APP);

        switch (account.role) {
            case "Driver":
                pages.add(wrap(new DriverConsolePanel(store, account, this)), "Driver Console");
                break;
            case "Camp Employee":
                pages.add(wrap(new CampConsolePanel(store, account, this)), "Camp Console");
                break;
            default: // Admin - full access to every feature
                pages.add(wrap(new DashboardPanel(store, this)), "Dashboard");
                pages.add(wrap(new CampsPanel(store)), "Relief Camps");
                pages.add(wrap(centered(new DonorsCardPanel(store, this, 0))), "Donors");
                pages.add(wrap(centered(new InventoryCardPanel(store))), "Supply Inventory");
                pages.add(wrap(centered(new DispatchCardPanel(store, this, 0))), "Dispatch");
                pages.add(wrap(new DriversPanel(store, this)), "Drivers");
                pages.add(wrap(new AlertsPanel(store)), "Alerts");
                pages.add(wrap(new AdminMessagesPanel(store)), "Messages");
                pages.add(wrap(new SimplePagePanel("Reports", "Detailed operational reports are generated at the end of each day. Check back soon.", AppIcon.Kind.REPORT)), "Reports");
                pages.add(wrap(new SettingsPanel(store, this)), "Settings");
        }

        add(pages, BorderLayout.CENTER);
        cardLayout.show(pages, initialPage);
    }

    private JPanel wrap(JComponent content) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BG_APP);
        wrapper.add(content, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel centered(JComponent content) {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        content.setPreferredSize(new Dimension(700, 560));
        outer.add(content, gbc);
        return outer;
    }

    private void showPage(String name) {
        cardLayout.show(pages, name);
    }
}
