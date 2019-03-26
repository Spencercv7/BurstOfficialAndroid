package betalab.ca.burstofficialandroid

/**
 * Created by Bruce on 2018-10-09.
 */
import android.content.Context
import android.view.MotionEvent
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


/**
 * This is used in MainActivity to manage the fragment swapping
 */
class NoSwipePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var localEnabled = false

    init {
        this.localEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.localEnabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.localEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.localEnabled = enabled
    }
}