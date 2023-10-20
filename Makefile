CC := javac

SRC :=  FooCalcRPL.java \
		PileRPL.java \
		ObjEmp.java

OUT := out

PROJ := FooCalcRPL

build:
	$(CC) -d $(OUT) $(SRC)

run:
	@cd $(OUT); java $(PROJ)

remote:
	@cd $(OUT); java $(PROJ) R

shared:
	@cd $(OUT); java $(PROJ) R S