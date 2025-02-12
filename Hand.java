import java.util.ArrayList;

public class Hand {
    private Card hiddenCartDealer;
    private int countAce;
    private int sumPoints;
    private ArrayList<Card> handDealerOrPlayer;

    public Hand(int numberOfCardsHand, ArrayList<Card> deck, String name){
        createHand(numberOfCardsHand, deck, name);
    }
    public int getSumPoints(){
        return sumPoints;
    }
    public Card getHiddenCartDealer(){
        return hiddenCartDealer;
    }
    public void setNewSum(int newCard){
        this.sumPoints = sumPoints + newCard;
    }
    public void newCountAce(Card card){
        this.countAce += (card.getValue().equals("A")) ? 1 : 0;
    }
    public int reducePlayerAce(){
        while (this.sumPoints > 21 && this.countAce > 0){
            this.sumPoints -= 10;
            this.countAce -= 1;
        }
        return sumPoints;
    }

    public ArrayList<Card> getHandDealerOrPlayer(){
        return handDealerOrPlayer;
    }

    // СОЗДАЕТ РУКУ ДЛЯ ДИЛЕРА ИЛИ ИГРОКА
    public void createHand(int numberOfCardsHand, ArrayList<Card> deck, String name){
        if (name.equals("DEALER")){ // создаем скрытую карту
            numberOfCardsHand -= 1;
            hiddenCartDealer = deck.remove(deck.size() - 1);
            sumPoints += hiddenCartDealer.checkingСardType();
            countAce += (hiddenCartDealer.getValue().equals("A")) ? 1 : 0;
        }

        handDealerOrPlayer = new ArrayList<Card>();

        for (int i = 0; i < numberOfCardsHand; i++){
            Card card = deck.remove(deck.size() - 1);
            sumPoints += card.checkingСardType();
            countAce += (card.getValue() == "A") ? 1 : 0;
            handDealerOrPlayer.add(card);
        }

    }
}
