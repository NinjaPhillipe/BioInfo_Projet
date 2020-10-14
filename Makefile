FILE = MainBioInfo.java Graph.java

build:
	javac $(FILE) -d ./build/
run:
	cd build && java root.MainBioInfo

br:
	javac $(FILE) -d ./build/
	cd build && java root.MainBioInfo
clean:
	cd build && rm *.class