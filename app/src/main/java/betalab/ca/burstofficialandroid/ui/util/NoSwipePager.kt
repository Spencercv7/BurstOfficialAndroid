package betalab.ca.burstofficialandroid.ui.util

/**
 * Created by Bruce on 2018-10-09.
 */
import android.content.Context
import android.view.MotionEvent
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


/**
 * This is used in MainFragment to manage the fragment swapping
 */
class NoSwipePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var localEnabled = false

    init {
        this.localEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        return false
    }
    @SuppressWarnings("")
    override fun performClick(): Boolean {
        return super.performClick()
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