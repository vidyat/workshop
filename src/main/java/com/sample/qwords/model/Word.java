package com.sample.qwords.model;

import java.util.Arrays;

// TODO: Add javadocs comments
public class Word {
    private String word;
    private char[] characters;

    public Word(String word) {
        this.word = word;
        this.characters = word.toCharArray();
    }

    public String getWord() {
        return word;
    }

    public boolean contains(char c) {
        for (char ch : characters) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }

    public boolean isCorrect(char[] guess) {
        return Arrays.equals(characters, guess);
    }

    public String getInfo(String guess) {
        char[] guessArray = guess.toCharArray();
        StringBuilder result = new StringBuilder();
        
        System.out.println("\n\nWord: " + word);
        System.out.println("\n\nGuess: " + guess);

        for (int i = 0; i < guessArray.length; i++) {
            char currentGuess = guessArray[i];
            System.out.println("Evaluating current char from guess to word: " + currentGuess + "->" + characters[i]);
            if (contains(currentGuess)) {
                // Check if the character is in the correct position
                if (characters[i] == currentGuess) {
                    // present and in the right position
                    result.append('+');
                } else {
                    // present but in wrong position
                    result.append('?');
                }
            } else {
                // character is not present
                result.append('X');
            }
        }

        return result.toString();
    }
}
