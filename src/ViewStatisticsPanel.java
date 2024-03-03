import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewStatisticsPanel extends JPanel {
    private JButton backButton;
    private JButton viewDiagramButton;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JTable table;

    public ViewStatisticsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45)); // Sötét háttérszín

        // Back gomb létrehozása és stílusának beállítása
        backButton = new JButton("Back");
        styleButton(backButton);

        // "View Diagram" gomb létrehozása és stílusának beállítása
        viewDiagramButton = new JButton("View Diagram");
        styleButton(viewDiagramButton);
        viewDiagramButton.addActionListener(e -> showDiagram());

        // Év és hónap választók létrehozása
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(45, 45, 45));
        topPanel.add(backButton);
        yearComboBox = new JComboBox<>(getYearOptions());
        monthComboBox = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        topPanel.add(yearComboBox);
        topPanel.add(monthComboBox);
        topPanel.add(viewDiagramButton);
        add(topPanel, BorderLayout.NORTH);

        // Táblázat modell létrehozása és beállítása
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Name", "Price", "Date", "Category"});
        table = new JTable(model);

        // Táblázat stílusának beállítása és hozzáadása
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Eseménykezelő a JComboBox-okhoz
        ActionListener comboBoxListener = e -> loadTableData(model, (String) yearComboBox.getSelectedItem(), (String) monthComboBox.getSelectedItem());
        yearComboBox.addActionListener(comboBoxListener);
        monthComboBox.addActionListener(comboBoxListener);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(70, 70, 70));
        table.setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
    }

    private void loadTableData(DefaultTableModel model, String selectedYear, String selectedMonth) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
                String monthIndex = String.format("%02d", monthStringToNumber(selectedMonth));
                String yearMonthPattern = selectedYear + "-" + monthIndex;

                List<String[]> filteredData = reader.lines()
                        .map(line -> line.split("\\$"))
                        .filter(data -> data.length == 4 && data[3].startsWith(yearMonthPattern))
                        .collect(Collectors.toList());

                SwingUtilities.invokeLater(() -> {
                    model.setRowCount(0); // Táblázat ürítése
                    filteredData.forEach(model::addRow); // Adatok hozzáadása
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private int monthStringToNumber(String month) {
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        return months.indexOf(month) + 1;
    }

    private String[] getYearOptions() {
        return new String[]{"2023", "2024", "2025"};
    }

    private void showDiagram() {
        String selectedYear = (String) yearComboBox.getSelectedItem();
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        String monthIndex = String.format("%02d", monthStringToNumber(selectedMonth));
        String yearMonthPattern = selectedYear + "-" + monthIndex;

        Map<String, Double> categorySpending = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            reader.lines().forEach(line -> {
                String[] data = line.split("\\$");
                if (data.length == 4 && data[3].startsWith(yearMonthPattern)) {
                    String category = data[2];
                    double amount = Double.parseDouble(data[1]);
                    categorySpending.put(category, categorySpending.getOrDefault(category, 0.0) + amount);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        categorySpending.forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Spending by Category in " + selectedMonth + " " + selectedYear,
                dataset,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame diagramFrame = new JFrame("Spending by Category");
        diagramFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        diagramFrame.add(chartPanel);
        diagramFrame.setSize(600, 400);
        diagramFrame.setLocationRelativeTo(null);
        diagramFrame.setVisible(true);
    }
    public JButton getBackButton() {
        return backButton;
    }

}
