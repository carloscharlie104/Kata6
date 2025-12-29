package software.ulpgc.kata6.web;

import software.ulpgc.kata6.io.Store;
import software.ulpgc.kata6.model.Movie;
import software.ulpgc.kata6.tasks.HistogramBuilder;
import software.ulpgc.kata6.viewmodel.Histogram;

import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramService {
    private  final Store store;

    public HistogramService(Store store) {
        this.store = store;
    }

    public Histogram buildHistogram(String field, Integer from, Integer to) {
        Stream<Movie> movies = store.movies();

        if (from != null && to != null) {
            final int f = (from != null) ? from : Integer.MIN_VALUE;
            final int t = (to != null) ? to : Integer.MAX_VALUE;
            movies = movies.filter(m -> {
                int value = valueOfField(field, m);
                return value >= f && value <= t;
            });
        }

        Function<Movie, Integer> binarize = movie -> valueOfField(field, movie);

        return HistogramBuilder.with(movies)
                .title("Histograma de " + field)
                .x(field)
                .legend("Nº de películas")
                .build(binarize);
    }

    private int valueOfField(String field, Movie m) {
        return switch (field == null ? "year" : field) {
            case "duration" -> m.duration();
            case "year" -> m.year();
            default -> m.year();
        };
    }
}
