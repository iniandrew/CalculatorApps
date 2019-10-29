package com.andrew.calculatorapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listNumber()
        listOperator()
    }

    // List all number in this function
    private fun listNumber() {
        btn_nine.setOnClickListener { onDigit(it) }
        btn_eight.setOnClickListener { onDigit(it) }
        btn_seven.setOnClickListener { onDigit(it) }
        btn_six.setOnClickListener { onDigit(it) }
        btn_five.setOnClickListener { onDigit(it) }
        btn_four.setOnClickListener { onDigit(it) }
        btn_three.setOnClickListener { onDigit(it) }
        btn_two.setOnClickListener { onDigit(it) }
        btn_one.setOnClickListener { onDigit(it) }
        btn_zero.setOnClickListener { onDigit(it) }
    }

    // List all operator in this function
    private fun listOperator() {
        btn_clear.setOnClickListener { onClear() }
        btn_remove.setOnClickListener { onRemove() }
        btn_modulus.setOnClickListener { onOperator(it) }
        btn_divide.setOnClickListener { onOperator(it) }
        btn_multiply.setOnClickListener { onOperator(it) }
        btn_subtract.setOnClickListener { onOperator(it) }
        btn_add.setOnClickListener { onOperator(it) }
        btn_dot.setOnClickListener { onDecimalPoint() }
        btn_equal.setOnClickListener { onEqual() }
    }


    private fun onDigit(view: View) {
        if (stateError) {
            tv_result.text = (view as Button).text
            stateError = false
        } else {
            tv_result.append((view as Button).text)
        }
        lastNumeric = true
    }

    private fun onDecimalPoint() {
        if (lastNumeric && !stateError && !lastDot) {
            tv_result.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            tv_result.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    /**
     * Clear the TextView
     */
    private fun onClear() {
        this.tv_result.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    /**
     * Delete last pressed key
     */
    private fun onRemove() {
        if (tv_result.text.isNotEmpty()) {
            tv_result.text = tv_result.text.substring(0, tv_result.text.length - 1)
        }
    }

    /**
     * Calculate the output using Exp4j
     */
    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = tv_result.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                // Calculate
                val result = expression.evaluate()
                tv_result.text = result.toInt().toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                tv_result.text = "Error!"
                stateError = true
                lastNumeric = false
            }
        }
    }

}
