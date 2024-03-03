import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private AddProductPanel addProductPanel;
    private ViewStatisticsPanel viewStatisticsPanel;
    private  ViewProductPanel viewProductPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainFrame() {
        super("Product Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 800);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        mainPanel = new MainPanel();
        addProductPanel = new AddProductPanel();
        viewStatisticsPanel = new ViewStatisticsPanel();
        viewProductPanel = new ViewProductPanel();

        cardPanel.add(mainPanel, "MainPanel");
        cardPanel.add(addProductPanel, "AddProductPanel");
        cardPanel.add(viewStatisticsPanel, "ViewStatisticsPanel");
        cardPanel.add(viewProductPanel, "ViewProductPanel");

        //mainPanel.getAddProduct().addActionListener(e -> cardLayout.show(cardPanel, "AddProductPanel"));
        mainPanel.getAddProduct().addActionListener(e -> {
            playSound("click2.wav");
            cardLayout.show(cardPanel, "AddProductPanel");
        });
        mainPanel.getViewProduct().addActionListener(e -> {
            playSound("click2.wav");
            cardLayout.show(cardPanel, "ViewProductPanel");
        });
        mainPanel.getViewStatistics().addActionListener(e -> {
            playSound("click2.wav");
            cardLayout.show(cardPanel, "ViewStatisticsPanel");
        });

        addProductPanel.getBackButton().addActionListener(e -> {
            playSound("click2.wav");
            cardLayout.show(cardPanel, "MainPanel");
        });
        viewProductPanel.getBackButton().addActionListener(e -> {
            playSound("click2.wav");
            cardLayout.show(cardPanel, "MainPanel");
        });
        viewStatisticsPanel.getBackButton().addActionListener(e -> {
            playSound("click2.wav");
            cardLayout.show(cardPanel, "MainPanel");
        });


        add(cardPanel);
        playBackgroundMusic("music2.wav");
        setVisible(true);
    }
    public void playSound(String soundFileName) {
        try {
            // A hangfájl betöltése
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Clip létrehozása és nyitása
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Hang lejátszása
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    private void playBackgroundMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Nem található a zenefájl");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
