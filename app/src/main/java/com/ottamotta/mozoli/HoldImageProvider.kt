package com.ottamotta.mozoli

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import sample.R
import kotlin.random.Random

class HoldImageProvider(val context : Context) {

    private val holdResources = arrayOf(R.drawable.hold_01, R.drawable.hold_02, R.drawable.hold_03, R.drawable.hold_04,
        R.drawable.hold_05, R.drawable.hold_06, R.drawable.hold_07, R.drawable.hold_08)

    private enum class Colors(val color : String) {

        brown("#FF410614"),
        red("#FFF13939"),
        purple("#FFAE13CE"),
        green("#FF12B600"),
        orange("#FFFF6F00"),
        black("#FF3B3B3B"),
        darkRed("#FFD60909"),
        yellow("#FFFCE51A"),
        white("#FFF2F2F2"),
        blue("#FF3983F1"),
        deepPink("#FFFF17D5"),
        limeGreen("#FFAEFF17"),
        multiColor("#FFA48B7B"),
        gray("#FFB1B1B1"),
        lightSkyBlue("#FF8CE8FF");

        companion object {
            fun byName(n : String?) = values().find{ it.name.equals(n)}?:white
        }

    }

    fun getDrawable(color : String?) : Drawable {
        val resIndex = Random.nextInt(holdResources.size)
        val drawable = context.resources.getDrawable(holdResources[resIndex])
        drawable.setColorFilter(Color.parseColor(Colors.byName(color).color), PorterDuff.Mode.SRC_IN)
        return drawable
    }

}