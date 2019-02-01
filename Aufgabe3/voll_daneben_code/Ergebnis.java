import java.util.ArrayList;
import java.util.List;

public class Ergebnis {

    List<Integer> gewinnZahlen;
    int gewinn;

    public Ergebnis(){
        gewinnZahlen = new ArrayList<>();
        gewinn = 0;
    }

    public Ergebnis(List<Integer> gewinnZahlen, int gewinn){
        this.gewinnZahlen = gewinnZahlen;
        this.gewinn = gewinn;
    }

    public int getGewinn(){return gewinn;}

    public void setGewinn(int gewinn){this.gewinn = gewinn;}

    public List<Integer> getGewinnZahlen(){return gewinnZahlen;}

    public void setGewinnZahlen(List<Integer> gewinnZahlen){this.gewinnZahlen = gewinnZahlen;}
}
