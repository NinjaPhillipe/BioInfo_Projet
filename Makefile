FILE = root/MainBioInfo.java  root/test/TestAll.java

build:
	javac $(FILE) -d ./build/
run:
	cd build && java root.MainBioInfo
test:
	cd build && java root.test.TestAll
br:
	javac $(FILE) -d ./build/
	cd build && java root.MainBioInfo

clean:
	cd build && rm *.class