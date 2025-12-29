package software.ulpgc.kata6.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer> {
    private final Map<String, String> labels;
    private final Map<Integer, Integer> frequencies;

    public Histogram(Map<String, String> labels) {
        this.labels = labels;
        this.frequencies = new HashMap<>();
    }

    public void add(int bin) {
        frequencies.put(bin, count(bin) + 1);
    }

    public int count(int bin) {
        return frequencies.getOrDefault(bin, 0);
    }

    @Override
    public Iterator<Integer> iterator() {
        return frequencies.keySet().iterator();
    }

    public String title() {
        return labels.getOrDefault("title", "");

    }

    public String x() {
        return labels.getOrDefault("x", "");
    }

    public String legend() {
        return labels.getOrDefault("legend", "");
    }
}
