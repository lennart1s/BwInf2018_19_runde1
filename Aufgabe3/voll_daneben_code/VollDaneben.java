import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VollDaneben {

    public static void main(String[] args){

        GewinnRechner gw = new GewinnRechner();
        List<Integer> list = bubbleSort(readFile("./beispiel3.txt"));
        //List<Integer> list = new ArrayList<>(bubbleSort(Arrays.asList(1,680,343,48,79,257,310,26,934,957,734,605,928,309,910,95,559,728,626,300,958,801,693,941,576)));
        //List<Integer> list = bubbleSort(randomInput());
        outputIntList(gw.maxGewinn(list));
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

    public static List<Integer> bubbleSort(List<Integer> input) {
        int temp;
        for(int i=1; i<input.size(); i++) {
            for(int j=0; j<input.size()-i; j++) {
                if(input.get(j)>input.get(j+1)) {
                    temp=input.get(j);
                    input.set(j,input.get(j+1));
                    input.set(j+1,temp);
                }

            }
        }
        return input;
    }

    public static void outputIntList(List<Integer> arr){
        for(int i = 0; i<arr.size(); i++){
            System.out.println(arr.get(i));
        }
    }

    public static List<Integer> randomInput(){
        Random rndm = new Random();
        int count = rndm.nextInt(1000);
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < count; i++){
            result.add(rndm.nextInt(1000));
        }
        return result;
    }
}
