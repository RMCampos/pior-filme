package awards.raspberry.golden.vo;

import java.io.Serializable;
import java.util.Objects;

public class AwardWinner implements Serializable {

    private static final long serialVersionUID = 157162745102289323L;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AwardWinner that = (AwardWinner) o;
        return interval == that.interval &&
                previousWin == that.previousWin &&
                followingWin == that.followingWin &&
                producer.equals(that.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, interval, previousWin, followingWin);
    }
}
