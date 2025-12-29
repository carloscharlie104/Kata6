package software.ulpgc.kata6.io;

import software.ulpgc.kata5.model.Movie;

import java.util.stream.Stream;

public interface Recorder {
    void record(Stream<Movie> movies);
}
