make:
	@echo To encode: make run ARGS=\"encode ciphertext keytext\"
	@echo To decode: make run ARGS=\"decode plaintext keytext\"

run: Playfair.class
	java Playfair $(ARGS)

compile: Playfair.java
	javac Playfair.java
