package workshop1024.com.xproject.feedback.model.message.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import workshop1024.com.xproject.feedback.model.message.Message

class Converters {
    @TypeConverter
    fun dataToMessageList(value: String): List<Message> {
        val type = object : TypeToken<List<Message>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromMessageList(data: List<Message>): String {
        return Gson().toJson(data)
    }
}