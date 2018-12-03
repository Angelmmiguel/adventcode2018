package adventofcode.day3;

public class Claim {
    public int id;
    public int startX;
    public int startY;

    public int areaX;
    public int areaY;

    public Claim(String id, String startX, String startY, String areaX, String areaY) {
        this.id = Integer.parseInt(id);
        this.startX = Integer.parseInt(startX);
        this.startY = Integer.parseInt(startY);
        this.areaX = Integer.parseInt(areaX);
        this.areaY = Integer.parseInt(areaY);
    }

    @Override
    public String toString() {
        return String.format("Claim: %s  @  %s,%s  area: %sx%s", id, startX, startY, areaX, areaY);
    }
}
