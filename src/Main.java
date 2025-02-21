import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Spiel spiel = new Spiel();
        Scanner scanner = new Scanner(System.in);
        boolean laufend = true;

        while (laufend) {
            spiel.ausgeben();
            System.out.println("Wählen Sie eine Aktion:");
            System.out.println("1: Weiter spielen");
            System.out.println("2: Undo");
            System.out.println("3: Redo");
            System.out.println("4: Beenden");

            int eingabe = scanner.nextInt();

            try {
                switch (eingabe) {
                    case 1:
                        System.out.println("Geben Sie den Stack von und den Stack zu ein (1-6):");
                        int von = scanner.nextInt();
                        int auf = scanner.nextInt();
                        if (!spiel.zugMachen(von, auf)) {
                            System.out.println("Ungültiger Zug.");
                        }
                        if (spiel.sieg()) {
                            System.out.println("Sie haben gewonnen!");
                            laufend = false;
                        }
                        break;

                    case 2:
                        if (!spiel.undo()) {
                            System.out.println("Kein Undo verfügbar.");
                        }
                        break;

                    case 3:
                        if (!spiel.redo()) {
                            System.out.println("Kein Redo verfügbar.");
                        }
                        break;

                    case 4:
                        laufend = false;
                        break;

                    default:
                        System.out.println("Ungültige Eingabe.");
                        break;
                }
            } catch (Exception e) {
                ExceptionHandling.handleException(e);
            }
        }
        scanner.close();
    }
}
