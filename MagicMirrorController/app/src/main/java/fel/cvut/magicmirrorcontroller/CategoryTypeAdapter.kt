package fel.cvut.magicmirrorcontroller

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// same thing as CategoryAgeAdapter but it is for Type filter
class CategoryTypeAdapter(
    private val list : List<Category>,
    private val listener: OnCategoryClickListener

) : RecyclerView.Adapter<CategoryTypeAdapter.CategoryTypeViewHolder>() {

    inner class CategoryTypeViewHolder(categoryTypeView: View) : RecyclerView.ViewHolder(categoryTypeView),

        View.OnClickListener {
        val textView : TextView = categoryTypeView.findViewById(R.id.category_text_view2)

        init {
            categoryTypeView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = bindingAdapterPosition

            if(position != RecyclerView.NO_POSITION){
                listener.onTypeCategoryClick(position, textView)
            }
        }
    }

    interface OnCategoryClickListener {
        fun onTypeCategoryClick(position: Int, textView: TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTypeViewHolder {

        var categoryTypeView = LayoutInflater.from(parent.context).inflate(R.layout.category_item2,
            parent, false)
        return CategoryTypeViewHolder(categoryTypeView)
    }

    override fun onBindViewHolder(holder: CategoryTypeViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView.text = currentItem.name
    }

    override fun getItemCount() = list.size
}