package software.ulpgc.kata6.app;

import software.ulpgc.kata5.io.Recorder;
import software.ulpgc.kata5.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {
    private final Connection connection;
    private final PreparedStatement preparedStatement;
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final int BATCH_SIZE = 1000;

    public DatabaseRecorder(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
        this.preparedStatement = connection.prepareStatement(
                "INSERT INTO movies (title, duration, year) VALUES (?, ?, ?)"
        );
    }

    private void createTableIfNotExists() throws SQLException {
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS movies (" +
                        "title TEXT, duration INTEGER, year INTEGER)"
        );
    }

    @Override
    public void record(Stream<Movie> movies) {
        try {
            movies.forEach(this::record);
            flushBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void flushBatch() throws SQLException {
        preparedStatement.executeBatch();
        connection.commit();
    }

    private void record(Movie movie) {
        try {
            preparedStatement.setString(1, movie.title());
            preparedStatement.setInt(2, movie.duration());
            preparedStatement.setInt(3, movie.year());

            preparedStatement.addBatch();

            if (counter.incrementAndGet() % BATCH_SIZE == 0) {
                preparedStatement.executeBatch();
                connection.commit();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

