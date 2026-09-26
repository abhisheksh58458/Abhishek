package resqbridge;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    private final JLabel dot = new JLabel();
    private Timer blinkTimer;

    public HeaderPanel(LoginAccount account, Runnable onLogout) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_PANEL);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)));

        // Left: logo mark + titles
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        JLabel logo = new JLabel(new AppIcon(AppIcon.Kind.TENT, 28, Theme.ACCENT_CYAN));
        left.add(logo);
        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("ResQBridge");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.ACCENT_CYAN);
        JLabel subtitle = new JLabel("Disaster Relief Logistics System");
        subtitle.setFont(Theme.FONT_SMALL);
        subtitle.setForeground(Theme.TEXT_MUTED);
        titles.add(title);
        titles.add(subtitle);
        left.add(titles);
        add(left, BorderLayout.WEST);

        // Center: live tracking pill
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        center.setOpaque(false);
        dot.setOpaque(true);
        dot.setBackground(Theme.GREEN);
        dot.setPreferredSize(new Dimension(8, 8));
        dot.setMaximumSize(new Dimension(8, 8));
        JPanel dotWrap = new JPanel(new GridBagLayout());
        dotWrap.setOpaque(false);
        dotWrap.setPreferredSize(new Dimension(10, 20));
        dotWrap.add(roundDot());
        center.add(dotWrap);
        JLabel liveLabel = new JLabel("Live Tracking Active");
        liveLabel.setFont(Theme.FONT_BASE);
        liveLabel.setForeground(Theme.TEXT_PRIMARY);
        center.add(liveLabel);
        add(center, BorderLayout.CENTER);

        // Right: signed-in user + logout
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JPanel userCol = new JPanel();
        userCol.setOpaque(false);
        userCol.setLayout(new BoxLayout(userCol, BoxLayout.Y_AXIS));
        JLabel name = new JLabel(account.displayName);
        name.setFont(Theme.FONT_BOLD);
        name.setForeground(Theme.TEXT_PRIMARY);
        name.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel role = new JLabel(account.role);
        role.setFont(Theme.FONT_SMALL);
        role.setForeground(Theme.TEXT_MUTED);
        role.setAlignmentX(Component.RIGHT_ALIGNMENT);
        userCol.add(name);
        userCol.add(role);

        JLabel avatar = new JLabel(new AppIcon(AppIcon.Kind.PERSON, 24, Theme.TEXT_MUTED));

        JButton logout = new JButton("Logout");
        logout.setFont(Theme.FONT_SMALL);
        logout.setForeground(Theme.ACCENT_CYAN);
        logout.setBackground(Theme.BG_CARD_ALT);
        logout.setFocusPainted(false);
        logout.setBorder(BorderFactory.createLineBorder(Theme.BORDER_LIGHT));
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> onLogout.run());

        right.add(avatar);
        right.add(userCol);
        right.add(logout);
        add(right, BorderLayout.EAST);

        blinkTimer = new Timer(700, e -> {
            dot.setVisible(!dot.isVisible());
        });
        blinkTimer.start();
    }

    private JComponent roundDot() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.GREEN);
                g2.fillOval(0, 0, 9, 9);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(9, 9));
        return p;
    }
}
