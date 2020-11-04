package awards.raspberry.golden.vo;

import awards.raspberry.golden.entity.MovieEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieInterval implements Serializable {

    private static final long serialVersionUID = 1750443984749527201L;
    private List<AwardWinner> minList;
    private List<AwardWinner> maxList;

    public MovieInterval() {
        this.minList = new ArrayList<>();
        this.maxList = new ArrayList<>();
    }

    public List<AwardWinner> getMaxList() {
        return maxList;
    }

    public void setMaxList(List<AwardWinner> maxList) {
        this.maxList = maxList;
    }

    public List<AwardWinner> getMinList() {
        return minList;
    }

    public void setMinList(List<AwardWinner> minList) {
        this.minList = minList;
    }

    public boolean empty() {
        return minList.isEmpty() && maxList.isEmpty();
    }

    @Override
    public String toString() {
        return "MovieInterval{}";
    }
}
