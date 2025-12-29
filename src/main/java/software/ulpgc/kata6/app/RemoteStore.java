package software.ulpgc.kata6.app;

import software.ulpgc.kata5.io.Store;
import software.ulpgc.kata5.model.Movie;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class RemoteStore implements Store {
    private static final String url = "https://datasets.imdbws.com/title.basics.tsv.gz";
    private final Function<String, Movie> deserialize;

    public RemoteStore(Function<String, Movie> deserialize) {
        this.deserialize = deserialize;
    }

    @Override
    public Stream<Movie> movies() {
        try {
            return moviesIn(new URL(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> moviesIn(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        InputStream in = new GZIPInputStream(new BufferedInputStream(connection.getInputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.lines().skip(1).map(deserialize).onClose(()->{
            try {reader.close();} catch (IOException e) {}
        });
    }


    private Stream<Movie> loadFrom(InputStream inputStream)  {
        return loadFrom(toReader(inputStream));
    }

    private Stream<Movie> loadFrom(BufferedReader reader)  {
        return reader.lines().skip(1).map(deserialize);
    }

    private BufferedReader toReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private InputStream unzip(InputStream inputStream) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(inputStream));
    }
}