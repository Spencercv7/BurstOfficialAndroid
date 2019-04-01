package betalab.ca.burstofficialandroid

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import betalab.ca.burstofficialandroid.activity.LoginActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.floating_action_button_layout.*
import android.R.attr.start
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.AnimationSet

class FloatingButton : Fragment() {

    companion object {
        fun newInstance(): FloatingButton {
            return FloatingButton()
        }
    }

    private var animTime: Long? = null
    private var animating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animTime = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.floating_action_button_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateListeners()
    }

    fun circleReveal() {
        if (!animating)
            with(ViewAnimationUtils.createCircularReveal(
                revealed_card,
                button_card_layout.right,
                button_card_layout.bottom,
                0f,
                Math.hypot(button_card_layout.width.toDouble(), button_card_layout.width.toDouble()).toFloat()
            ).apply {

                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        animating = false
                    }
                })
            }) {
                animating = true
                fab_import.hide()
                this.start()
                fab_import.elevation = 0f
                revealed_card.visibility = View.VISIBLE

            }
    }

    fun circularHide() {
        if (!animating)
            with(ViewAnimationUtils.createCircularReveal(
                revealed_card,
                button_card_layout.width,
                button_card_layout.height,
                Math.hypot(button_card_layout.width.toDouble(), button_card_layout.width.toDouble()).toFloat(),
                0f
            ).apply {

                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        revealed_card.visibility = View.INVISIBLE
                        animating = false
                    }
                })
            }) {
                fab_import.show()
                animating = true
                this.start()
                fab_import.elevation = 0f
            }
    }

    private fun instantiateListeners() {
        fab_import.setOnClickListener {
             circleReveal()
        }
        fab_in_menu.setOnClickListener {
            circularHide()
        }

        newEvent_button.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)//should be new actiity just temp
            startActivity(intent)
        }
        import_cal_button.setOnClickListener {
            Toast.makeText(activity, "Launching Import Activity", Toast.LENGTH_SHORT).show()
        }
    }
}