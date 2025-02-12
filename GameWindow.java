import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;


class GameWindow extends JFrame{
    File[] soundFile = {
            new File("src/music/sarkazm.wav"),
            new File("src/music/money.wav"),
            new File("src/music/kto-daet-tebe-rabotu.wav"),
            new File("src/music/avada.wav"),
            new File("src/music/cg.wav"),
            };
    private final int CARD_WIDTH = 145;
    private final int CARD_HEIGHT = 180;
    private static final int WIDTHT = 1280;
    private static final int HEIGHTT = 1020;

    JButton backButton = new JButton("Назад в меню");
    JButton newGameButton = new JButton("Давай еще раз сыграем");
    JButton BUTTON_STOP = new JButton("ПОКАЖИ СВОИ");
    JButton BUTTON_GIVE_CARD = new JButton("ЕЩЕ КАРТУ");

    GameWindow(MenuWindow menuWindow){
        setTitle("Двадцать одно");
        setSize(WIDTHT, HEIGHTT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MusicGame mg = new MusicGame();
        JPanel buttonPanel = new JPanel();

        // блок создания карт и рук
        Deck newDeck = new Deck();
        ArrayList<Card> deck = newDeck.createDeck();
        newDeck.shuffleDeck();

        Hand dealerHand = new Hand(2,deck, "DEALER");
        Hand playerHand = new Hand(2,deck, "PLAYER");

        JPanel panelGame = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // 1. выводит на экран скрытую карту
                    Image hiddenCardImg = new ImageIcon(getClass().getResource("./card/BACK.png")).getImage();

                    // 2. отображаем информацию на скрытой карте(перевернем ее)
                    if (!BUTTON_STOP.isEnabled()){
                        hiddenCardImg = new ImageIcon(
                                getClass().getResource(dealerHand.getHiddenCartDealer().imagePath())).getImage();
                    }
                    g.drawImage(hiddenCardImg, 35, 35, CARD_WIDTH, CARD_HEIGHT, null);
                    // 3. выводит на экран карты в руке дилера и игрока
                    displayCardOnTheScreen(playerHand, dealerHand, g, CARD_WIDTH, CARD_HEIGHT, mg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        panelGame.setLayout(new BorderLayout());
        panelGame.setBackground(new Color(45, 86, 52));
        add(panelGame);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuWindow.setVisible(true);
                dispose();
            }
        });

        BUTTON_GIVE_CARD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mg.playMusic(soundFile[3], 0);
                Card newCard = deck.remove(deck.size() - 1);
                playerHand.setNewSum(newCard.checkingСardType());
                playerHand.newCountAce(newCard);
                playerHand.getHandDealerOrPlayer().add(newCard);
                if (playerHand.reducePlayerAce() > 21) {
                    BUTTON_GIVE_CARD.setEnabled(false);
                }
                repaint();
            }
        });
        BUTTON_STOP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BUTTON_GIVE_CARD.setEnabled(false);
                BUTTON_STOP.setEnabled(false);

                while (dealerHand.getSumPoints() < 16){
                    Card newCard = deck.remove(deck.size() - 1);
                    dealerHand.setNewSum(newCard.checkingСardType());
                    dealerHand.newCountAce(newCard);
                    dealerHand.getHandDealerOrPlayer().add(newCard);
                }
                repaint();
            }
        });
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameWindow(menuWindow);
            }
        });
        buttonPanel.setBackground(Color.red);
        buttonPanel.add(backButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(BUTTON_STOP);
        buttonPanel.add(BUTTON_GIVE_CARD);
        settings(backButton);
        settings(newGameButton);
        settings(BUTTON_GIVE_CARD);
        settings(BUTTON_STOP);

        add(BorderLayout.SOUTH,buttonPanel);
        setVisible(true);
    }
    public void displayCardOnTheScreen(Hand player, Hand dealer, Graphics g, int cardW, int cardH, MusicGame mg) {
        // вывод на экран карты в руке дилера
        dealerHandOfTheScreen(dealer, g, cardW, cardH);
        // 3. вывод на экран карты в руке игрока
        playerHandOfTheScreen(player, g, cardW, cardH);

        if (!BUTTON_STOP.isEnabled()){
            String msg = " ";
            if (player.getSumPoints() > 21){
                msg = "ПРОИГРАЛ";
                mg.playMusic(soundFile[2], 0);
            }
            else if (dealer.getSumPoints() > 21 ) {
                mg.playMusic(soundFile[4], 0);
                msg = "Дилер набрал больше 21 ТЫ ПОБЕДИЛ";
            }
            else if (player.getSumPoints() == dealer.getSumPoints()) {msg = "У ВАС НИЧЬЯ";}
            else if (player.getSumPoints() > dealer.getSumPoints()){
                msg = "ПОБЕДА";
                mg.playMusic(soundFile[1], 0);
            }
            else if (player.getSumPoints() < dealer.getSumPoints()) {
                msg = "ПРОИГРАЛ";
                mg.playMusic(soundFile[0], 0);
            }
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            if (msg == "ПРОИГРАЛ"){g.setColor(Color.red);}
            else if (msg == "ПОБЕДА" || msg.length() > 20){g.setColor(Color.green);}
            else{g.setColor(Color.orange);}

            g.drawString(msg, 1280/2, 1020 - (1280/2));
        }
    }

    public void dealerHandOfTheScreen(Hand dealer,Graphics g, int cardW, int cardH){
        // 2. вывод на экран карты в руке дилера
        for (int idCard = 0; idCard < dealer.getHandDealerOrPlayer().size(); idCard++) {
            Card card = dealer.getHandDealerOrPlayer().get(idCard);
            Image cardImg = new ImageIcon(getClass().getResource(card.imagePath())).getImage();
            g.drawImage(cardImg, cardW + 50 + (cardW + 5) * idCard, 35, cardW, cardH, null);
        }
    }

    public void playerHandOfTheScreen(Hand player,Graphics g, int cardW, int cardH){
        // 2. вывод на экран карты в руке дилера
        for (int idCard = 0; idCard < player.getHandDealerOrPlayer().size(); idCard++) {
            Card card = player.getHandDealerOrPlayer().get(idCard);
            Image cardImg = new ImageIcon(getClass().getResource(card.imagePath())).getImage();
            g.drawImage(cardImg, 35 + (cardW + 15) * idCard, 700, cardW, cardH, null);
        }
    }

    public void settings(JButton button){
        button.setBackground(Color.BLACK);
        button.setForeground(Color.red);

    }

}