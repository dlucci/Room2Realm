package com.dlucci.room2realm

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dlucci.room2realm.databinding.ActivityMainBinding
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var db : AppDatabase

    val viewModel: MainViewModel by viewModels()

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE DayOfWeek ADD COLUMN time TEXT")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initVars()
    }

    fun initVars() {

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "room2realm"
        ).addMigrations(MIGRATION_1_2)
            .build()

        viewModel.db = db

        insertIntoDb()

        loadRecyclerView()
    }

    private fun loadRecyclerView() {

        viewModel.dayList.observe(this, {
            binding.data.adapter = MyAdapter(it)
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.data.layoutManager = LinearLayoutManager(this)

        viewModel.getDays()
    }

    private fun insertIntoDb() {

        val listOfDays = getDaysOfWeek()

        db.dayOfWeekDao().insertAll(listOfDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    private fun getDaysOfWeek(): List<DayOfWeek> {
        val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var retVal = ArrayList<DayOfWeek>()
        for ( i in 0..6) {
            var day = DayOfWeek(i, days[i], "15:35")
            retVal.add(day)
        }

        return retVal
    }
}

class MainViewModel(@NonNull application: Application) : AndroidViewModel(application) {

    var dayList = MutableLiveData<List<DayOfWeek>>()

    lateinit var db : AppDatabase

    fun getDays() {
        db.dayOfWeekDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                dayList.postValue(it)
            },{
                Toast.makeText(getApplication(), it.localizedMessage, Toast.LENGTH_SHORT).show()
            })
    }

}