package com.xinsu.heartrate.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xinsu.heartrate.ui.widgets.RootHudLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        initUI()
    }

    /**
     * 初始化 UI
     */
    private fun initUI() {

        val root =
            RootHudLayout(this)

        setContentView(root)
    }
}
