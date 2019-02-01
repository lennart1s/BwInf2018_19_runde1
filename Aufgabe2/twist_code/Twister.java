import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Twister {

    List<Map<String, List<String>>> collections;

    public Twister(String ... wordFilePaths) {
        collections = new ArrayList<>();
        for(String wordFilePath : wordFilePaths) {
            try {
                Map<String, List<String>> words = new HashMap<>();
                BufferedReader br = new BufferedReader(new FileReader(wordFilePath));

                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    String normalized = this.normalize(line);

                    char first = Character.toUpperCase(normalized.charAt(0));
                    char last = Character.toUpperCase(normalized.charAt(normalized.length()-1));
                    String key = ""+first+last+normalized.length();

                    words.putIfAbsent(key, new ArrayList<>());
                    words.get(key).add(normalized);
                }

                collections.add(words);
            } catch (FileNotFoundException e) {
                System.err.println("No file or directory '" + wordFilePath + "'!");
            } catch (IOException e) {
                System.err.println("Error reading file '" + wordFilePath + "'!");
                e.printStackTrace();
            }
        }
    }

    public String untwistSentence(String sentence) {
        String untiwsted = "";

        int start = -1;
        for(int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if(Character.isLetter(c) && start == -1) {
                start = i;
            } else if((!Character.isLetter(c) || i == sentence.length()-1) && start != -1) {
                String twistedWord = this.untwistWord(sentence.substring(start, i));
                untiwsted += twistedWord;
                start = -1;
                untiwsted += c;
            } else if(!Character.isLetter(c)) {
                untiwsted += c;
            }
        }


        return untiwsted;
    }

    public String untwistWord(String word) {
        char first = Character.toUpperCase(word.charAt(0));
        char last = Character.toUpperCase(word.charAt(word.length()-1));
        int length = word.length();
        String key = ""+first+last+length;

        for(Map<String, List<String>> wc : this.collections) {
            if(wc.containsKey(key)) {
                eqKeyCheck:
                for (String eqKey : wc.get(key)) {
                    for (char c : word.toCharArray()) {
                        if (this.numCharInString(word, c) != this.numCharInString(eqKey, c)) {
                            continue eqKeyCheck;
                        }
                    }
                    return (Character.isUpperCase(word.charAt(0))) ? firstUpperCase(eqKey): eqKey;
                }
            }
        }

        return word;
    }

    private String twistWord(String s) {
        s = this.normalize(s);
        if(s.length() <= 3) {
            return s;
        }

        char first = s.charAt(0);
        char last = s.charAt(s.length()-1);

        s = s.substring(1, s.length()-1);
        List<Character> chars = new ArrayList<>();
        for(char c : s.toCharArray()) {
            chars.add(c);
        }
        Random r = new Random();
        String twisted = ""+first;
        for(int i = chars.size(); i > 0; i = chars.size()) {
            int charIndex = r.nextInt(i);
            twisted += chars.get(charIndex);
            chars.remove(charIndex);
        }
        twisted += last;

        return twisted;
    }

    public String twistSentence(String sentence) {
        sentence = this.normalize(sentence);
        String twisted = "";

        int start = -1;
        for(int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if(Character.isLetter(c) && start == -1) {
                start = i;
            } else if((!Character.isLetter(c) || i == sentence.length()-1) && start != -1) {
                String twistedWord = this.twistWord(sentence.substring(start, i));
                twisted += twistedWord;
                start = -1;
                twisted += c;
            } else if(!Character.isLetter(c)) {
                twisted += c;
            }
        }

        return twisted;
    }

    private int numCharInString(String s, char wanted) {
        int num = 0;
        for(char c : s.toCharArray()) {
            if((""+c).equalsIgnoreCase(""+wanted)) {
                num++;
            }
        }
        return num;
    }

    private String normalize(String s) {
        s = s.replaceAll("Ä", "Ae");
        s = s.replaceAll("ä", "ae");
        s = s.replaceAll("Ö", "Oe");
        s = s.replaceAll("ö", "oe");
        s = s.replaceAll("Ü", "Ue");
        s = s.replaceAll("ü", "ue");
        return s;
    }

    private String unNormalize(String s) {
        s = s.replaceAll("Ae", "Ä");
        s = s.replaceAll("ae", "ä");
        s = s.replaceAll("Oe", "Ö");
        s = s.replaceAll("oe", "ö");
        s = s.replaceAll("Ue", "Ü");
        s = s.replaceAll("ue", "ü");
        return s;
    }

    private String firstUpperCase(String s) {
        return  Character.toUpperCase(s.charAt(0))+s.substring(1);
    }

}
