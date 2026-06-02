public class Animation {
    private double startX, startY;
    private double endX, endY;
    private long startTime;
    private long duration;
    private Card card;

    public Animation(Card card, double startX, double startY, double endX, double endY, long duration) {
        this.card = card;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    private double getT() {
        return Math.min((System.currentTimeMillis() - startTime) / (double) duration, 1.0);
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