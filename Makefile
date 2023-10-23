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

help:
	@cd $(OUT); java $(PROJ) -h

logged:
	@cd $(OUT); java $(PROJ) -l

replay:
	@cd $(OUT); java $(PROJ) -r

logged_replay:
	@cd $(OUT); java $(PROJ) -lr

shared:
	@cd $(OUT); java $(PROJ) --shared

discrete:
	@cd $(OUT); java $(PROJ) --discrete

remote_logged:
	@cd $(OUT); java $(PROJ) --discrete -l