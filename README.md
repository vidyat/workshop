# Q-Words

## Overview

Q-Words is a web-based word guessing game built using the Spring Boot framework. The objective of the game is to guess a randomly selected word within a limited number of attempts.

### How to Play

1. Open the Q-Words application in your web browser.
1. The game will select a random word for you to guess.
1. Enter your guess in the provided input field and submit it.
1. The application will provide feedback on your guess:
   - '+' indicates a correct letter in the correct position.
   - '?' indicates a correct letter in the wrong position.
   - 'X' indicates a letter that is not present in the word.
1. Keep making guesses until you either correctly guess the word or run out of attempts.

### Rules

- The word to be guessed is randomly selected from a predefined word list.
- You have a maximum of 5 attempts to guess the word correctly.
- Each guess must be a valid word of the same length as the word to be guessed.
- Feedback is provided for each guess, indicating the correctness of the letters and their positions.

## Application Code Architecture

### Frameworks and Libraries

- Spring Boot: The main framework used for developing the web application.
- Thymeleaf: The templating engine used for rendering the HTML views.
- Lombok: A library used for reducing boilerplate code.
- JUnit: The testing framework used for unit testing.

### Game Logic

The game logic is primarily handled in the `GameController` class. It receives the user's guesses, compares them against the selected word, updates the game state, and returns the appropriate view with the game status and feedback.

### Word List Generation

The word list is stored in the `WordList` class as an `ArrayList<String>`. The words are currently hard-coded in the constructor of the `WordList` class. However, this can be easily extended to load the words from an external file or database.

## Setting Up the Development Environment

1. Install Java Development Kit (JDK) version 1.8. (this is the current version supported)
1. Install Apache Maven build tool.
1. Clone the Q-Words repository from [GitHub](https://github.com/your-repo-url).
1. Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
1. Build the project using Maven: `mvn clean install`.

## Running the Game

1. Open a terminal and navigate to the project's root directory.
1. Run the following command to start the application: `mvn spring-boot:run`.
1. Open a web browser and visit `http://localhost:8080` to access the Q-Words game.
