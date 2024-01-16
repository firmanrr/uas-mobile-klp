package com.example.uasmobile.pesantren


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R


class PesantrenAdapter(
    private var context: Context,
    private var modelPesantrenList: List<ModelPesantren>
) :
    RecyclerView.Adapter<PesantrenAdapter.ViewHolder>() {

    fun PesantrenAdapter(mContext: Context?, modelPesantren: List<ModelPesantren?>?) {
        context = mContext!!
        modelPesantrenList = modelPesantren as List<ModelPesantren>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesantrenAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_data_pesantren, parent, false)
        return com.example.uasmobile.pesantren.PesantrenAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: PesantrenAdapter.ViewHolder, position: Int) {
        val data = modelPesantrenList[position]

        holder.tvNamaPesantren.setText(data.nama)
        holder.tvNSPP.setText(data.nspp)
        holder.tvAlamat.setText(data.alamat)

        if (data.kyai.equals("")) {
            holder.tvNamaKyai.text = "-"
        } else {
            holder.tvNamaKyai.setText(data.kyai)
        }
    }

    override fun getItemCount(): Int {
        return modelPesantrenList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNamaPesantren: TextView
        var tvNSPP: TextView
        var tvAlamat: TextView
        var tvNamaKyai: TextView

        init {
            tvNamaPesantren = itemView.findViewById<TextView>(R.id.tvNamaPesantren)
            tvNSPP = itemView.findViewById<TextView>(R.id.tvNSPP)
            tvAlamat = itemView.findViewById<TextView>(R.id.tvAlamat)
            tvNamaKyai = itemView.findViewById<TextView>(R.id.tvNamaKyai)
        }
    }


}