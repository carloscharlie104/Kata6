package software.ulpgc.kata6.web;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import software.ulpgc.kata6.app.DatabaseRecorder;
import software.ulpgc.kata6.app.DatabaseStore;
import software.ulpgc.kata6.app.MovieDeserializer;
import software.ulpgc.kata6.app.RemoteStore;
import software.ulpgc.kata6.io.Store;
import software.ulpgc.kata6.model.Movie;
import software.ulpgc.kata6.viewmodel.Histogram;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class WebMain {
    private static final String DATABASE = "movies.db";

    public static void main(String[] args) throws SQLException {
        boolean created = !new File(DATABASE).exists();

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE)) {
            connection.setAutoCommit(false);

            if (created) {
                System.out.println("Importing movies into Sqlite...");
                Stream<Movie> movies = new RemoteStore(MovieDeserializer::fromTsv).movies();
                new DatabaseRecorder(connection).record(movies);
                System.out.println("Movies imported!");
            }

            Store store = new DatabaseStore(connection);

            Javalin app = Javalin.create(
                    JavalinConfig config -> {
                        config.defaultContentType = "application/json";
                    }
            ).start(7000);

            HistogramService histogramService = new HistogramService(store);
            HistogramAdapter adapter = new HistogramAdapter();
            HistogramController controller = new HistogramController(histogramService, adapter);

            app.get("/histogram", controller::getHistogram);

            System.out.println("Service running at http://localhost:7000");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
