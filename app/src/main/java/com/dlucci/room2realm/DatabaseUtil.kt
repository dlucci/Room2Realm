package com.dlucci.room2realm

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Entity
data class DayOfWeek(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "day") val day: String?,
    @ColumnInfo(name = "time") val time: String?)

@Dao
interface DayOfWeekDao {
    @Query("SELECT * from DayOfWeek")
    fun getAll(): Single<List<DayOfWeek>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dayOfWeek: List<DayOfWeek>) : Completable
}

@Database(entities = arrayOf(DayOfWeek::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayOfWeekDao() : DayOfWeekDao
}