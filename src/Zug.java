public class Zug {
    private int von;
    private int auf;

    public Zug(int von, int auf) {
        this.von = von;
        this.auf = auf;
    }

    public int getVon() {
        return von;
    }

    public int getAuf() {
        return auf;
    }

    @Override
    public String toString() {
        return "Zug von Stack " + von + " auf Stack " + auf;
    }
}
