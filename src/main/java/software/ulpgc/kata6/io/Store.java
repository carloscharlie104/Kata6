package software.ulpgc.kata6.io;

import software.ulpgc.kata5.model.Movie;

import java.util.stream.Stream;

public interface Store {
    Stream<Movie> movies();
}
