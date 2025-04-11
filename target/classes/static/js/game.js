document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const guessInput = document.querySelector('input[name="guess"]');
    const guessedWord = document.getElementById('guessedWord');
    const result = document.getElementById('result');
    const message = document.querySelector('p[th\\:text="${message}"]');
    const submitButton = document.querySelector('button[type="submit"]');
    const user = document.querySelector('input[name="user"]');

    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            
            if (!guessInput && submitButton) {
                const submitText = submitButton.textContent.trim().toUpperCase();
                if (submitText === 'PLAY AGAIN' || submitText === 'TRY AGAIN') {
                    form.submit();
                    return;
                }
            }            

            if (guessInput) {
                const guess = guessInput.value.toLowerCase();
                if (guess.length !== 6) {
                    shakeElement(guessInput);
                    return;
                }

                // Clear previous guess and result
                if (guessedWord) {
                    guessedWord.innerHTML = '';
                }

                if (result) {
                    result.innerHTML = '';
                }
                // Animate new guess
                for (let i = 0; i < guess.length; i++) {
                    setTimeout(() => {
                        const letterBox = document.createElement('span');
                        letterBox.classList.add('letter-box', 'fade-in');
                        letterBox.textContent = guess[i];
                        guessedWord.appendChild(letterBox);
                    }, i * 100);
                }

                // Submit form after animation
                setTimeout(() => {
                    form.submit();
                }, guess.length * 100 + 300);
            } else {
                console.error('Guess input element not found');
                // Handle the error appropriately
            }
        });
    }

    // Add hover effect to letter boxes
    document.querySelectorAll('.letter-box').forEach(addHoverEffect);

    // Animate result boxes
    document.querySelectorAll('#result .letter-box').forEach((box, index) => {
        setTimeout(() => {
            box.classList.add('fade-in');
        }, index * 100);
    });

    // Shake animation for incorrect guesses
    if (message && message.textContent.includes('Incorrect')) {
        shakeElement(guessedWord);
    }
});

function addHoverEffect(element) {
    element.addEventListener('mouseover', function () {
        this.style.transform = 'scale(1.1)';
        this.style.transition = 'transform 0.3s';
    });
    element.addEventListener('mouseout', function () {
        this.style.transform = 'scale(1)';
    });
}

function shakeElement(element) {
    element.classList.add('shake');
    setTimeout(() => {
        element.classList.remove('shake');
    }, 820);
}