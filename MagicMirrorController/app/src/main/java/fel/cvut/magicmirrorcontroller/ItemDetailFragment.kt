package fel.cvut.magicmirrorcontroller

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemDetailFragment : Fragment(R.layout.fragment_item_detail){
    private lateinit var nameView : TextView
    private lateinit var imageView : ImageView
    private lateinit var descriptionView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view =  inflater.inflate(R.layout.fragment_item_detail, container, false)
        nameView = view.findViewById(R.id.text_view_detail)
        imageView = view.findViewById(R.id.image_view_detail)
        descriptionView = view.findViewById(R.id.text_view_detail2)

        val item = requireArguments().getSerializable("item") as Item

        val imageBytes = Base64.decode(item.image, Base64.DEFAULT);
        val decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size);

        imageView.setImageBitmap(decodedByte)
        nameView.text = item.name
        descriptionView.text = item.description

        val try_on = view.findViewById<Button>(R.id.try_on_button)

        try_on.setOnClickListener {

            (activity as MainActivity?)?.mark_from_fragment(item.id)

            val post = Post(item.id, item.name, true);

            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8088/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(ApiService::class.java);

            api.createPost(post).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    Log.d("Connection Failure", "[SUCCESS] Data posted to the server")
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.d("Connection Failure", "[FAIL] Data was not posted to the server")
                }
            })
        }
        return view
    }
}