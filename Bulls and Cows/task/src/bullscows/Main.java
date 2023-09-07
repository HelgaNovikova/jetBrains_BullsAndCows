package bullscows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String BULLS = " bull(s)";
    public static final String COWS = " cow(s).";
    public static final String NONE = "None.";
    public static final String SECRET_CODE = " The secret code is prepared: ";
    public static final String ENTER_CODE_LENGTH = "Input the length of the secret code: ";
    public static final String ENTER_SYMBOLS_NUMBER = "Input the number of possible symbols in the code: ";
    public static final String LETS_START = "Okay, let's start a game!";
    public static final String CONGRATS = "Congratulations! You guessed the secret code.";
    private static final int MAX_CODE_LENGTH = 36;
    private static List<Character> secretCode;
    private static int cows = 0;
    private static int bulls = 0;
    private static int lengthOfTheCode;
    private static int symbolsNumber;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        enterLengthOfCode(scan);
        enterSymbolsNumber(scan);
        secretCode = new ArrayList<>();

        if (!checkLengths()) {
            System.out.println("Error: can't generate a secret number with provided lengths");
            System.exit(400);
        } else {
            generateCode(lengthOfTheCode, symbolsNumber);
        }
        System.out.println(LETS_START + secretCode.toString());

        int iteration = 1;
        while (bulls < lengthOfTheCode) {
            System.out.println("Turn " + iteration);
            makeTurn(lengthOfTheCode, secretCode, scan);
            iteration++;
        }
        System.out.println(CONGRATS);
    }

    private static void enterSymbolsNumber(Scanner scan) {
        System.out.println(ENTER_SYMBOLS_NUMBER);
        System.out.print("> ");
        String input = scan.nextLine();
        if (input.matches("\\d+")) {
            symbolsNumber = Integer.parseInt(input);
        } else {
            System.out.println("Error: " + input + " isn't a valid number.");
            System.exit(400);
        }
    }

    private static void enterLengthOfCode(Scanner scan) {
        System.out.println(ENTER_CODE_LENGTH);
        System.out.print("> ");
        String input = scan.nextLine();
        if (input.matches("\\d+")) {
            lengthOfTheCode = Integer.parseInt(input);
        } else {
            System.out.println("Error: " + input + " isn't a valid number.");
            System.exit(400);
        }
    }

    private static boolean checkLengths() {
        if (symbolsNumber > MAX_CODE_LENGTH || lengthOfTheCode == 0 || symbolsNumber == 0) {
            return false;
        }
        return lengthOfTheCode <= symbolsNumber;
    }

    private static void makeTurn(int lengthOfTheCode, List<Character> secretCodeList, Scanner scan) {
        System.out.print("> ");
        String userCode = scan.next();
        List<Character> userCodeList = new ArrayList<>();
        bulls = 0;
        cows = 0;
        for (int i = 0; i < lengthOfTheCode; i++) {
            userCodeList.add(userCode.charAt(i));
        }
        for (int i = 0; i < lengthOfTheCode; i++) {
            if (secretCodeList.get(i) == userCodeList.get(i)) {
                bulls++;
            } else if (secretCodeList.contains(userCodeList.get(i))) {
                cows++;
            }
        }
        printGrade(bulls, cows);
    }

    private static void printGrade(int bulls, int cows) {
        StringBuilder grade = new StringBuilder("Grade: ");
        if ((bulls > 0) && (cows > 0)) {
            grade.append(bulls)
                    .append(BULLS)
                    .append(" and ")
                    .append(cows)
                    .append(COWS);
        } else if (bulls > 0) {
            grade.append(bulls)
                    .append(BULLS)
                    .append(".");
        } else if (cows > 0) {
            grade.append(cows)
                    .append(COWS);
        } else {
            grade.append(NONE);
        }
        System.out.println(grade);
    }

    private static void generateCode(int lengthOfTheCode, int symbolsNumber) {
        String allSymbols = "0123456789abcdefghijklmnopqrstuvwxyz";
        ArrayList<Character> allowedSymbols = new ArrayList<>();
        StringBuilder message = new StringBuilder(SECRET_CODE);
        for (int i = 0; i < symbolsNumber; i++) {
            allowedSymbols.add(allSymbols.charAt(i));
        }
        for (int i = 0; i < lengthOfTheCode; i++) {
            Collections.shuffle(allowedSymbols);
            secretCode.add(allowedSymbols.get(0));
            allowedSymbols.remove(0);
            message.append('*');
        }
        message.append(" (0-9");
        if (symbolsNumber <= 10) {
            message.append(").");
        } else {
            message.append(allSymbols.charAt(10))
                    .append("-")
                    .append(allSymbols.charAt(symbolsNumber - 1))
                    .append(").");
        }
        System.out.println(message);
    }
}
