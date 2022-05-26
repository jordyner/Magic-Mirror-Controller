package fel.cvut.magicmirrorcontroller

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(
    private val list : List<Item>,
    private val listener: OnItemClickListener,
    private val marked_list : List<Int>

) : RecyclerView.Adapter<ItemAdapter.ExampleViewHolder>() {

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            val textView : TextView = itemView.findViewById(R.id.text_view)
            val button : Button = itemView.findViewById(R.id.quick_try_on_button)

        init {
            itemView.setOnClickListener(this)

            button.setOnClickListener {
                listener.onClickButton(list[bindingAdapterPosition].id, button)
            }
        }

        override fun onClick(v: View?){
            val position = bindingAdapterPosition

            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position, button)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, button: Button)
        fun onClickButton(id: Int, button: Button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,
            parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        var currentItem = list[position]
        val imageBytes = Base64.decode(currentItem.image.toString(), Base64.DEFAULT);
        val decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size);

        holder.imageView.setImageBitmap(decodedByte)
        holder.textView.text = currentItem.name
        holder.button.setBackgroundResource(R.drawable.off)

        // ensures that all things that are suppose to be marked are really marked. If I have marked something in all type and I choose to filter then I need to mark items that were marked previously
        if(marked_list.size > 0){
            for(marked_item in marked_list) {
                if (currentItem.id == marked_item) {
                    holder.button.setBackgroundResource(R.drawable.on)
                }
            }
        }
    }
    override fun getItemCount() = list.size
}