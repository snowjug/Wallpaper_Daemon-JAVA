package wallpaperdaemon;

import java.awt.BorderLayout;
import java.sql.*;
import java.util.*;
import javax.swing.*;   // Swing components + SwingUtilities

import com.sun.jna.*;
import com.sun.jna.win32.W32APIOptions;

public class WallpaperDaemon {

    // === Windows API binding (user32.dll) ===
    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo(int uiAction, int uiParam, String pvParam, int fWinIni);
    }
    private static final int SPI_SETDESKWALLPAPER = 0x0014;
    private static final int SPIF_UPDATEINIFILE  = 0x01;
    private static final int SPIF_SENDCHANGE     = 0x02;

    private Connection conn;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private int timerInterval = 10; // minutes
    private java.util.Timer timer;
    private int currentIndex = 0;

    public WallpaperDaemon() {
        setupDatabase();
        setupGUI();
        startTimer();
    }

    private void setupDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:wallpapers.db");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS images (" +
                         "id INTEGER PRIMARY KEY, " +
                         "path TEXT, " +
                         "added_time DATETIME DEFAULT CURRENT_TIMESTAMP)");
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DB Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private void setupGUI() {
        JFrame frame = new JFrame("Wallpaper Daemon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        loadImagesFromDB();
        frame.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Image");
        JButton removeBtn = new JButton("Remove Selected");
        JLabel label = new JLabel("Change interval (min):");
        JTextField intervalField = new JTextField(Integer.toString(timerInterval), 4);

        JPanel controls = new JPanel();
        controls.add(addBtn);
        controls.add(removeBtn);
        controls.add(label);
        controls.add(intervalField);
        frame.add(controls, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int opt = chooser.showOpenDialog(frame);
            if (opt == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO images(path) VALUES(?)")) {
                    ps.setString(1, path);
                    ps.executeUpdate();
                    listModel.addElement(path);
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        });

        removeBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx >= 0) {
                String path = listModel.get(idx);
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM images WHERE path = ?")) {
                    ps.setString(1, path);
                    ps.executeUpdate();
                    listModel.remove(idx);
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        });

        intervalField.addActionListener(e -> {
            try {
                int minutes = Integer.parseInt(intervalField.getText());
                if (minutes > 0) {
                    timerInterval = minutes;
                    restartTimer();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid interval!");
            }
        });

        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadImagesFromDB() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT path FROM images ORDER BY id")) {
            listModel.clear();
            while (rs.next()) {
                listModel.addElement(rs.getString("path"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void startTimer() {
        if (timer != null) return;
        timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> changeWallpaper());
            }
        }, 0, timerInterval * 60 * 1000);
    }

    private void restartTimer() {
        if (timer != null) timer.cancel();
        timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> changeWallpaper());
            }
        }, 0, timerInterval * 60 * 1000);
    }

    private void changeWallpaper() {
        if (listModel.isEmpty()) return;
        if (currentIndex >= listModel.size()) currentIndex = 0;
        String path = listModel.get(currentIndex);
        User32.INSTANCE.SystemParametersInfo(
            SPI_SETDESKWALLPAPER, 0, path,
            SPIF_UPDATEINIFILE | SPIF_SENDCHANGE
        );
        currentIndex++;
    }

    public static void main(String[] args) {
    System.out.println("Starting Wallpaper Daemon...");
    SwingUtilities.invokeLater(WallpaperDaemon::new);
}

}
