package com.example.calculadoradeproprina

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var billAmountInput: TextInputEditText
    private lateinit var customTipInput: TextInputEditText
    private lateinit var tip10Button: MaterialButton
    private lateinit var tip15Button: MaterialButton
    private lateinit var tip20Button: MaterialButton
    private lateinit var calculateButton: MaterialButton
    private lateinit var minusButton: MaterialButton
    private lateinit var plusButton: MaterialButton
    private lateinit var peopleCountText: TextView

    private var selectedTipPercent: Double? = null
    private var peopleCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        billAmountInput = findViewById(R.id.billAmountInput)
        customTipInput = findViewById(R.id.customTipInput)
        tip10Button = findViewById(R.id.tip10Button)
        tip15Button = findViewById(R.id.tip15Button)
        tip20Button = findViewById(R.id.tip20Button)
        calculateButton = findViewById(R.id.calculateButton)
        minusButton = findViewById(R.id.minusButton)
        plusButton = findViewById(R.id.plusButton)
        peopleCountText = findViewById(R.id.peopleCountText)

        // Botones de porcentaje
        tip10Button.setOnClickListener {
            selectedTipPercent = 10.0
            customTipInput.setText("")
            highlightTipButton(10)
        }
        tip15Button.setOnClickListener {
            selectedTipPercent = 15.0
            customTipInput.setText("")
            highlightTipButton(15)
        }
        tip20Button.setOnClickListener {
            selectedTipPercent = 20.0
            customTipInput.setText("")
            highlightTipButton(20)
        }

        // Propina personalizada
        customTipInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                selectedTipPercent = null
                highlightTipButton(null)
            }
        }

        // Botones de personas
        minusButton.setOnClickListener {
            if (peopleCount > 1) {
                peopleCount--
                peopleCountText.text = peopleCount.toString()
            }
        }
        plusButton.setOnClickListener {
            peopleCount++
            peopleCountText.text = peopleCount.toString()
        }

        // Calcular
        calculateButton.setOnClickListener {
            val billAmount = billAmountInput.text.toString().toDoubleOrNull()
            val tipPercent = selectedTipPercent ?: customTipInput.text.toString().toDoubleOrNull()

            if (billAmount == null || billAmount <= 0) {
                Snackbar.make(calculateButton, "Por favor, ingresa un monto válido.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (tipPercent == null || tipPercent < 0) {
                Snackbar.make(calculateButton, "Selecciona un porcentaje o ingresa una propina personalizada.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipAmount = billAmount * (tipPercent / 100.0)
            val totalAmount = billAmount + tipAmount
            val perPerson = if (peopleCount > 0) totalAmount / peopleCount else totalAmount

            val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
            val totalPerPersonStr = currencyFormat.format(perPerson)
            val totalBillStr = currencyFormat.format(billAmount)
            val totalTipStr = currencyFormat.format(tipAmount)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("totalPerPerson", totalPerPersonStr)
            intent.putExtra("totalBill", totalBillStr)
            intent.putExtra("totalTip", totalTipStr)
            startActivity(intent)
        }
    }

    private fun highlightTipButton(selected: Int?) {
        val azul = ContextCompat.getColor(this, R.color.colorPrimary)
        val branco = ContextCompat.getColor(this, android.R.color.white)

        // Botón 10%
        if (selected == 10) {
            tip10Button.setBackgroundColor(azul)
            tip10Button.setTextColor(branco)
        } else {
            tip10Button.setBackgroundColor(branco)
            tip10Button.setTextColor(azul)
        }
        // Botón 15%
        if (selected == 15) {
            tip15Button.setBackgroundColor(azul)
            tip15Button.setTextColor(branco)
        } else {
            tip15Button.setBackgroundColor(branco)
            tip15Button.setTextColor(azul)
        }
        // Botón 20%
        if (selected == 20) {
            tip20Button.setBackgroundColor(azul)
            tip20Button.setTextColor(branco)
        } else {
            tip20Button.setBackgroundColor(branco)
            tip20Button.setTextColor(azul)
        }
    }
}