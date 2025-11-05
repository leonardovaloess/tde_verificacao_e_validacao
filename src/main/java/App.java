import gui.MainFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Executar a interface gráfica na EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
