FILE = root/FragmentAssembler.java
TEST = root/test/Test.java

build:
	javac $(FILE) -d ./build/
run:
	cd build && java root.FragmentAssembler
br:
	javac $(FILE) -d ./build/
	cd build && java root.FragmentAssembler

jar:
	javac $(FILE) -d ./build/
	cd build && jar cfm FragmentAssembler.jar ../Manifest.txt root

clean:
	cd build && rm **/*.class