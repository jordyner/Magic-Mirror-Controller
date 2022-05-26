package fel.cvut.magicmirrorcontroller

import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener, CategoryAgeAdapter.OnCategoryClickListener, CategoryTypeAdapter.OnCategoryClickListener{
    // Global variables for holding data from server
    private lateinit var list_of_items : ArrayList<Item>
    private lateinit var list_of_age_categories : ArrayList<Category>
    private lateinit var list_of_type_categories : ArrayList<Category>
    private lateinit var general_information : General

    // These variables are suppose to unmark categories that are not selected
    var age_last_selected : TextView? = null
    var type_last_selected : TextView? = null

    // It is set to 1000 because it is default value so 1000 means All
    var current_age_category : Int = 1000;

    // When app is launched, spacing is added because want some space between each item in list_of_items but when we filter items then we dont want more and more space
    var spacing : Boolean = true

    // This is for green/grey button on each item and it controls changing backgrounds
    var state : Boolean = false;

    // We need to track which items are marked and mark them even after filtering values
    var marked_items = arrayListOf<Int>()

    // If items is clicked, I store a button which was clicked and in case we get information from fragment that it was put on then we need to make potential_candidate_for_mark green.
    var potential_candidate_for_mark : Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8088/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java);
        get_data(api);
    }

    // This is what happens when we click on item in item list
    override fun onItemClick(position: Int, button: Button) {
        potential_candidate_for_mark = button;

        val itemDetail = ItemDetailFragment()
        val bdl = Bundle()
        bdl.putSerializable("item", list_of_items[position])
        itemDetail.setArguments(bdl)
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_holder, itemDetail)
            addToBackStack("replacement")
            commit()
        }

    }

    // If we click on green/grey button in the right upper corner then it changes color and in future we will send post request to the server.
    override fun onClickButton(position: Int, button: Button) {
        state = !state;
        if(state){
            button.setBackgroundResource(R.drawable.on)
            marked_items += list_of_items[position].id;
        } else {
            button.setBackgroundResource(R.drawable.off)
            marked_items -= list_of_items[position].id;
        }
    }

    // this is sent from fragment in case a button "Nasadit" was pressed
    fun mark_from_fragment(id : Int) {
        marked_items += id;
        potential_candidate_for_mark?.setBackgroundResource(R.drawable.on);
    }

    // On category click we need to show approriate items and we need to change background
    override fun onAgeCategoryClick(position: Int, textView: TextView) {
        age_last_selected?.setBackgroundResource(R.drawable.border)
        age_last_selected = textView

        showItems(list_of_items, list_of_age_categories[position].id, 0)
        current_age_category = list_of_age_categories[position].id;

        textView.setBackgroundResource(R.drawable.border_for_detail);
    }

    override fun onTypeCategoryClick(position: Int, textView: TextView) {
        type_last_selected?.setBackgroundResource(R.drawable.border)
        type_last_selected = textView

        showItems(list_of_items, current_age_category, list_of_type_categories[position].id,)

        textView.setBackgroundResource(R.drawable.border_for_detail);
    }

    // This method shows appropriate items. At the beginning it shows all but when we want to filter then it shows only the relevant items.
    fun showItems(body : List<Item>, age_category : Int, type_category : Int): ArrayList<Item> {
        val list = ArrayList<Item>()
        // I prepared only 5 test items but to present more items than 5 I just the same images but they have different ids.
        for(i in 0..(general_information.data_count)*3){
            if(i < 5){
                if(body[i].age_category == age_category || age_category == 1000){
                    if(body[i].type_category == type_category || type_category == 0){
                        val item = Item(i, body[i].image, body[i].name, body[i].description, body[i].age_category, body[i].type_category);
                        list += item;
                    }
                }
            } else if(i >= 5 && i < 10){
                if(body[i-5].age_category == age_category || age_category == 1000){
                    if(body[i-5].type_category == type_category || type_category == 0){
                        val item = Item(i, body[i-5].image, body[i-5].name, body[i-5].description, body[i-5].age_category, body[i-5].type_category);
                        list += item;
                    }
                }

            } else if(i >= 10 && i < 15){
                if(body[i-10].age_category == age_category || age_category == 1000){
                    if(body[i-10].type_category == type_category || type_category == 0){
                        val item = Item(i, body[i-10].image, body[i-10].name, body[i-10].description, body[i-10].age_category, body[i-10].type_category);
                        list += item;
                    }
                }
            }

        }

        val adapter = ItemAdapter(list, this, marked_items)
        val recycler_view : RecyclerView = findViewById(R.id.recycler_view)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)

        if(spacing) {
            recycler_view.addItemDecoration(GridSpacingItemDecoration(4,25,true))
            spacing = false
        }

        d("Item sync", "Items were shown in the view with age_category: " + age_category + " and typecategory: " + type_category)
        return list;
    }

    // this is executed only once when app is launched and fills recyclerview with categories
    fun showAgeCategories(body : List<Category>): ArrayList<Category> {
        val list = ArrayList<Category>()
        for(i in 0..general_information.age_category_count){
            val category = Category(body[i].id, body[i].name);
            list += category;
        }

        val adapter = CategoryAgeAdapter(list, this)
        val recycler_view_cats_age : RecyclerView = findViewById(R.id.recycler_view_cats_age)
        recycler_view_cats_age.adapter = adapter
        recycler_view_cats_age.setHasFixedSize(true)

        return list;
    }

    // this is executed only once when app is launched and fills recyclerview with categories
    fun showCategoriesType(body : List<Category>): ArrayList<Category> {
        val list = ArrayList<Category>()
        for(i in 0..general_information.type_category_count){
            val category = Category(i, body[i].name);
            list += category;
        }

        val adapter = CategoryTypeAdapter(list, this)
        val recycler_view_cats_type : RecyclerView = findViewById(R.id.recycler_view_cats_type)
        recycler_view_cats_type.adapter = adapter
        recycler_view_cats_type.setHasFixedSize(true)

        return list;
    }

    // we need to fetch general information first so we need to make sure that this api call is executed first.
    private fun get_data(api : ApiService){
        api.fetchGeneral().enqueue(object : Callback<General> {
            override fun onResponse(
                call: Call<General>,
                response: Response<General>
            ) {
                d("Connection Success", "General data were received..")
                general_information = General(response.body()!!.data_count-1, response.body()!!.age_category_count-1, response.body()!!.type_category_count-1)
                fill_recycler_views(api)
            }

            override fun onFailure(call: Call<General>, t: Throwable) {
                d("Connection Failure", "General data NOT RECEIVED.")
            }
        })
    }

    // following api calls can be executed in random order because they are not dependent on each other
    private fun fill_recycler_views(api : ApiService) {
        api.fetchAllItems().enqueue(object : Callback<List<Item>> {
        override fun onResponse(
            call: Call<List<Item>>,
            response: Response<List<Item>>
        ) {
            d("Connection Success", "Items were received..")
            list_of_items = showItems(response.body()!!, 1000, 0)
        }

        override fun onFailure(call: Call<List<Item>>, t: Throwable) {
            d("Connection Failure", "Items NOT RECEIVED.")
        }
    })

        api.fetchAllAgeCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                d("Connection Success", "Age categories were received..")
                list_of_age_categories = showAgeCategories(response.body()!!)
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                d("Connection Failure", "Age categories NOT RECEIVED")
            }
        })

        api.fetchAllTypeCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                d("Connection Success", "Type categories were received..")
                list_of_type_categories = showCategoriesType(response.body()!!)
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                d("Connection Failure", "Type categories NOT RECEIVED")
            }
        })
    }
}



