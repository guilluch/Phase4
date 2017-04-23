import javax.swing.*;
import java.awt.*;

public class PleaseWaitDialog extends JFrame {

    public PleaseWaitDialog(String title) {
        setMinimumSize(new Dimension(200, 100));
        setTitle(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel pleaseWaitLabel = new JLabel("Veuillez patienter...", SwingConstants.CENTER);
        add(pleaseWaitLabel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }
}
