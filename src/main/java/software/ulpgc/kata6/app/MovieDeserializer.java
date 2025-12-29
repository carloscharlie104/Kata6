package software.ulpgc.kata6.app;

import software.ulpgc.kata5.model.Movie;

public class MovieDeserializer {
    public static Movie fromTsv(String str) {
        return fromTsv(str.split("\t"));
    }

    private static Movie fromTsv(String[] split) {
        return new Movie(split[2], toInt(split[5]), toInt(split[7]));
    }

    private static int toInt(String str) {
        if (str.equals("\\N")) return -1;
        return Integer.parseInt(str);
    }
}