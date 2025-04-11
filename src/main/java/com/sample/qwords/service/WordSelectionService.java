package com.sample.qwords.service;

import org.springframework.stereotype.Service;
import com.sample.qwords.repository.WordList;

/**
 * A service class that handles the selection of a random word from a word list.
 */
@Service
public class WordSelectionService {

    private WordList wordList;
    private String selectedWord;

    /**
     * Constructs a new instance of the WordSelectionService.
     * Initializes the word list and selects a random word from it.
     */
    public WordSelectionService() {

        this.wordList = new WordList();
        this.selectNewWord();
    }

    /**
     * Returns the currently selected word.
     *
     * @return The selected word as a String.
     */
    public String getWord() {
        return this.selectedWord;
    }

    /**
     * Selects a new random word from the word list.
     */
    public void selectNewWord() {
        this.selectedWord = wordList.getRandomWord();
    }
}
