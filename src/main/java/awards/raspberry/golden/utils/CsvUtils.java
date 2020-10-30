package awards.raspberry.golden.utils;

public class CsvUtils {

    public static String[] findColumns(String line, char separator) {
        if (line.indexOf(separator) == -1) {
            throw new RuntimeException("Given line doesn't have given separator!");
        }

        return line.split(String.valueOf(separator));
    }

    public static char findSeparator(String line) {
        if (line.indexOf(';') > -1) {
            return ';';
        }

        if (line.indexOf(',') > -1) {
            return ',';
        }

        if (line.indexOf('|') > -1) {
            return '|';
        }

        if (line.indexOf('#') > -1) {
            return '#';
        }

        return ' ';
    }
}
