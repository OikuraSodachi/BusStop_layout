package com.todokanai.busstop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.todokanai.busstop.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }
}