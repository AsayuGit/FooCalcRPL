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

logged:
	@cd $(OUT); java $(PROJ) -l true	

shared:
	@cd $(OUT); java $(PROJ) -m shared

discrete:
	@cd $(OUT); java $(PROJ) -m discrete

remote_logged:
	@cd $(OUT); java $(PROJ) -m discrete -l true