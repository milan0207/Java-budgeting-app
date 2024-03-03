import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private JButton addProduct = new JButton("Add Product");
    private JButton viewProduct = new JButton("View Products");
    private JButton viewStatistics = new JButton("View Statistics");

    public MainPanel() {
        super();
        setLayout(new GridLayout(3, 1)); // 3 sor, 1 oszlop
        setBackground(new Color(48, 52, 67));

        // Stílusok beállítása
        styleButton(addProduct);
        styleButton(viewProduct);
        styleButton(viewStatistics);

        // Panelek létrehozása és hozzáadása
        add(createPanel(addProduct));
        add(createPanel(viewProduct));
        add(createPanel(viewStatistics));
    }

    // Segédfüggvény a gombok stílusának beállításához
    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 70, 70));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    // Segédfüggvény egy panel létrehozásához egy gombbal
    private JPanel createPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(48, 52, 67));
        panel.setLayout(new FlowLayout());
        panel.add(button);
        return panel;
    }
    public JButton getAddProduct() {
        return addProduct;
    }

    public JButton getViewProduct() {
        return viewProduct;
    }

    public JButton getViewStatistics() {
        return viewStatistics;
    }

}
