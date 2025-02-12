import java.util.ArrayList;
import java.util.Random;

public class Deck {
    // 2.2 создадим переменную для создания рандомного значения
    private Random random = new Random();
    private ArrayList<Card> deck;


    public ArrayList<Card> createDeck(){
        // инициализируем наш списокМассивов
        this.deck = new ArrayList<Card>();
        // массив значений карт
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        // массив типов карт
        String[] types = {"C", "D", "H", "S"}; // крести, буби, черви, пики

        // создадим с помощью цикла колоду карт
        for (int idTypes = 0; idTypes < types.length; idTypes++){
            for (int idValues = 0; idValues < values.length; idValues++){
                Card newCard = new Card(values[idValues], types[idTypes]);
                this.deck.add(newCard);
            }
        }
        return this.deck;
    }
    public void shuffleDeck(){
        for (int idCard = 0; idCard < deck.size(); idCard++){
            int randIDCard = random.nextInt(deck.size()); // получаем рандомное значение от 0 до 51
            Card randomCard = deck.get(randIDCard); // записываем полученную рандомную карту в переменную
            deck.set(idCard, randomCard);// ставим на место обычной карты рандомную
            deck.set(randIDCard, deck.get(idCard)); // а на место рандомной ставим обычную
        }
    }
}
