import java.util.ArrayList;
import java.util.List;

public class GardenInstance {

    public List<PositionedGarden> gardens;
    public List<Area> areas;

    public GardenInstance(List<PositionedGarden> gardens, List<Area> areas) {
        this.gardens = gardens;
        this.areas = areas;
    }

    public List<GardenInstance> next(Garden newGarden) {
        List<GardenInstance> gardenInstances = new ArrayList<>();

        List<Vector2D> allEntries = new ArrayList<>();

        for (Area area : this.areas) {
           int xa = area.a.getX();
           int xb = area.b.getX();
           int ya = area.a.getY();
           int yb = area.b.getY();

           allEntries.add(new Vector2D(xa - newGarden.getX(), ya));
           allEntries.add(new Vector2D(xb, ya));
           allEntries.add(new Vector2D(xa, yb));
           allEntries.add(new Vector2D(xb - newGarden.getX(), yb));
           allEntries.add(new Vector2D(xb, yb - newGarden.getY()));
           allEntries.add(new Vector2D(xa - newGarden.getY(), yb - newGarden.getY()));
           allEntries.add(new Vector2D(xa, ya - newGarden.getY()));
           allEntries.add(new Vector2D(xb - newGarden.getX(), ya - newGarden.getY()));
        }

        for (Vector2D entry : new ArrayList<>(allEntries)) {
            Area a = new Area(new Vector2D(entry.getX(), entry.getY()), new Vector2D(entry.getX() + newGarden.getX(), entry.getY() + newGarden.getY()));
            for (Area area : this.areas) {
                if (a.overlaps(area)) {
                    allEntries.remove(entry);
                    break;
                }
            }
        }

        if (allEntries.isEmpty() && this.gardens.isEmpty()) {
            allEntries.add(new Vector2D(0, 0));
        }

        for (Vector2D entry : allEntries) {
            List<PositionedGarden> nGardens = new ArrayList<>(this.gardens);
            List<Area> nAreas = new ArrayList<>(this.areas);
            Area a = new Area(new Vector2D(entry.getX(), entry.getY()), new Vector2D(entry.getX() + newGarden.getX(), entry.getY() + newGarden.getY()));

            nGardens.add(new PositionedGarden(newGarden.getX(), newGarden.getY(), a));
            nAreas.add(a);
            gardenInstances.add(new GardenInstance(nGardens, nAreas));
        }

        return gardenInstances;
    }

    public int getArea() {
        int x_min = 0;
        int y_min = 0;
        int x_max = 0;
        int y_max = 0;

        for (PositionedGarden garden : this.gardens) {
            if (garden.getPositionedArea().b.getX() > x_max) {
                x_max = garden.getPositionedArea().b.getX();
            }
            if (garden.getPositionedArea().b.getY() > y_max) {
                y_max = garden.getPositionedArea().b.getY();
            }
            if (garden.getPositionedArea().a.getX() < x_min) {
                x_min = garden.getPositionedArea().a.getX();
            }
            if (garden.getPositionedArea().a.getY() < y_min) {
                y_min = garden.getPositionedArea().a.getY();
            }
        }

        return (x_max - x_min) * (y_max - y_min);
    }

    public Area getAreaObject() {
        int x_min = 0;
        int y_min = 0;
        int x_max = 0;
        int y_max = 0;

        for (PositionedGarden garden : this.gardens) {
            if (garden.getPositionedArea().b.getX() > x_max) {
                x_max = garden.getPositionedArea().b.getX();
            }
            if (garden.getPositionedArea().b.getY() > y_max) {
                y_max = garden.getPositionedArea().b.getY();
            }
            if (garden.getPositionedArea().a.getX() < x_min) {
                x_min = garden.getPositionedArea().a.getX();
            }
            if (garden.getPositionedArea().a.getY() < y_min) {
                y_min = garden.getPositionedArea().a.getY();
            }
        }

        return new Area(new Vector2D(x_min, y_min), new Vector2D(x_max, y_max));
    }

}
