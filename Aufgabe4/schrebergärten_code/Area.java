public class Area {

    public Vector2D a;
    public Vector2D b;

    public Area(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
    }

    public boolean overlaps(Area area) {
        Vector2D other_a = area.a;
        Vector2D other_b = area.b;
        int x1 = a.getX();
        int x2 = b.getX();
        int y1 = a.getY();
        int y2 = b.getY();

        int ox1 = other_a.getX();
        int ox2 = other_b.getX();
        int oy1 = other_a.getY();
        int oy2 = other_b.getY();

        if (x1 < ox2 && x2 > ox1) {
            if(y1 < oy2 && y2 > oy1) {
                return true;
            }
        }

        return false;
    }

}
