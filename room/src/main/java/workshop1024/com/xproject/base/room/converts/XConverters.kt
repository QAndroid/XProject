package workshop1024.com.xproject.base.room.converts

import androidx.databinding.ObservableBoolean
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class XConverters {
    @TypeConverter
    fun dateToList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromIsList(date: List<String>): String {
        return Gson().toJson(date)
    }

    @TypeConverter
    fun dateToObservableBoolean(value: Boolean?): ObservableBoolean? {
        return value?.let { ObservableBoolean(value) }
    }

    @TypeConverter
    fun fromIsObservableBoolean(date: ObservableBoolean?): Boolean? {
        return date?.get()
    }
}