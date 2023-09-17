package LAB1;

import java.util.Arrays;
import java.util.Scanner;

public class Lab1 {
    static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char operation = getOperation(scanner);
        String word = getValidatedWord(scanner);
        int key = getKey(scanner);

        System.out.println("Enter your encryption type:" +
                "\n1 - Simple Alphabet" +
                "\n2 - Custom Alphabet");
        int encryptionType = getEncryptionType(scanner);

        String result = performEncryption(word, key, operation, encryptionType);
        System.out.println("========================================================");
        System.out.println("Result: " + result);
    }

    private static char getOperation(Scanner scanner) {
        char operation;
        do {
            System.out.println("Enter your operation:" +
                    "\ne - encryption" +
                    "\nd - decryption");
            operation = scanner.nextLine().charAt(0);
        } while (operation != 'e' && operation != 'd');
        return operation;
    }

    private static String getValidatedWord(Scanner scanner) {
        String word;
        do {
            System.out.println("Enter your word:");
            word = scanner.nextLine().toUpperCase().replaceAll("\\s", "");
        } while (!word.matches("[A-Z]+"));
        return word;
    }

    private static int getKey(Scanner scanner) {
        int key;
        do {
            System.out.println("Enter your key (1-25):");
            key = scanner.nextInt();
        } while (key < 1 || key > 25);
        return key;
    }

    private static int getEncryptionType(Scanner scanner) {
        int encryptionType;
        do {
            encryptionType = scanner.nextInt();
        } while (encryptionType != 1 && encryptionType != 2);
        return encryptionType;
    }

    private static String performEncryption(String word, int key, char operation, int encryptionType) {
        StringBuilder newWord = new StringBuilder();
        char[] alphabet = (encryptionType == 1) ? ALPHABET : generateCustomAlphabet();

        for (char c : word.toCharArray()) {
            if (operation == 'e') {
                newWord.append(encryptChar(c, alphabet, key));
            } else if (operation == 'd') {
                newWord.append(decryptChar(c, alphabet, key));
            }
        }
        return newWord.toString();
    }

    private static char encryptChar(char c, char[] alphabet, int key) {
        int index = index(alphabet, c);
        return alphabet[(index + key) % alphabet.length];
    }

    private static char decryptChar(char c, char[] alphabet, int key) {
        int index = index(alphabet, c);
        return alphabet[(index - key + alphabet.length) % alphabet.length];
    }

    private static char[] generateCustomAlphabet() {
        System.out.println("Enter your Word Key:");
        String customKey = new Scanner(System.in).nextLine().toUpperCase();

        while (customKey.length() < 7) {
            System.out.println("Your word key is too short. It needs to be at least 7 characters long!");
            System.out.println("Enter your Word Key:");
            customKey = new Scanner(System.in).nextLine().toUpperCase();
        }

        char[] customAlphabet = new char[ALPHABET.length];
        int customIndex = 0;

        for (char c : customKey.toCharArray()) {
            if (index(customAlphabet, c) == -1) {
                customAlphabet[customIndex++] = c;
            }
        }

        for (char c : ALPHABET) {
            if (index(customAlphabet, c) == -1) {
                customAlphabet[customIndex++] = c;
            }
        }

        System.out.println("Do you want to see the Custom Alphabet? (y/n): ");
        char choice = new Scanner(System.in).next().charAt(0);
        if (choice == 'y' || choice == 'Y') {
            System.out.println(Arrays.toString(customAlphabet));
        }

        return customAlphabet;
    }

    private static int index(char[] arr, char target) {
        target = Character.toUpperCase(target);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target)
                return i;
        }
        return -1;
    }
}
