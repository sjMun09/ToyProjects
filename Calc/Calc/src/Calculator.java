import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Calculator extends JFrame {
    private JTextField inputSpace;
    // number
    private String number = "";
    private ArrayList<String> equationSign = new ArrayList<String>();

        public Calculator(){
            // add Button && UI
            setLayout(null);

            // instance
            inputSpace = new JTextField();
            // Use only Button
            inputSpace.setEditable(false);
            // BackGround setting
            inputSpace.setBackground(Color.white);
            // Sorting location
            inputSpace.setHorizontalAlignment(JTextField.RIGHT);
            // Font
            inputSpace.setFont(new Font("Arial", Font.BOLD, 50));
            // size && Location setting
            inputSpace.setBounds(8,10,305,75);


            //ButtonPanel
            JPanel buttonPanel = new JPanel();
            //Layout
            buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
            // location && size setting
            buttonPanel.setBounds(8, 90, 270, 235);

            // add
            String button_names[]={ "C", "÷", "×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" };
            // button array
            JButton buttons[] = new JButton[button_names.length];

            //  button create
            for (int i = 0; i < button_names.length; i++) {
                // setting
                buttons[i] = new JButton(button_names[i]);
                //Font
                buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
                //set color button
                if (button_names[i]=="C") {
                    buttons[i].setBackground(Color.RED);
                } else if ((i>=4 && i<=6)|| (i >= 8 && i <= 10) || (i >= 12 && i <= 14)) {
                    buttons[i].setBackground(Color.BLACK);
                }else buttons[i].setBackground(Color.LIGHT_GRAY);
                // font color
                buttons[i].setForeground(Color.BLUE);
                // NoOutSide
                buttons[i].setBorderPainted(false);
                // ActionListener add Button
                buttons[i].addActionListener(new PadActionListener());
                // add button in panel
                buttonPanel.add(buttons[i]);
            }

            add(inputSpace); // No output without this
            // because : Set only setter, not getter
            add(buttonPanel);

            // Title
            setTitle("Calculator");
            setVisible(true);
            setSize(320, 420);
            // centerFocusing
            setLocationRelativeTo(null);
            // final sizeSetting
            setResizable(false);
            // EXIT
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        class PadActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                // research button
                String operation = e.getActionCommand();

                // if 'C' clear
                if (operation.equals("C")) {
                    inputSpace.setText("");
                } else if (operation.equals("=")) {
                    String result = calculateEquation(inputSpace.getText());
                    inputSpace.setText("" + result);
                    number = "";
                    // add Calc
                } else {
                    inputSpace.setText(inputSpace.getText() + e.getActionCommand());
                }
            }
        }

    private String calculateEquation(String inputText) {
        TextParsing(inputText);
//
//        // Handle empty equation
//        if (equationSign.isEmpty()) {
//            return "";
//        }
//
//        double result = 0;
//        int currentIndex = 0;
//        int signCount = equationSign.size();
//        while (currentIndex < signCount) {
//            String sign = equationSign.get(currentIndex);
//            if (sign.equals("×") || sign.equals("÷")) {
//                // Handle multiplication and division
//                double num1 = Double.parseDouble(equationSign.get(currentIndex - 1));
//                double num2 = Double.parseDouble(equationSign.get(currentIndex + 1));
//                double partialResult = sign.equals("×") ? num1 * num2 : num1 / num2;
//                equationSign.set(currentIndex - 1, Double.toString(partialResult));
//                equationSign.remove(currentIndex);
//                equationSign.remove(currentIndex);
//                signCount -= 2;
//            } else {
//                currentIndex += 2;
//            }
//        }
//
//        // Calculate remaining addition and subtraction
//        for (int i = 1; i < signCount; i += 2) {
//            String sign = equationSign.get(i);
//            double num = Double.parseDouble(equationSign.get(i + 1));
//            if (sign.equals("+")) {
//                result += num;
//            } else if (sign.equals("-")) {
//                result -= num;
//            }
//        }
//
//        // Round the result to 6 decimal places
//        BigDecimal roundedResult = BigDecimal.valueOf(result).setScale(6, RoundingMode.HALF_UP);
//        return roundedResult.stripTrailingZeros().toPlainString();

        // Handle empty equation
        if (equationSign.isEmpty()) {
            return "";
        }
        double result = Double.parseDouble(equationSign.get(0));
        int currentIndex = 1;
        int signCount = equationSign.size();
        while (currentIndex < signCount) {
            String sign = equationSign.get(currentIndex);
            double num = Double.parseDouble(equationSign.get(currentIndex + 1));
            if (sign.equals("+")) {
                result += num;
            } else if (sign.equals("-")) {
                result -= num;
            } else if (sign.equals("×")) {
                result *= num;
            } else if (sign.equals("÷")) {
                result /= num;
            }
            currentIndex += 2;
        }

        // Round the result to 6 decimal places
        BigDecimal roundedResult = BigDecimal.valueOf(result).setScale(6, RoundingMode.HALF_UP);
        return roundedResult.stripTrailingZeros().toPlainString();
    }

    private void TextParsing(String inputText) {
        equationSign.clear();
        number = "";

        // each character in the calculation
        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);

            // if ALU, add ArrayList && clear arrayList
            if (ch == '-' || ch == '+' || ch == '×' || ch == '÷') {
                // if ALU, that's numbers
                // number add ArrayList
                equationSign.add(number);
                //number clear
                number = "";
                // add ALU in ArrayList
                equationSign.add(ch + "");
            } else {
                // Just numbering
                number = number + ch;
            }
        }
        // The remaining number add
        equationSign.add(number);
    }


        public static void main(String []args){
            // instance
            new Calculator();
        }
}
