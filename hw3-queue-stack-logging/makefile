collect:
	find -name "*.java" > sources.txt

build:
	javac -d build @sources.txt

run:
	java -cp build Main.Main $(ARGS)

clean:
	rm -rf build sources.txt
	$(RM) -r docs

docs:
	javadoc -d docs -cp . @sources.txt

all: collect build