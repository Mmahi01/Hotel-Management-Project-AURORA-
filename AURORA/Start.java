import Classes.U_log;
import javax.swing.*;


public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            U_log uLog = new U_log("default_user");
            uLog.setVisible(true);
            });
        };
    }

