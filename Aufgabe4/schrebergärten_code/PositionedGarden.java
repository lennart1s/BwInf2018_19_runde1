public class PositionedGarden extends Garden {

    private Area area;

    public PositionedGarden(int x, int y, Area area) {
        super(x, y);
        this.area = area;
    }

    public Area getPositionedArea() {
        return this.area;
    }

}
