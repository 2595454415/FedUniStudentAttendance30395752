package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromListToString(List<String> listData) {
        if (listData == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : listData) {
            sb.append(s).append(",");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }

    @TypeConverter
    public static List<String> fromStringToList(String listDataString) {
        if (listDataString == null || listDataString.isEmpty()) {
            return null;
        }
        return Arrays.asList(listDataString.split(","));
    }
}
