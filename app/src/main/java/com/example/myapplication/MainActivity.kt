package com.example.myapplication

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat


private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 20

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDesciption: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDesciption = findViewById(R.id.tvTipDesciption)
        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)

        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "OnProgressChanged $p1")
                tvTipPercentLabel.text = "$p1%"
                updateTipDescription(p1)
                calculateTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"afterTextChanged$p0")
                calculateTipAndTotal()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun updateTipDescription(tipPercent: Int){
        val tipDescription:String
        when(tipPercent){
            in 0..9 -> tipDescription = "Poor"
            in 10..14 -> tipDescription = "Acceptable"
            in 15..19 -> tipDescription = "Good"
            in 20..24 -> tipDescription = "Great"
            else -> tipDescription = "Amazing"
        }
        tvTipDesciption.text = tipDescription
        val color  = ArgbEvaluator().evaluate(tipPercent.toFloat() / seekBarTip.max,ContextCompat.getColor(this,R.color.teal_200),ContextCompat.getColor(this,R.color.teal_700))
        as Int
        tvTipDesciption.setTextColor(color)
    }
    private fun calculateTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
        }
        else {
            val baseAmount = etBaseAmount.text.toString().toDouble()
            val tipPercent = seekBarTip.progress
            val tipAmount = baseAmount * tipPercent / 100
            val totalAmount = baseAmount + tipAmount
            tvTipAmount.text = "%.2f".format(tipAmount)
            tvTotalAmount.text = "%.2f".format(totalAmount)
        }
    }
}
