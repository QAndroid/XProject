package workshop1024.com.xproject.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Android Room List转换器，用户Room保存List类型数据
 */
public class ListConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToStringList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stringListToString(List<String> stringList) {
        return gson.toJson(stringList);
    }
}
