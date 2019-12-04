package workshop1024.com.xproject.main.publisher.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import workshop1024.com.xproject.main.publisher.data.PublisherType

//ObservableBoolean无法存入Room：TypeConverter error of error: Cannot figure out how to save field to database"
//参考：https://developer.android.com/training/data-storage/room/referencing-data
//FIXME 怎么区分应用在哪个类型
class Converters {
    @TypeConverter
    fun dataToPublisherType(value: String): PublisherType {
        return Gson().fromJson(value, PublisherType::class.java)
    }

    @TypeConverter
    fun fromPublisherType(publisherType: PublisherType): String {
        return Gson().toJson(publisherType)
    }
}