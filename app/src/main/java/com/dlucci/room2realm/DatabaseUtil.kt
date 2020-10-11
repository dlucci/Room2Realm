package com.dlucci.room2realm

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Entity
data class DayOfWeek(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "day") val day: String?)

@Dao
interface DayOfWeekDao {
    @Query("SELECT * from DayOfWeek")
    fun getAll(): Single<List<DayOfWeek>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dayOfWeek: List<DayOfWeek>) : Completable
}

@Database(entities = arrayOf(DayOfWeek::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayOfWeekDao() : DayOfWeekDao
}