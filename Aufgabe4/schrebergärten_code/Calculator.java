import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Calculator {

    private List<Garden> gardenList;

    public Calculator(List<Garden> gardenList) {
        this.gardenList = gardenList;
    }

    public List<GardenInstance> calculate() {
        List<GardenInstance> best = new ArrayList<>();

        List<GardenInstance> instances = new ArrayList<>();
        GardenInstance first = new GardenInstance(new ArrayList<>(), new ArrayList<>());
        instances.add(first);
        Iterator<Garden> iterator = this.gardenList.iterator();

        while (iterator.hasNext()) {
            Garden garden = iterator.next();

            List<GardenInstance> nList = new ArrayList<>();

            for (GardenInstance instance : instances) {
                nList.addAll(instance.next(garden));
            }
            instances.clear();
            instances.addAll(nList);
            iterator.remove();
        }

        int area = Integer.MAX_VALUE;

        for (GardenInstance gi : instances) {
            if (gi.getArea() < area) {
                best.clear();
                area = gi.getArea();
                best.add(gi);
            } else if (gi.getArea() == area) {
                best.add(gi);
            }
        }

        return best;
    }


}
