package fel.cvut.magicmirrorcontroller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8088/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api : ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}