import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class main {
    // gets the word from a list of word
    public static String getWord(String wordList[]){
        Random rand = new Random();
        int n = rand.nextInt(10);
        return wordList[n];
    }
    // fills array with dashes (-)
    public static void fillArrayWithDashes(char array[]){
        for(int i = 0; i < array.length; i++){
            array[i] = '-';
        }
    }
    // checks if word was guessed before
    // returns boolean
    public static boolean wasWordUsed(String used[], String word){
        boolean test = false;

        for (int i = 0; i < used.length; i++) {
            if(used[i] == word) {
                test = true;
            }
        }
        return test;
    }

    public static void main(String[] args) {
        //Variables
        char[] availableLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] visibleLetters = new char[100];
        char userGuess = ' ';
        char userInput = ' ';
        int roundsPlayed = 0;
        int numOfGuesses = 7;
        int wordLength = 0;
        int lettersFound = 0;
        boolean roundOver = false;
        boolean gameOver = false;
        boolean foundLetter = false;
        boolean foundWord = false;
        String[] wordList = {"overflow", "cheese", "pepperoni", "scarecrow", "noise", "embarrass", "string", "regular", "unnatural", "gonzaga"};
        String[] wordsGuessed = new String[10];
        String wordRead = "";
        String wordToGuess = "";
        Scanner sc = new Scanner(System.in);

        // Opening File
        // checks if u have file
        System.out.println("Do you have a words.txt on your desktop?");
        System.out.println("Press y for yes and n for no.");
        if(sc.next().charAt(0) == 'y' ) {
            File desktop = new File(System.getProperty("user.home"), "/Desktop");
            try {
                Scanner file = new Scanner(new File(desktop, "words.txt"));
                int i = 0;
                while (file.hasNextLine()) {
                    wordList[i] = file.nextLine();
                    i++;
                    // populates array
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // GAME
        // SETUP
        do {
            Scanner in = new Scanner(System.in);
            String temp = getWord(wordList);
            // gets a word that isn't used yet
            do {
                if (!wasWordUsed(wordsGuessed, temp)) {
                    wordToGuess = temp;
                    wordLength = wordToGuess.length();
                }
            }while(wasWordUsed(wordsGuessed,temp));

            fillArrayWithDashes(visibleLetters);
            System.out.println("The word is: " + wordToGuess);
            System.out.println("The word to guess has " + wordLength + " letters.");

            // GAME LOOP
            do {
                foundLetter = false;
                // prints visibleletters
                for (int i = 0; i < wordLength; i++) {
                    System.out.print(visibleLetters[i]);
                }
                // prints available letters
                System.out.print("\nAvailable Letters: ");
                for (char item : availableLetters) {
                    System.out.print(item);
                }
                // prints number of guesses remaining
                System.out.println("\n" + numOfGuesses + " incorrect guesses remaining.");
                System.out.println("Please enter your guess:");
                userGuess = in.next().charAt(0);
                System.out.println("\nLetter guessed: " + userGuess);

                if (!(new String(availableLetters).contains(String.valueOf(userGuess)))) { // prevents letters from being guessed again
                    System.out.println("You've guessed this letter before, try something else.");
                } else {
                    for (int i = 0; i < wordToGuess.length(); i++) {
                        if (wordToGuess.charAt(i) == userGuess) {
                            visibleLetters[i] = userGuess;
                            foundLetter = true;
                            lettersFound++;
                        }
                    }
                    if (foundLetter) {
                        System.out.println("Nice! " + userGuess + " is in the word.");
                    }
                    if (!foundLetter) {
                        System.out.println("\nSorry " + userGuess + " is not in the word");
                        numOfGuesses--;
                        System.out.println(numOfGuesses + " incorrect guesses remaining.");
                    }
                    if (lettersFound == wordLength) {
                        System.out.println("Congrats, you guessed the word " + wordToGuess);
                        roundOver = true;
                    }
                    if (numOfGuesses == 0) {
                        roundOver = true;
                        System.out.println("\nSorry, you ran out of guesses :(");
                        System.out.println("The word was " + wordToGuess);
                        System.out.println("Better luck next time!");
                    }
                    for (int j = 0; j < availableLetters.length; j++) {
                        if (availableLetters[j] == userGuess) {
                            availableLetters[j] = ' ';
                        }
                    }
                }
                if(roundOver){
                    wordsGuessed[roundsPlayed] = wordToGuess;
                    roundsPlayed++;
                }
            } while (!roundOver && roundsPlayed != 10);
            // check to see if user would like to keep playing
            //in.nextLine();
            //userInput = ' ';
            System.out.println("Would you like to keep playing?");
            System.out.println("Press y for yes or n for no.");
            userInput = in.next().charAt(0);
            if(userInput == 'y'){
                availableLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                fillArrayWithDashes(visibleLetters);
                numOfGuesses = 7;
                lettersFound = 0;
                roundOver = false;
            }
            else {
                gameOver = true;
                System.out.println("Thanks for playing!");
            }
        } while(!gameOver);
    }
}

