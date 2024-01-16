package com.example.uasmobile.pesantren


import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R
import com.example.uasmobile.network.ApiService
import com.example.uasmobile.network.Url
import com.example.uasmobile.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PesantrenActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var rvDaftarPesantren: RecyclerView? = null
    private var modelPesantrenList: MutableList<ModelPesantren> = ArrayList()
    private var pesantrenAdapter: PesantrenAdapter? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesantren)
        toolbar = findViewById(R.id.toolbar)
        rvDaftarPesantren = findViewById(R.id.rvDaftarPesantren)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Pesantren di " + Constant.kabupatenName
        }
        progressDialog = ProgressDialog(this).apply {
            setTitle("Mohon Tunggu...")
            setCancelable(false)
            setMessage("Sedang menampilkan data")
        }
        pesantrenAdapter = PesantrenAdapter(this, modelPesantrenList)
        rvDaftarPesantren?.apply {
            layoutManager = LinearLayoutManager(this@PesantrenActivity)
            setHasFixedSize(true)
            adapter = pesantrenAdapter
        }
        Constant.kabupatenId?.let { getListPesantren(it) }
    }

    private fun getListPesantren(kabupatenId: String) {
        progressDialog?.show()

        val retrofit = Retrofit.Builder()
            .baseUrl(Url.BASEURL_PROV)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getListPesantren(kabupatenId)

        call.enqueue(object : Callback<List<ModelPesantren>> {
            override fun onResponse(
                call: Call<List<ModelPesantren>>,
                response: Response<List<ModelPesantren>>
            ) {
                progressDialog?.dismiss()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.isNotEmpty()) {
                        modelPesantrenList.addAll(responseBody)
                        pesantrenAdapter?.notifyDataSetChanged()
                    } else {
                        showToast("Ups, tidak ada data!")
                    }
                } else {
                    showToast("Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.")
                }
            }

            override fun onFailure(call: Call<List<ModelPesantren>>, t: Throwable) {
                progressDialog?.dismiss()
                showToast("Oops, terjadi kesalahan. Coba ulangi beberapa saat lagi.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@PesantrenActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
