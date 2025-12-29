package software.ulpgc.kata6.web;

import software.ulpgc.kata6.viewmodel.Histogram;

import java.util.HashMap;
import java.util.Map;

public class HistogramAdapter {
    public HistogramDto adapt(Histogram histogram) {
        HistogramDto dto = new HistogramDto();
        dto.setTitle(histogram.title());
        dto.setLegend(histogram.legend());

        Map<Integer, Integer> freq = new HashMap<>();
        for (int bin : histogram) {
            freq.put(bin, histogram.count(bin));
        }
        dto.setFrequencies(freq);
        return dto;
    }
}
