package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static char[] SYMBOLS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
            , 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int cows = 0;
        int bulls = 0;

        int turnCounter = 0;

        System.out.println("Input the length of the secret code:");
        String secretLengthStr = sc.nextLine();
        int secretLength;

        if (Pattern.matches("\\d+", secretLengthStr)) {
            secretLength = Integer.parseInt(secretLengthStr);
        } else {
            System.out.printf("Error: \"%s\" isn't a valid number.", secretLengthStr);
            return;
        }

        if (secretLength < 1) {
            System.out.println("Error: Input length of the secret code can not be less than 1.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int amountOfSymbols = sc.nextInt();

        if (secretLength > amountOfSymbols) {
            System.out.printf(
                    "Error: it's not possible to generate a code with a length of %d with %d unique symbols."
                    , secretLength
                    , amountOfSymbols
            );
            return;
        }

        if (amountOfSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else {

            List<Character> secretList;

            secretList = createSecret(secretLength, amountOfSymbols);

            System.out.print("The secret is prepared: ");

            for (int i = 0; i < secretLength; i++) {
                System.out.print("*");
            }

            System.out.print(" (0-" + (amountOfSymbols <= 10 ? SYMBOLS[amountOfSymbols - 1] : "9") + ")");

            if (amountOfSymbols > 10) {
                System.out.println(", " + "(a-" + SYMBOLS[amountOfSymbols - 1] + ").");
            } else {
                System.out.println();
            }

            StringBuilder sb = new StringBuilder();

            for (Character c : secretList) {
                sb.append(c);
            }

            String secret = sb.toString();

            System.out.println("Okay, let's start a game!");

            do {
                bulls = 0;
                cows = 0;
                turnCounter++;
                System.out.println("Turn " + turnCounter + ":");
                String guess = sc.next();

                for (int i = 0; i < guess.length(); i++) {
                    if (guess.charAt(i) == secret.charAt(i)) {
                        bulls++;
                    } else if (secret.contains((String.valueOf(guess.charAt(i))))) {
                        cows++;
                    }
                }

            } while (!grade(bulls, cows, secretLength));

            System.out.println("Congratulations! You guessed the secret code.");
        }

        sc.close();

    }

    private static boolean grade(int bulls, int cows, int secretLength) {
        System.out.print("Grade: ");

        if (bulls > 0 && cows == 0) {
            System.out.printf("%d bull(s).%n", bulls);
        } else if (bulls > 0 && cows > 0) {
            System.out.printf("%d bull(s) and %d cow(s).%n", bulls, cows);
        } else if (bulls == 0 && cows > 0) {
            System.out.printf("%d cow(s).%n", cows);
        } else {
            System.out.println("None");
        }

        return secretLength == bulls;
    }

    private static List<Character> createSecret(int secretLength, int amountOfSymbols) {

        List<Character> secretList = new ArrayList<>();
        Random random = new Random();

        while (secretList.size() != secretLength) {

            int randomIndex = random.nextInt(amountOfSymbols);

            char randomChar = SYMBOLS[randomIndex];

            if (!secretList.contains(randomChar)) {
                secretList.add(randomChar);
            }
        }

        return secretList;
    }
}
