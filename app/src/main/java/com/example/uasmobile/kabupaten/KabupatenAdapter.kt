package com.example.uasmobile.kabupaten


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R
import com.example.uasmobile.pesantren.PesantrenActivity
import com.example.uasmobile.utils.Constant

class KabupatenAdapter(
    private var context: Context,
    private var modelKabupatenList: List<ModelKabupaten>
) : RecyclerView.Adapter<KabupatenAdapter.ViewHolder>() {

    fun KabupatenAdapter(mContext: Context?, modelKabupaten: List<ModelKabupaten?>?) {
        context = mContext!!
        modelKabupatenList = modelKabupaten as List<ModelKabupaten>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KabupatenAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_data_kabupaten, parent, false)
        return com.example.uasmobile.kabupaten.KabupatenAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: KabupatenAdapter.ViewHolder, position: Int) {
        val data = modelKabupatenList[position]

        holder.tvKabupaten.setText(data.nama)
        holder.cvDaftarKabupaten.setOnClickListener {
            Constant.kabupatenId = modelKabupatenList[holder.adapterPosition].id
            Constant.kabupatenName = modelKabupatenList[holder.adapterPosition].nama
            val intent = Intent(context, PesantrenActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return modelKabupatenList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvDaftarKabupaten: CardView
        var tvKabupaten: TextView

        init {
            cvDaftarKabupaten = itemView.findViewById<CardView>(R.id.cvDaftarKabupaten)
            tvKabupaten = itemView.findViewById<TextView>(R.id.tvKabupaten)
        }
    }
}
