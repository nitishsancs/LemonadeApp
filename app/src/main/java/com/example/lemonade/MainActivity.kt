
package com.example.lemonade

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has been drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            showSnackbar()
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }
    private fun clickLemonImage() {
        when (lemonadeState){
            SELECT -> {
                lemonadeState=SQUEEZE
                val tree: LemonTree = lemonTree
                lemonSize=tree.pick()
                squeezeCount=0
            }

            SQUEEZE -> {
                squeezeCount+=1
                lemonSize-=1
                lemonadeState = if(lemonSize==0) {
                    DRINK
                }
                else{
                    SQUEEZE
                }
            }

            DRINK -> {
                lemonadeState=RESTART
                lemonSize=-1
            }

            RESTART -> {
                lemonadeState=SELECT
            }
        }
        setViewElements()
    }

    @SuppressLint("SetTextI18n")
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        val lemonImage: ImageView= findViewById(R.id.image_lemon_state)

        when (lemonadeState){
            SELECT -> {
                textAction.text="Select Lemon from Tree the way i chose notish during adoption 19 years ago"
                lemonImage.setImageResource(R.drawable.lemon_tree)
            }

            SQUEEZE -> {
                textAction.text="Squeeze the J U I C Y J U I C Y lemons"
                lemonImage.setImageResource(R.drawable.lemon_squeeze)
            }

            DRINK -> {
                textAction.text="Drink the JUICE that has secreted by the lemon you passionately squeezed."
                lemonImage.setImageResource(R.drawable.lemon_drink)
            }

            RESTART -> {
                textAction.text="Go do her all over again like Epstein did."
                lemonImage.setImageResource(R.drawable.lemon_restart)
            }
        }
    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
