package betalab.ca.burstofficialandroid.ui.util

import android.content.Context

class ConversionUtils {
    companion object {
        fun dpToPixels(context: Context, dp: Int): Int = dp * context.resources.displayMetrics.density.toInt()
    }
}