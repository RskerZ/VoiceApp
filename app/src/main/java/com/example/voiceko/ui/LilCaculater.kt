package com.example.voiceko.ui

//import kotlinx.android.synthetic.main.activity_enter_data.*

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.voiceko.R
import kotlinx.android.synthetic.main.lilcaculater_fragment.*
import net.objecthunter.exp4j.ExpressionBuilder


class LilCaculater(var actname: String): Fragment() {

    /*Function to calculate the expressions using expression builder library*/
    private var editTextNumber:TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.lilcaculater_fragment, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lil","onViewCreated")

       when(actname) {
           "Enter"-> {
               editTextNumber = activity!!.findViewById(R.id.editTextNumber)
           }
           "Fixedcost"-> {
               editTextNumber = activity!!.findViewById(R.id.fixedcost_editTextNumber)
           }
           else -> {
               Log.e("lil", activity.toString())
           }
       }
        tvOne.setOnClickListener {
            evaluateExpression("1", clear = true)
        }
        tvTwo.setOnClickListener {
            evaluateExpression("2", clear = true)
        }
        tvThree.setOnClickListener {
            evaluateExpression("3", clear = true)
        }
        tvFour.setOnClickListener {
            evaluateExpression("4", clear = true)
        }
        tvFive.setOnClickListener {
            evaluateExpression("5", clear = true)
        }
        tvSix.setOnClickListener {
            evaluateExpression("6", clear = true)
        }
        tvSeven.setOnClickListener {
            evaluateExpression("7", clear = true)
        }
        tvEight.setOnClickListener {
            evaluateExpression("8", clear = true)
        }
        tvNine.setOnClickListener {
            evaluateExpression("9", clear = true)
        }
        tvZero.setOnClickListener {
            evaluateExpression("0", clear = true)
        }
        /*Operators*/
        tvPlus.setOnClickListener {
            evaluateExpression("+", clear = true)
        }
        tvMinus.setOnClickListener {
            evaluateExpression("-", clear = true)
        }
        tvMul.setOnClickListener {
            evaluateExpression("*", clear = true)
        }
        tvDivide.setOnClickListener {
            evaluateExpression("/", clear = true)
        }
        tvDoubleZero.setOnClickListener {
            evaluateExpression("00", clear = true)
        }
        tvClear.setOnClickListener {
            editTextNumber?.text = ""
        }

        tvEquals.setOnClickListener {
            val text = editTextNumber?.text.toString()
            val expression = ExpressionBuilder(text).build()

            val result = expression.evaluate()
            val longResult = result.toLong()
            if (result == longResult.toDouble()) {
                editTextNumber?.text = longResult.toString()
                activity!!.onBackPressed()
            } else {
                editTextNumber?.text = result.toString()
                activity!!.onBackPressed()
            }

        }

        tvBack.setOnClickListener {
            val text = editTextNumber?.text.toString()
            if(text.isNotEmpty()) {
                editTextNumber?.text = text.drop(1)
            }
            editTextNumber?.text = ""
        }
    }


    fun evaluateExpression(string: String, clear: Boolean) {
        if(clear) {
            editTextNumber?.append(string)
        } else {
            editTextNumber?.append(editTextNumber?.text)
            editTextNumber?.append(string)
            editTextNumber?.text = ""
        }
    }

}