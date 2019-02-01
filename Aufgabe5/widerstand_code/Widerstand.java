import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Widerstand {

    static int[] resistors;
    //Zielwert, mit Kombination zu erreichender Wert
    static final double GOAL = 500;

    public static void main(String[] args){
        resistors = readFile("./widerstaende.txt").stream().mapToInt(i->i).toArray();
        WiderstandCalculator calc = new WiderstandCalculator(resistors, GOAL);
    }

    public static List<Integer> readFile(String path){

        List<Integer> result = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));

            for(String line = br.readLine(); line != null; line = br.readLine()) {
                result.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
}