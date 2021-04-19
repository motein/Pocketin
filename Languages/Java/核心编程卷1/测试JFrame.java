import java.awt.*;
import javax.swing.*;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class 测试JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame();
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;
            System.out.println("Width:" + screenWidth + ", Height:" + screenHeight);
            frame.setTitle("Welcome to JFrame");
            frame.setSize(screenWidth / 4, screenHeight / 4);
            frame.add(new HelloWorldComponent());
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}

class HelloWorldComponent extends JComponent {
    public static final int MSG_X = 75;
    public static final int MSG_Y = 100;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Font f = new Font("Serif", Font.BOLD, 12);
        g2.setFont(f);
        g2.drawString("Hello World !!!", this.MSG_X, this.MSG_Y);
        g2.setPaint(Color.RED);
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
