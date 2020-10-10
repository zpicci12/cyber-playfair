make:
	@echo To encode: make run ARGS=\"encode ciphertext keytext\"
	@echo To decode: make run ARGS=\"decode plaintext keytext\"
	@echo \*\*Note: this algorithm removes the letter X from decoded words and removes the letter Z from decoded words if it is the last letter in the word.

run: Playfair.class
	java Playfair $(ARGS)

compile: Playfair.java
	javac Playfair.java
