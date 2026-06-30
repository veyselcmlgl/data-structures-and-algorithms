# Makefile for Task Scheduling Using Min Heap Data Structure
# CSE222 Assignment 5

# Build directory
BUILD_DIR = build
# Source directory
SRC_DIR = src
# Documentation directory
DOCS_DIR = docs
# Main class
MAIN = Main.Main

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

test: build
	@echo "Running automated test..."
	java -cp $(BUILD_DIR) $(MAIN) config.txt < test_input.txt

clean:
	rm -rf $(BUILD_DIR) sources.txt
	$(RM) -r $(DOCS_DIR)

docs:
	mkdir -p $(DOCS_DIR)
	javadoc -d $(DOCS_DIR) -cp $(BUILD_DIR) @sources.txt