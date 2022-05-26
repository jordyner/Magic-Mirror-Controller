package fel.cvut.magicmirrorcontroller

import java.io.Serializable

// Represents each category
data class Category (
    val id: Int,
    val name : String,
) : Serializable
