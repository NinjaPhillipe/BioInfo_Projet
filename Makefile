FILE = root/FragmentAssembler.java
JUNIT = libs/junit-4.13.1.jar
HAMCREST = libs/hamcrest-core-1.3.jar
TEST = root/test/Test.java

build:
	javac $(FILE) -d ./build/
run:
	cd build && java root.FragmentAssembler
bt:
	javac -cp ".:$(JUNIT)" $(TEST) -d ./build/
	cd build && java -cp ".:../$(JUNIT):../$(HAMCREST)" junit.textui.TestRunner root.test.Test
br:
	javac $(FILE) -d ./build/
	cd build && java root.FragmentAssembler

clean:
	cd build && rm **/*.class