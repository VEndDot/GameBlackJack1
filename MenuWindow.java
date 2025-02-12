import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MenuWindow extends JFrame {
    private static final File[] SOUND_FILE = {
            new File("src/music/somnitelno-no-okej.wav"),
            new File("src/music/asd.wav"),
            new File("src/music/magic.wav"),
    };

    private static final int WIDTHT = 1280;
    private static final int HEIGHTT = 1020;

    public MenuWindow() {

        MusicGame startMusic = new MusicGame();

        //startMusic.playMusic(SOUND_FILE[1], -1);

        setTitle("МЕНЮ");
        setSize(WIDTHT, HEIGHTT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startGameButton = new JButton("Начать игру");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMusic.playMusic(SOUND_FILE[0],0);

                new GameWindow(MenuWindow.this);
                setVisible(false);
            }
        });

        JButton exitGameButton = new JButton("Выйти из игры");
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panelMenu = new JPanel();

        panelMenu.setBackground(Color.red);

        add(new JLabel(new ImageIcon("src/menuStart/StartMenu.png")));
        settings(startGameButton);
        settings(exitGameButton);
        panelMenu.add(startGameButton);
        panelMenu.add(exitGameButton);
        add(BorderLayout.SOUTH, panelMenu);

        setVisible(true);
    }


    public void settings(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.red);
    }
}