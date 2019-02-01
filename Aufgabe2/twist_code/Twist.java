import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Twist {

    private static Twister twister;

    public static final String[] defaultDictionaries = new String[]{"./words_de.txt","./german.dic"};

    private static String lastTwisted = "";
    private static String lastUntwisted = "";

    public static void main(String[] args) {
        System.out.println("Starting up...");

        String[] dictPaths = args;
        if(dictPaths.length ==  0) {
            dictPaths = defaultDictionaries;
            System.out.println("Loading default dictionaries:");
        } else {
            System.out.println("Loading user dictionaries:");
        }
        for (String dictPath : dictPaths) {
            System.out.println(" -'"+dictPath+"'");
        }

        twister = new Twister(dictPaths);
        System.out.println("Loaded dictionaries.\n");

        System.out.println("Waiting for commands...  try 'help'\n");
        commandLoop();
    }

    private static void commandLoop() {
        Scanner consoleIn = new Scanner(System.in);
        for (String input = consoleIn.nextLine(); input != null && !input.equals("exit"); input = consoleIn.nextLine()) {
            if (input.startsWith("twist ")) {
                String text = input.replaceFirst("twist ", "");
                lastTwisted = twister.twistSentence(text);
                System.out.println(lastTwisted+"\n");

            } else if (input.startsWith("twistFile ")) {
                String path = input.replaceFirst("twistFile ", "");
                String text = readFile(path);
                if(text != null) {
                    lastTwisted = twister.twistSentence(text);
                    System.out.println(lastTwisted+"\n");
                }

            } else if (input.startsWith("untwist")) {
                String untwisted = twister.untwistSentence(lastTwisted);
                lastUntwisted = untwisted;
                System.out.println(lastUntwisted+"\n");

            } else if (input.startsWith("saveUntwisted")) {
                String path = input.replaceFirst("saveUntwisted ", "").replaceFirst("saveUntwisted", "");
                path = (path.length() <= 0) ? "./untwisted.txt" : path;
                writeToFile(path, lastUntwisted);

            } else if (input.startsWith("help")){
                String format = "%-24s%s\n";
                System.out.println("Help:      (*: benötigte Argumente)");
                System.out.format(format, "twist [*text]", "Twisted den angegebenen Text");
                System.out.format(format, "twistFile [*path]", "Liest die angegebene Datei und twisted ihren Inhalt.");
                System.out.format(format, "untwist", "Untwisted den letzten Text.");
                System.out.format(format, "saveUntwisted [path]", "Speichert den zuletzt enttwisteten Text in einer Textdatei.");
                System.out.format(format, "exit", "Beendet das Programm.\n");

            } else {
                System.out.println("No valid command. Try 'help'\n");
            }
        }
    }

    private static void writeToFile(String path, String text) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(text);
            fw.close();
            System.out.println("Saved text to '"+path+"'.\n");
        } catch (IOException e) {
            System.err.println("Couldn't write to '"+path+"'!");
            e.printStackTrace();
        }
    }

    private static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file '"+path+"'!");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
