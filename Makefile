CC := javac

SRC :=  src/FooCalcRPL.java

OUT := out

PROJ := FooCalcRPL

build:
	$(CC) -sourcepath src -d $(OUT) $(SRC)
	jar cfe $(PROJ).jar $(PROJ) -C $(OUT) .

clean:
	rm -rf $(OUT) $(PROJ).jar

run:
	java -jar $(PROJ).jar

docker-build:
	docker build -t foocalcrpl:latest .

docker-run:
	docker run --rm -it -v $(CURDIR):$(CURDIR) -w $(CURDIR) foocalcrpl /bin/bash