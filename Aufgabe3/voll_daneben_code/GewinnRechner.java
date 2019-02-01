import java.util.*;

public class GewinnRechner {

    public int einsatz = 25;
    public int profit = 0;
    int abweichung = 50;

    //Anzahl an Wiederholung nachdem Profit über 0 liegt -> höherer profit möglich
    final int RETURNS = 10;
    int currentReturn = 0;
    Ergebnis bestErgebnis = new Ergebnis();

    public GewinnRechner(){}

    public List<Integer> maxGewinn(List<Integer> inputs){
        int size = inputs.size();
        int gewinn = size * einsatz;
        System.out.println("Gesamteinsatz: " + gewinn);

        while((profit <= 0 || RETURNS > currentReturn)){
            Ergebnis result = new Ergebnis();
            result.setGewinnZahlen(getGewinnZahlen(inputs, abweichung));
            result.setGewinn(getGewinn(inputs, result.getGewinnZahlen()));

            if(abweichung > 1) abweichung -=1;
            else abweichung = 50;

            if(result.getGewinn() > bestErgebnis.getGewinn()) {
                bestErgebnis = result;
                currentReturn = 0;
                System.out.println("Found a better one " + bestErgebnis.getGewinn());
            }
            profit = result.getGewinn();
            if(profit > 0)currentReturn++;
        }
        System.out.println("Profit: " + bestErgebnis.getGewinn());
        return bestErgebnis.getGewinnZahlen();
    }

    public int getGewinn(List<Integer> inputs, List<Integer> gewinnZahlen){
        int result = inputs.size() * einsatz;
        for(int i = 0; i<inputs.size(); i++){
            result -= getClosestDistance(inputs.get(i), gewinnZahlen);
        }
        return result;
    }

    public int getClosestDistance(int input, List<Integer> gewinnZahlen){
        boolean firstIteration = true;
        int minDist = 0;
        for(int i = 0; i< gewinnZahlen.size(); i++) {
            int dist = Math.abs(gewinnZahlen.get(i)-input);
            if(firstIteration){
                minDist = dist;
                firstIteration = false;
            }else if(dist < minDist) {
                minDist = dist;
            }
        }
        return minDist;
    }

    public List<Integer> getGewinnZahlen(List<Integer> inputs, int abweichung){
        List<Integer> result = new ArrayList<>();
        int size = inputs.size() -1;
        for(int i = 0; i < 10; i++){
            Random rndm = new Random();
            int diff = rndm.nextInt(inputs.get(size)/abweichung);
            int negate = rndm.nextInt(2);
            if(negate == 1) diff = 0-diff;
            int val = (int)Math.ceil((double)inputs.get(size)/10)*i + (int)Math.ceil((double)inputs.get(size)/20 + diff);
            if(val < 0) val = 0;
            //if(val > inputs.get(size)) val = inputs.size() -1;
            result.add(val);
        }
        return result;
    }
}
