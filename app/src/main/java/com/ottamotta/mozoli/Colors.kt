package com.ottamotta.mozoli

import android.graphics.Color

public enum class Colors(val color : String) {

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
        fun colorByName(n : String?) = Color.parseColor(byName(n).color)
    }

}