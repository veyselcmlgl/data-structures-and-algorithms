# Makefile for Deep Space Planetary System Analysis
# CSE 222/505 Homework 4

# Build directory
BUILD_DIR = build

# Source directory
SRC_DIR = src

# Documentation directory
DOCS_DIR = docs

# Main class
MAIN = DeepSpacePlanetarySystem

# Default target
.PHONY: all collect build run clean docs

all: collect build

collect:
	find $(SRC_DIR) -name "*.java" > sources.txt

build:
	mkdir -p $(BUILD_DIR)
	javac -d $(BUILD_DIR) @sources.txt

run:
	java -cp $(BUILD_DIR) $(MAIN) $(ARGS)

test: build
	java -cp $(BUILD_DIR) $(MAIN) < test_commands.txt

clean:
	rm -rf $(BUILD_DIR) sources.txt
	$(RM) -r $(DOCS_DIR)

docs:
	mkdir -p $(DOCS_DIR)
	javadoc -d $(DOCS_DIR) -cp $(BUILD_DIR) @sources.txt