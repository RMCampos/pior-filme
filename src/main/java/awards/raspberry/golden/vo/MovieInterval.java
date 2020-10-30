package awards.raspberry.golden.vo;

import awards.raspberry.golden.entity.MovieEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieInterval implements Serializable {

    private static final long serialVersionUID = 1750443984749527201L;
    private List<MovieEntity> min;
    private List<MovieEntity> max;

    public MovieInterval() {
        this.min = new ArrayList<>();
        this.max = new ArrayList<>();
    }

    public List<MovieEntity> getMin() {
        return min;
    }

    public void setMin(List<MovieEntity> min) {
        this.min = min;
    }

    public List<MovieEntity> getMax() {
        return max;
    }

    public void setMax(List<MovieEntity> max) {
        this.max = max;
    }
}
