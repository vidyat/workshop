package com.sample.qwords.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class WordList {

    private static final Logger logger = LogManager.getLogger(WordList.class);
    private static WordList instance;
    private final List<String> wordList;
    private boolean isLoaded = false;

    // local variables to track the current and last word returned
    private String lastWord = null;
    private String currentWord = null;
    private String filePath = null;

    public WordList() {
        this.wordList = new ArrayList<>();
        this.isLoaded = false;
        this.filePath = "";
        loadWordsFromFile();
    }

    public WordList(String filePath) throws IOException {
        this.wordList = new ArrayList<>();
        this.isLoaded = false;
        this.filePath = filePath;
        loadWordsFromFile(filePath);
    }

    private void loadWordsFromFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            wordList.add(line.trim());
        }
        isLoaded = true;

        currentWord = wordList.isEmpty() ? null : getRandomWord();
        lastWord = null;
        System.out.println("Total words: " + wordList.size());
    }

    private void loadWordsFromFile(String filePath) throws IOException {
        System.out.println("Loading words from file: " + filePath);
        if (!isLoaded | !filePath.equals(this.filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                loadWordsFromFile(reader);
                System.out.println("Words loaded from file: " + filePath);
            } catch (IOException e) {
                System.err.println("Error loading words from file: " + filePath);
                throw e;
            }
        }
    }

    private void loadWordsFromFile() {
        System.out.println("Loading words from classpath resource: words.txt");
        if (!isLoaded) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("words.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                loadWordsFromFile(reader);
                System.out.println("Words loaded from classpath resource: words.txt");
            } catch (IOException e) {
                System.err.println("Error loading words from classpath resource: words.txt");
                e.printStackTrace();
            }
        }
    }

    public List<String> getWordList() {
        return new ArrayList<>(wordList);
    }

    public void clearWordList() {
        wordList.clear();
    }

    public void addWord(String word) {
        if (word != null && !wordList.contains(word)) {
            wordList.add(word);
        }
    }

    public boolean containsWord(String word) {
        if (word == null) {
            return false;
        }
        return wordList.contains(word);
    }

    public String getWordAtIndex(int index) {
        return wordList.get(index);
    }

    public boolean removeWord(String word) {
        boolean removed = wordList.remove(word);
        if (removed) {
        }
        return removed;
    }

    public void saveWordListToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String word : wordList) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void loadWordListFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            wordList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line.trim());
            }
            isLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Shuffles the word list.
     */
    public void shuffleWordList() {
        Collections.shuffle(wordList);
    }

    public List<String> getWordsByLength(int length) {
        return wordList.stream().filter(word -> word.length() == length).collect(Collectors.toList());
    }

    public String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        // log the selected index
        logger.info("Selected index: " + index);

        // track the current and last word returned using
        // lastWord and currentWord class properties
        if (wordList.isEmpty()) {
            return null;
        }

        // if the currentWord is not null, store it in the lastWord variable before
        // getting a new word.
        if (currentWord != null) {
            lastWord = currentWord;
        }
        currentWord = this.wordList.get(0);
        return currentWord;
    }

    public List<String> getWords() {
        return new ArrayList<>(wordList);
    }

    public static void resetInstance() {
        if (instance != null) {
            instance = null;
        }
        throw new UnsupportedOperationException("Unimplemented method 'resetInstance'");
    }

    public String getLastWord() {
        return lastWord;
    }
}
