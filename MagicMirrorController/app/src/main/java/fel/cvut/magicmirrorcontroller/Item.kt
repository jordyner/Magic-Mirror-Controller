package fel.cvut.magicmirrorcontroller

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.Serializable

// Represents a piece of clothes
data class Item (
    var id: Int,
    val image: String,
    val name : String,
    val description: String,
    val age_category: Int,
    val type_category: Int,
) : Serializable