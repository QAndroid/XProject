package workshop1024.com.xproject.home.model.subinfo.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import workshop1024.com.xproject.home.model.subinfo.SubInfo

@Dao
interface SubInfoDao {

    @Query("SELECT * FROM subinfoes WHERE infoType = :infoType")
    fun getSubinfoesByinfoType(infoType: String): List<SubInfo>

    @Query("DELETE FROM subinfoes WHERE infoId = :subInfoId")
    fun unSubInfoById(subInfoId: String): Int

    @Query("UPDATE subinfoes SET name = :customName WHERE infoId = :subinfoId")
    fun reNameSubInfoById(subinfoId: String, customName: String)

    @Query("DELETE FROM subinfoes WHERE infoType = :infoType")
    fun deleteAllSubInfoes(infoType: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubInfo(subInfo: SubInfo)

    @Query("UPDATE subinfoes SET unreadCount = '0' WHERE infoId = :subinfoId")
    fun markedSubInfoAsReadById(subinfoId: String)
}
