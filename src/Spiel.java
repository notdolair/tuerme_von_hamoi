import java.util.*;
public class Spiel {
    private Stack<Integer>[] staecke;
    private Stack<Zug> undoStack;
    private Stack<Zug> redoStack;
    private List<Integer> zahlenPool;

    public Spiel() {
        // Initialisiere die Stacks (6 Stacks)
        staecke = new Stack[6];
        for (int i = 0; i < 6; i++) {
            staecke[i] = new Stack<>();
        }

        undoStack = new Stack<>();
        redoStack = new Stack<>();
        zahlenPool = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4));
        Collections.shuffle(zahlenPool);  // Mische die Zahlen zufällig

        // Befülle die ersten 4 Stacks zufällig
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                staecke[i].push(zahlenPool.remove(0));
            }
        }
    }

    public void ausgeben() {
        for (int i = 0; i < 6; i++) {
            System.out.print("Stack " + (i + 1) + ": ");
            for (Integer zahl : staecke[i]) {
                System.out.print(zahl + "|");
            }
            System.out.println();
        }
    }

    public boolean zugMachen(int von, int auf) throws Exception {
        // Überprüfe, ob die Eingabewerte gültig sind
        if (von < 1 || von > 6 || auf < 1 || auf > 6 || staecke[von - 1].isEmpty()) {
            throw new Exception("Ungültiger Zug.");
        }

        // Hole die Zahl vom "von"-Stack
        int zahl = staecke[von - 1].pop();

        // Prüfe, ob der Ziel-Stack voll ist (maximal 4 Elemente)
        if (staecke[auf - 1].size() >= 4) {
            // Füge die Zahl wieder zurück, da der Ziel-Stack voll ist
            staecke[von - 1].push(zahl);
            throw new Exception("Ziel-Stack ist bereits voll.");
        }

        // Füge die Zahl zum Ziel-Stack hinzu
        staecke[auf - 1].push(zahl);

        // Speichere den Zug für Undo
        undoStack.push(new Zug(von, auf));
        return true;
    }

    public boolean undo() {
        if (undoStack.isEmpty()) return false;

        Zug letzterZug = undoStack.pop();
        int von = letzterZug.getVon();
        int auf = letzterZug.getAuf();
        int zahl = staecke[auf - 1].pop();
        staecke[von - 1].push(zahl);

        redoStack.push(letzterZug);
        return true;
    }

    public boolean redo() {
        if (redoStack.isEmpty()) return false;

        Zug letzterZug = redoStack.pop();
        int von = letzterZug.getVon();
        int auf = letzterZug.getAuf();
        int zahl = staecke[von - 1].pop();
        staecke[auf - 1].push(zahl);

        undoStack.push(letzterZug);
        return true;
    }

    public boolean sieg() {
        boolean oneFound = false, twoFound = false, threeFound = false, fourFound = false;

        for (int i = 0; i < 6; i++) {
            if (staecke[i].size() == 4) {
                Set<Integer> uniqueNumbers = new HashSet<>(staecke[i]);
                if (uniqueNumbers.size() == 1) {
                    int number = uniqueNumbers.iterator().next();
                    if (number == 1) oneFound = true;
                    if (number == 2) twoFound = true;
                    if (number == 3) threeFound = true;
                    if (number == 4) fourFound = true;
                }
            }
        }

        return oneFound && twoFound && threeFound && fourFound;
    }
}
