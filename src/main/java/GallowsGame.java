
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GallowsGame {

    private long totalLines;
    private int errorCounter = 0;
    private int correctLetters = 0;

    private final ArrayList<Character> mask = new ArrayList<>();
    private final ArrayList<Character> letters = new ArrayList<>();
    private final ArrayList<Character> whitedLetters = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);
    private static final Random rand = new Random();
    private final String dictionary = "src\\main\\resources\\nouns.txt";

    public void start() {
        System.out.println("Please choose");
        System.out.println("[E]xit or [N]ew game");

        char choice = scanner.next().toLowerCase().charAt(0);

        if (choice == 'e') {
            System.exit(0);
        } else if (choice == 'n') {
            errorCounter = 0;
            correctLetters = 0;
            mask.clear();
            letters.clear();
            whitedLetters.clear();
            resolving();
        } else {
            System.out.println("Invalid choice");
            start();
        }
    }

    public void initialise() {
        countLines();
        start();
    }

    private ArrayList<Character> createNewWord() {
        String word = getRandomWord();

        for (int i = 0; i < word.length(); i++) {
            letters.add(word.charAt(i));
            mask.add('*');
        }
        return letters;
    }

    private void resolving() {
        ArrayList<Character> word = createNewWord();
        System.out.println(mask);

        while (errorCounter < 8 && correctLetters < word.size()) {
            char letter = scanner.next().toLowerCase().charAt(0);
            if (!Character.toString(letter).matches("[а-я]") ){ //валидация вводимых символов
                System.out.println("Введите корректный символ (а-я)");
                continue;
            }
            whitedLetters.add(letter);

            fillingMask(word, letter);

            System.out.println(mask);
        }

        if (!mask.contains('*')) {
            System.out.println("Вы победили!");
        } else {
            System.out.println("Вы проиграли!");
            System.out.println("Было загадано слово: " + word.toString());
        }

        start();
    }

    private void fillingMask(ArrayList<Character> word, char letter) {
        if (word.contains(letter)) {
            for (int i = 0; i < word.size(); i++) {
                if (word.get(i).equals(letter)) {
                    mask.set(i, letter);
                    correctLetters++;
                }
            }
        } else {
            errorCounter++;
            printErrors(errorCounter);
            System.out.println(whitedLetters);
        }
    }

    private String getRandomWord() {
        String word = null;

        if (totalLines == 0) {
            System.out.println("Пустой словарь");
            return null;
        }

        long randomLine = rand.nextLong(totalLines) + 1L;

        try (BufferedReader reader = new BufferedReader(new FileReader(dictionary))) {
            for (int i = 0; i < randomLine; i++) {
                word = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return word;
    }

    private void countLines() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionary))) {
            totalLines = 0;
            while (reader.readLine() != null) {
                totalLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printErrors(int errors) {
        switch (errors) {
            case 1:
                System.out.println
                        ("\n|  " +
                                "\n|   " +
                                "\n|   " +
                                "\n|   " +
                                "\n|   " +
                                "\n   ");
                break;
            case 2:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|   " +
                                "\n|   " +
                                "\n|   " +
                                "\n|   " +
                                "\n   ");
                break;
            case 3:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|     *" +
                                "\n|   " +
                                "\n|   " +
                                "\n|   " +
                                "\n   ");
                break;
            case 4:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|     *" +
                                "\n|     |" +
                                "\n|   " +
                                "\n|   " +
                                "\n   ");
                break;
            case 5:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|     *" +
                                "\n|    /|" +
                                "\n|   " +
                                "\n|   " +
                                "\n   ");
                break;
            case 6:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|     *" +
                                "\n|    /|\\" +
                                "\n|   " +
                                "\n|   " +
                                "\n   ");
                break;
            case 7:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|     *" +
                                "\n|    /|\\ " +
                                "\n|    /" +
                                "\n|   " +
                                "\n   ");
                break;
            case 8:
                System.out.println
                        ("\n _____" +
                                "\n|/    | " +
                                "\n|     *" +
                                "\n|    /|\\ " +
                                "\n|    / \\" +
                                "\n|     " +
                                "\n   ");
                break;
            default:
                break;
        }
    }
}