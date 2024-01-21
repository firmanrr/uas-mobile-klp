package com.example.uasmobile.provinsi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R
import com.example.uasmobile.kabupaten.KabupatenActivity
import com.example.uasmobile.utils.Constant


class MainAdapter(private var context: Context, private var modelMainList: List<ModelMain>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    fun MainAdapter(mContext: Context?, mainList: List<ModelMain?>?) {
        context = mContext!!
        modelMainList = mainList as List<ModelMain>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_data_provinsi, parent, false)
        return com.example.uasmobile.provinsi.MainAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val data = modelMainList[holder.adapterPosition]

        holder.tvProvinsi.setText(data.nama)
        holder.cvDaftarProvinsi.setOnClickListener {
            Constant.provinsiId = modelMainList[holder.adapterPosition].id
            Constant.provinsiName = modelMainList[holder.adapterPosition].nama
            val intent = Intent(context, KabupatenActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return modelMainList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvDaftarProvinsi: CardView
        var tvProvinsi: TextView

        init {
            cvDaftarProvinsi = itemView.findViewById<CardView>(R.id.cvDaftarProvinsi)
            tvProvinsi = itemView.findViewById<TextView>(R.id.tvProvinsi)
        }
    }
}