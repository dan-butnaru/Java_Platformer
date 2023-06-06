package main;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    private JFrame jframe;
    private  GraphicsDevice device;
    private GraphicsEnvironment graphics;
    public GameWindow(GamePanel gamePanel){
        jframe= new JFrame();
        graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = graphics.getDefaultScreenDevice();
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}
