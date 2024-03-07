package com.macropay.prueba.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macropay.prueba.R
import com.macropay.prueba.ui.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}