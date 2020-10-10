package com.dlucci.room2realm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlucci.room2realm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.data.layoutManager = LinearLayoutManager(this)
        binding.data.adapter = MyAdapter(days)
    }
}