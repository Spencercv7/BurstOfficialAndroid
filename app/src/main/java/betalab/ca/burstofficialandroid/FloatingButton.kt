package betalab.ca.burstofficialandroid

import android.content.Intent
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
import com.google.android.material.button.MaterialButton

private lateinit var fabButton: FloatingActionButton
private lateinit var buttonCard: MaterialCardView
private lateinit var buttonCardLayout : ConstraintLayout
private lateinit var eventButton: MaterialButton
private lateinit var importButton: MaterialButton

class FloatingButton : Fragment() {

    companion object {
        fun newInstance() : FloatingButton{
            return FloatingButton()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.floating_action_button_layout,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabButton = view.findViewById(R.id.fab_import)
        buttonCard = view.findViewById(R.id.revealed_card)
        buttonCardLayout = view.findViewById(R.id.button_card_layout)
        eventButton = view.findViewById(R.id.newEvent_button)
        importButton = view.findViewById(R.id.import_cal_button)


        fabButton.setOnClickListener {
            if (buttonCard.visibility == (View.VISIBLE)) {
                buttonCard.visibility = (View.GONE)
            } else {
                runAnimation()
            }
        }

        eventButton.setOnClickListener {
            val intent = Intent(activity, NewEvent::class.java)
            startActivity(intent)
        }

        importButton.setOnClickListener {
            Toast.makeText(activity,"Launching Import Activity",Toast.LENGTH_SHORT).show()
        }
    }

    fun runAnimation() {
        val cornerX = (buttonCardLayout.right)
        val cornerY = (buttonCardLayout.bottom)
        val startRad = 0
        val endRad  = Math.hypot(buttonCardLayout.width.toDouble(), buttonCardLayout.width.toDouble()).toInt()
        val anim =
            ViewAnimationUtils.createCircularReveal(buttonCard, cornerX, cornerY, startRad.toFloat(), endRad.toFloat())
        anim.start()
        buttonCard.visibility = (View.VISIBLE)
    }
}