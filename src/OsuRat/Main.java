package OsuRat;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class Main extends JFrame implements NativeKeyListener{

    static JButton button;
    private static int keyOneIndex;
    private static int keyTwoIndex;
    private static boolean keysSetOne;
    private static boolean keysSetTwo;
    boolean rightButton = false;
    boolean leftButton = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,608);
        frame.setUndecorated(true);

        keyDefine();

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Main());

        // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);

        button = new JButton();
        button.setSize(new Dimension(50, 50));
        button.setLocation(500, 350);
        button.setIcon(new ImageIcon("src/OsuRat/RatHit.png"));
        button.addActionListener(e -> {
            keyDefine();
        });
        frame.getContentPane().add(button);

        frame.setVisible(true);
        button.setIcon(new ImageIcon("src/OsuRat/RatUp.png"));
    }

    private static void keyDefine() {
        keysSetOne = false;
        keysSetTwo = false;
        JOptionPane.showMessageDialog(button, "After clicking OK, please set the two buttons you use in OSU! by pressing them.");
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!keysSetOne) {
            keyOneIndex = e.getKeyCode();
            keysSetOne = true;
        } else if (!keysSetTwo) {
            keyTwoIndex = e.getKeyCode();
            keysSetTwo = true;
        }

        int keypunched = e.getKeyCode();
        if (keypunched == keyOneIndex) {
            rightButton = true;
        } else if (keypunched == keyTwoIndex) {
            leftButton = true;
        }
        checkButtonState();

        System.out.println(keypunched);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        int keypunched = e.getKeyCode();
        if (keypunched == keyOneIndex) {
            rightButton = false;
        } else if (keypunched == keyTwoIndex) {
            leftButton = false;
        }
        checkButtonState();
    }

    void checkButtonState(){
        if (rightButton && leftButton) {
            button.setIcon(new ImageIcon("src/OsuRat/RatHit.png"));
        } else if (rightButton &! leftButton) {
            button.setIcon(new ImageIcon("src/OsuRat/RatHitRight.png"));
        } else if (!rightButton && leftButton) {
            button.setIcon(new ImageIcon("src/OsuRat/RatHitLeft.png"));
        } else {
            button.setIcon(new ImageIcon("src/OsuRat/RatUp.png"));
        }

    }
}