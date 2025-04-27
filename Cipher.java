import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Cipher {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Mystery Cipher: The Enigma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setResizable(false);

        // Set the background color of the frame to a white theme
        frame.getContentPane().setBackground(Color.WHITE);

        // Create and set up components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);  // Matching background color

        // Font settings
        Font labelFont = new Font("Serif", Font.BOLD, 14);
        Font textFont = new Font("Serif", Font.PLAIN, 16);

        // Add labels, input fields, and button to the panel
        JLabel inputTextLabel = new JLabel("Enter text to process:");
        inputTextLabel.setFont(labelFont);
        JTextArea inputTextArea = new JTextArea(4, 30);
        inputTextArea.setFont(textFont);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setBackground(Color.WHITE);  // White background for text input
        inputTextArea.setForeground(Color.BLACK);  // Black text for readability

        JLabel shiftKeyLabel = new JLabel("Enter shift key (integer):");
        shiftKeyLabel.setFont(labelFont);
        JTextField shiftKeyField = new JTextField(10);
        shiftKeyField.setFont(textFont);
        shiftKeyField.setBackground(Color.WHITE);  // White background for text input
        shiftKeyField.setForeground(Color.BLACK);  // Black text for readability

        JButton actionButton = new JButton("Process Text");
        actionButton.setFont(labelFont);
        actionButton.setBackground(new Color(0, 153, 255));  // Blue button color
        actionButton.setForeground(Color.WHITE);

        JLabel resultLabel = new JLabel("Encrypted/Decrypted Text:");
        resultLabel.setFont(labelFont);
        JTextArea resultTextArea = new JTextArea(4, 30);
        resultTextArea.setFont(textFont);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setBackground(Color.WHITE);  // White background
        resultTextArea.setForeground(Color.BLACK);  // Black text
        resultTextArea.setEditable(false);  // Make the result area non-editable

        // Arrange the components on the panel using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);  // Add some space between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(inputTextLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(new JScrollPane(inputTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(shiftKeyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(shiftKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(actionButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(resultLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(new JScrollPane(resultTextArea), gbc);

        // Add panel to the frame
        frame.add(panel);
        frame.setVisible(true);

        // Ask user for action (encryption or decryption)
        String[] options = {"Encrypt", "Decrypt"};
        int choice = JOptionPane.showOptionDialog(frame, "Would you like to Encrypt or Decrypt the text?", "Select Action",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Action listener for the button
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                String shiftKeyText = shiftKeyField.getText();

                if (inputText.isEmpty() || shiftKeyText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "⚠️ Please fill in both fields.");
                    return;
                }

                try {
                    int shiftKey = Integer.parseInt(shiftKeyText);
                    String processedText;
                    if (choice == 0) {
                        processedText = encrypt(inputText, shiftKey);  // Perform encryption
                        resultLabel.setText("Encrypted Text:");
                    } else {
                        processedText = decrypt(inputText, shiftKey);  // Perform decryption
                        resultLabel.setText("Decrypted Text:");
                    }
                    resultTextArea.setText(processedText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "⚠️ Please enter a valid integer for the shift key.");
                }
            }
        });

        // Move focus to the next field when the user presses Enter
        inputTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    shiftKeyField.requestFocus();  // Move focus to shift key field
                }
            }
        });

        shiftKeyField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionButton.doClick();  // Automatically click the action button when Enter is pressed
                }
            }
        });
    }

    // Caesar cipher encryption method
    private static String encrypt(String text, int shiftKey) {
        StringBuilder result = new StringBuilder();

        // Iterate through each character in the text
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Encrypt only alphabetic characters
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char shiftedChar = (char) (base + (c - base + shiftKey + 26) % 26);
                result.append(shiftedChar);
            } else {
                result.append(c);  // Non-alphabetic characters remain unchanged
            }
        }

        return result.toString();
    }

    // Caesar cipher decryption method
    private static String decrypt(String text, int shiftKey) {
        StringBuilder result = new StringBuilder();

        // Iterate through each character in the text
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Decrypt only alphabetic characters
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char shiftedChar = (char) (base + (c - base - shiftKey + 26) % 26);
                result.append(shiftedChar);
            } else {
                result.append(c);  // Non-alphabetic characters remain unchanged
            }
        }

        return result.toString();
    }
}
