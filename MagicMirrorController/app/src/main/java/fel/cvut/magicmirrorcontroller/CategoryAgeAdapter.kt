package fel.cvut.magicmirrorcontroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Simple adapter for age category
class CategoryAgeAdapter(
    private val list : List<Category>,
    private val listener: OnCategoryClickListener,

) : RecyclerView.Adapter<CategoryAgeAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(categoryView: View) : RecyclerView.ViewHolder(categoryView),

        View.OnClickListener {
        val textView : TextView = categoryView.findViewById(R.id.category_text_view)

        init {
            categoryView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = bindingAdapterPosition

            if(position != RecyclerView.NO_POSITION){

                listener.onAgeCategoryClick(position, textView)
            }
        }
    }

    interface OnCategoryClickListener {
        fun onAgeCategoryClick(position: Int, textView: TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        var categoryView = LayoutInflater.from(parent.context).inflate(R.layout.category_item,
            parent, false)
        return CategoryViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView.text = currentItem.name
    }

    override fun getItemCount() = list.size
}