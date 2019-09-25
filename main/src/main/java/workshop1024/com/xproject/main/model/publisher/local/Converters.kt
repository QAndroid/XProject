package workshop1024.com.xproject.main.model.publisher.local

import androidx.databinding.ObservableBoolean
import androidx.room.TypeConverter

//ObservableBoolean无法存入Room：TypeConverter error of error: Cannot figure out how to save field to database"
//参考：https://developer.android.com/training/data-storage/room/referencing-data
class Converters {
    @TypeConverter
    fun fromIsSubscribed(value: Boolean?): ObservableBoolean? {
        return value?.let { ObservableBoolean(value) }
    }

    @TypeConverter
    fun dateToIsSubscribed(date: ObservableBoolean?): Boolean? {
        return date?.get()
    }
}