package com.example.calculadoradeproprina

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val totalPerPerson = intent.getStringExtra("totalPerPerson") ?: "€0"
        val totalBill = intent.getStringExtra("totalBill") ?: "€0"
        val totalTip = intent.getStringExtra("totalTip") ?: "€0"

        findViewById<TextView>(R.id.totalPerPersonText).text = totalPerPerson
        findViewById<TextView>(R.id.totalBillText).text = totalBill
        findViewById<TextView>(R.id.totalTipText).text = totalTip

        findViewById<MaterialButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
} 