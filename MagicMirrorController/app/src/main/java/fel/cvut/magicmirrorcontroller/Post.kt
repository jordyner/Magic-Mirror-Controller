package fel.cvut.magicmirrorcontroller

import java.io.Serializable

// Handles data for sending to the server. Basically says what item is selected and if it is suppose to put on or off.
data class Post(
    val id : Int,
    val name : String,
    val wear : Boolean
): Serializable
