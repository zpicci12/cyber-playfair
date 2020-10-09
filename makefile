make:
	@echo To encode: make run ARGS=\"encode ciphertext keytext\"

run: PlayfairEncode.class compile
	java PlayfairEncode $(ARGS)

compile: PlayfairEncode.java
	javac PlayfairEncode.java
