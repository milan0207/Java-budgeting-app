import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {

    private final String hintText;

    public HintTextField(String hintText) {
        super(hintText);
        this.hintText = hintText;
        this.setForeground(Color.GRAY);

        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().equals(hintText)) {
            this.setText("");
            this.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            this.setText(hintText);
            this.setForeground(Color.GRAY);
        }
    }
}
