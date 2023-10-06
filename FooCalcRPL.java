class FooCalcRPL {
    public static void main(String[] args) {
        ObjEmp o1 = new ObjEmp(42, 2);
        ObjEmp o2 = new ObjEmp(64, 3);

        PileRPL pile = new PileRPL(5);
        System.out.println(pile);

        pile.push(o1);
        System.out.println(pile);

        pile.push(o2);
        System.out.println(pile);

        pile.add();
        System.out.println(pile);
    }
}