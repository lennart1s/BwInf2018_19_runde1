import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Display extends JFrame {

    public Display(String addendum){
        super("BWINF Aufgabe 4 - Lösung " + addendum);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(1500, 800);
    }

    public void display(GardenInstance instance) {
        Area a = instance.getAreaObject();
        int x1 = a.a.getX();
        int x2 = a.b.getX();
        int y1 = a.a.getY();
        int y2 = a.b.getY();


        int offset_x = Math.abs(x1);
        int offset_y = Math.abs(y1);

        int x = x2 - x1;
        int y = y2 - y1;

        setSize(x + 500, y + 250);

        int multiplier = 1;
        if(x * y < 150) {
            multiplier = 10;
        }

        for(PositionedGarden garden : instance.gardens) {
            JPanel graph = new JPanel();
            Area positionedArea = garden.getPositionedArea();
            graph.setBorder(BorderFactory.createLineBorder(Color.blue));
            graph.setBounds(multiplier * (offset_x + garden.getPositionedArea().a.getX()), multiplier * (offset_y + garden.getPositionedArea().a.getY()), multiplier * (positionedArea.b.getX() - positionedArea.a.getX()), multiplier * (positionedArea.b.getY() - positionedArea.a.getY()));
            this.add(graph);
        }

    }

}
