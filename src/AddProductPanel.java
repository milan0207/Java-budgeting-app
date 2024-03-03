import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;

public class AddProductPanel extends JPanel {
    private JButton backButton;
    private HintTextField productNameField;
    private HintTextField productPriceField;
    private HintTextField productDateField;
    private JButton addProductButton;
    private JComboBox<String> categorySelector;

    public AddProductPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45)); // Sötét háttérszín

        // Back gomb létrehozása és stílusának beállítása
        backButton = new JButton("Back");
        styleButton(backButton);
        add(backButton, BorderLayout.NORTH);

        // Középső panel az elemekhez
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(45, 45, 45));
        centerPanel.add(Box.createVerticalStrut(20)); // Térköz hozzáadása

        // Termék nevének mezője
        productNameField = new HintTextField("Product Name");
        styleTextField(productNameField);
        centerPanel.add(productNameField);

        // Termék árának mezője
        productPriceField = new HintTextField("Product Price");
        styleTextField(productPriceField);
        centerPanel.add(productPriceField);

        // Termék dátumának mezője
        productDateField = new HintTextField("Product Date (YYYY-MM-DD)");
        styleTextField(productDateField);
        centerPanel.add(productDateField);

        // Kategória választó
        String[] categories = {"Drinks", "Food", "Entertainment","Clothes","Electronics", "Other"};
        categorySelector = new JComboBox<>(categories);
        styleComboBox(categorySelector);
        centerPanel.add(categorySelector);

        // Termék hozzáadása gomb
        addProductButton = new JButton("Add Product");
        styleButton(addProductButton);
        addProductButton.addActionListener(this::buttonPressed);
        centerPanel.add(addProductButton);

        // Panelek és gombok méretezésének és elrendezésének beállítása
        setComponentAlignment(centerPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    // Komponensek méretezésének és elrendezésének beállítása
    private void setComponentAlignment(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            ((JComponent) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
            if (comp instanceof JTextField) {
                comp.setPreferredSize(new Dimension(300, 30));
            } else if (comp instanceof JButton) {
                comp.setPreferredSize(new Dimension(150, 35));
            }
        }
    }

    // Gombok stílusának beállítása
    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    // Szövegmezők stílusának beállítása
    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 25));
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setBackground(new Color(70, 70, 70));
        //textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
    }

    // ComboBox stílusának beállítása
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(new Color(70, 70, 70));
        comboBox.setForeground(Color.WHITE);
    }

    private void buttonPressed(ActionEvent e) {
        if (e.getSource() == addProductButton) {
            String name = productNameField.getText();
            String price = productPriceField.getText();
            String date = productDateField.getText();
            int correct_date = 0;

            // Dátum formátumának ellenőrzése
            try {
                String[] dateParts = date.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                if (dateParts.length != 3 || dateParts[0].length() != 4 || dateParts[1].length() != 2 || dateParts[2].length() != 2 || year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
                    JOptionPane.showMessageDialog(null, "The date format is not correct!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Megállítja a további végrehajtást
                }
                correct_date = 1;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "The date format is not correct!", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Megállítja a további végrehajtást
            }

            // Ár ellenőrzése
            try {
                double d = Double.parseDouble(price);
                String category = categorySelector.getSelectedItem().toString();
                if (correct_date == 1) {
                    writeToFile(name, price, category, date);
                }
                System.out.println(name);
                System.out.println(price);
                System.out.println(category);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "The price is not a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void writeToFile(String name, String price, String category,String date){
        try {
            FileWriter myWriter = new FileWriter("products.txt", true); // 'true' az adatok hozzáfűzéséhez
            myWriter.write(name + "$" + price + "$" + category + "$" +date+ System.lineSeparator()); // Adatok elválasztása '$' jellel
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (java.io.IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public JButton getBackButton() {
        return backButton;
    }
}
