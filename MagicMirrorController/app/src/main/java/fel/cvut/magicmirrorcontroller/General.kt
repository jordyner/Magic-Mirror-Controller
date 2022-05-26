package fel.cvut.magicmirrorcontroller

import java.io.Serializable

// Represents general data for recyclerviews
data class General (
    val data_count: Int,
    val age_category_count: Int,
    val type_category_count: Int,
) : Serializable
