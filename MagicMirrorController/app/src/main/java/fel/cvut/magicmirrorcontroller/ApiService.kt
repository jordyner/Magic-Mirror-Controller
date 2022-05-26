package fel.cvut.magicmirrorcontroller

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// following methods are used by retrofit to proceed REST operations.
interface ApiService {
    @GET("/items/")
    fun fetchAllItems() : Call<List<Item>>

    @GET("/categories/age/")
    fun fetchAllAgeCategories() : Call<List<Category>>

    @GET("/categories/type/")
    fun fetchAllTypeCategories() : Call<List<Category>>

    @GET("/general")
    fun fetchGeneral() : Call<General>

    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>
}