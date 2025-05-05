package com.example.mycalc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var previousCalculationTextView: TextView

    private var firstNumber = 0.0
    private var operation = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultTextView = findViewById(R.id.resultTextView)
        previousCalculationTextView = findViewById(R.id.previousCalculationTextView)

        // Initialize number buttons
        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)

        // Initialize operation buttons
        val add: Button = findViewById(R.id.btnPlus)
        val sub: Button = findViewById(R.id.btnMinus)
        val mul: Button = findViewById(R.id.btnMultiply)
        val div: Button = findViewById(R.id.btnDivide)
        val equal: Button = findViewById(R.id.btnEquals)
        val clear: Button = findViewById(R.id.btnClear)
        val backSpace: Button = findViewById(R.id.btnBackspace)
        val dot: Button = findViewById(R.id.btnDot)
        val percent: Button = findViewById(R.id.btnPercent)

        // Set click listeners for number buttons
        btn0.setOnClickListener { appendNumber("0") }
        btn1.setOnClickListener { appendNumber("1") }
        btn2.setOnClickListener { appendNumber("2") }
        btn3.setOnClickListener { appendNumber("3") }
        btn4.setOnClickListener { appendNumber("4") }
        btn5.setOnClickListener { appendNumber("5") }
        btn6.setOnClickListener { appendNumber("6") }
        btn7.setOnClickListener { appendNumber("7") }
        btn8.setOnClickListener { appendNumber("8") }
        btn9.setOnClickListener { appendNumber("9") }
        dot.setOnClickListener { appendNumber(".") }

        // Set click listeners for operation buttons
        percent.setOnClickListener { setOperation("%") }
        add.setOnClickListener { setOperation("+") }
        sub.setOnClickListener { setOperation("-") }
        mul.setOnClickListener { setOperation("*") }
        div.setOnClickListener { setOperation("÷") }

        equal.setOnClickListener { calculateResult() }
        clear.setOnClickListener { clearCalculator() }
        backSpace.setOnClickListener { deleteNum() }
    }

    private fun deleteNum() {
        val currentText = resultTextView.text.toString()
        when {
            currentText == "Error" -> clearCalculator()
            currentText.length > 1 -> resultTextView.text = currentText.dropLast(1)
            else -> resultTextView.text = "0"
        }
    }

    private fun clearCalculator() {
        firstNumber = 0.0
        operation = ""
        isNewOperation = true
        resultTextView.text = "0"
        previousCalculationTextView.text = ""
    }

    private fun calculateResult() {
        try {
            val secondNumber = resultTextView.text.toString().toDouble()
            val result = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%" -> firstNumber % secondNumber
                else -> secondNumber
            }

            previousCalculationTextView.text = "$firstNumber $operation $secondNumber ="
            resultTextView.text = when {
                result.isNaN() -> "Error"
                result == result.toInt().toDouble() -> result.toInt().toString()
                else -> result.toString()
            }
            isNewOperation = true
        } catch (e: Exception) {
            resultTextView.text = "Error"
        }
    }

    private fun setOperation(operator: String) {
        try {
            firstNumber = resultTextView.text.toString().toDouble()
            operation = operator
            isNewOperation = true
            previousCalculationTextView.text = "$firstNumber $operation"
            resultTextView.text = "0"
        } catch (e: Exception) {
            resultTextView.text = "Error"
        }
    }

    private fun appendNumber(number: String) {
        val currentText = resultTextView.text.toString()

        if (isNewOperation) {
            resultTextView.text = if (number == ".") "0." else number
            isNewOperation = false
        } else {
            when {
                number == "." && currentText.contains(".") -> return
                currentText == "0" && number != "." -> resultTextView.text = number
                else -> resultTextView.text = currentText + number
            }
        }
    }
}