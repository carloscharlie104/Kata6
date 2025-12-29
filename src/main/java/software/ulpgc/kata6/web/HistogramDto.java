package software.ulpgc.kata6.web;

import java.util.Map;

public class HistogramDto {
    private String title;
    private String x;
    private String legend;
    private Map<Integer, Integer> frequencies;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public void setFrequencies(Map<Integer, Integer> frequencies) {
        this.frequencies = frequencies;
    }

    public String getTitle() {
        return title;
    }

    public String getX() {
        return x;
    }

    public String getLegend() {
        return legend;
    }

    public Map<Integer, Integer> getFrequencies() {
        return frequencies;
    }
}
