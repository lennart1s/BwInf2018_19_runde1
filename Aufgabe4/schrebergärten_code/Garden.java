public class Garden {

    private int x, y;

    public Garden(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getArea() {
        return this.x * this.y;
    }

    public boolean widthLonger() {
        return this.x > this.y;
    }

}
