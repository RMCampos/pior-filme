package awards.raspberry.golden.vo;

public class AwardWinner {

    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

    public AwardWinner() {
        this.producer = "";
        this.interval = 0;
        this.previousWin = 0;
        this.followingWin = 0;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(int previousWin) {
        this.previousWin = previousWin;
    }

    public int getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(int followingWin) {
        this.followingWin = followingWin;
    }
}
