# Makefile for High-Performance Spell Checker
# CSE222 Assignment 6

# Build directory
BUILD_DIR = build
# Source directory
SRC_DIR = src
# Documentation directory
DOCS_DIR = docs
# Main class
MAIN = SpellChecker

# Default target
.PHONY: all collect build run clean docs test
all: collect build docs

collect:
	find $(SRC_DIR) -name "*.java" > sources.txt

build:
	mkdir -p $(BUILD_DIR)
	javac -d $(BUILD_DIR) @sources.txt

run:
	java -cp $(BUILD_DIR) $(MAIN) $(ARGS)

clean:
	rm -rf $(BUILD_DIR) sources.txt
	$(RM) -r $(DOCS_DIR)

docs:
	mkdir -p $(DOCS_DIR)
	javadoc -d $(DOCS_DIR) -cp $(BUILD_DIR) @sources.txt