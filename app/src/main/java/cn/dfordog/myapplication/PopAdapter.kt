package cn.dfordog.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopAdapter(
    private val data: List<String>
) : RecyclerView.Adapter<PopAdapter.ViewHolder>() {

    inner class ViewHolder(item:View): RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_spinner,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.itemTv).text = data[position]
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}