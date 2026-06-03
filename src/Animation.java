public class Animation {
    private double startX, startY;
    private double endX, endY;
    private long startTime;
    private long duration;
    private Card card;
    private boolean isGrid;

    public Animation(Card card, double startX, double startY, double endX, double endY, long duration, boolean isGrid) {
        this.card = card;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
        this.isGrid = isGrid;
    }

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    private double getT() {
        return (System.currentTimeMillis() - startTime) / (double) duration;
    }

    public double getCurrentX() {
        return startX + (endX - startX) * getT();
    }

    public double getCurrentY() {
        return startY + (endY - startY) * getT();
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= duration;
    }

    public Card getCard() {
        return card;
    }
}