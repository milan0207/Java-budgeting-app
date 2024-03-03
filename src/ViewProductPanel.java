import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewProductPanel extends JPanel {
    private JTable table;
    private JButton viewStatisticsButton;
    private JButton backButton;

    public ViewProductPanel() {
        setBackground(new Color(45, 45, 45)); // Sötét háttérszín
        setLayout(new BorderLayout());

        // Gombok létrehozása és hozzáadása
        viewStatisticsButton = new JButton("View Statistics");
        backButton = new JButton("Back");
        styleButton(viewStatisticsButton);
        styleButton(backButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(viewStatisticsButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Táblázat modell létrehozása és beállítása
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Name", "Price", "Category", "Date"});
        loadTableData(model);

        // Táblázat létrehozása
        table = new JTable(model);
        styleTable(table);

        // Táblázat hozzáadása a görgethető panelhez és a panelhez
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Eseménykezelők
        viewStatisticsButton.addActionListener(e -> showDiagram());
       // backButton.addActionListener(e -> goBack());
    }
    private void showDiagram() {
        Map<String, Double> monthlySpending = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            reader.lines().forEach(line -> {
                String[] data = line.split("\\$");
                if (data.length == 4) {
                    String date = data[3];
                    String month = date.substring(0, 7); // "YYYY-MM" formátum
                    double amount = Double.parseDouble(data[1]);
                    monthlySpending.put(month, monthlySpending.getOrDefault(month, 0.0) + amount);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        monthlySpending.forEach((month, amount) -> dataset.addValue(amount, "Spending", month));

        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Spending",
                "Month",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);

        JFrame diagramFrame = new JFrame("Spending by Month");
        diagramFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        diagramFrame.add(chartPanel);
        diagramFrame.setSize(800, 600);
        diagramFrame.setLocationRelativeTo(null);
        diagramFrame.setVisible(true);
    }
    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

        private void loadTableData(DefaultTableModel model) {
        try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\$");
                if (data.length == 4) {
                    model.addRow(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(70, 70, 70));
        table.setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("View Product Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setLocationRelativeTo(null);
        frame.add(new ViewProductPanel());
        frame.setVisible(true);
    }

    public AbstractButton getBackButton() {
        return backButton;
    }
}
