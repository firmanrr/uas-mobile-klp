package com.example.uasmobile.kabupaten

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R
import com.example.uasmobile.kabupaten.KabupatenAdapter
import com.example.uasmobile.kabupaten.ModelKabupaten
import com.example.uasmobile.network.ApiService
import com.example.uasmobile.network.Url
import com.example.uasmobile.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KabupatenActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var rvDaftarKabupaten: RecyclerView? = null
    private var modelKabupatenList: MutableList<ModelKabupaten> = ArrayList()
    private var kabupatenAdapter: KabupatenAdapter? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kabupaten)
        toolbar = findViewById(R.id.toolbar)
        rvDaftarKabupaten = findViewById(R.id.rvDaftarKabupaten)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Kabupaten " + Constant.provinsiName
        }
        progressDialog = ProgressDialog(this).apply {
            setTitle("Mohon Tunggu...")
            setCancelable(false)
            setMessage("Sedang menampilkan data")
        }
        kabupatenAdapter = KabupatenAdapter(this, modelKabupatenList)
        rvDaftarKabupaten?.apply {
            layoutManager = LinearLayoutManager(this@KabupatenActivity)
            setHasFixedSize(true)
            adapter = kabupatenAdapter
        }
        Constant.provinsiId?.let { getListKabupaten(it) }
    }

    private fun getListKabupaten(strIdProv: String) {
        progressDialog?.show()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Url.BASEURL_PROV)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)

                val call = apiService.getListKabupaten(strIdProv)
                call.enqueue(object : Callback<List<ModelKabupaten>> {
                    override fun onResponse(
                        call: Call<List<ModelKabupaten>>,
                        response: Response<List<ModelKabupaten>>
                    ) {
                        progressDialog?.dismiss()

                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && responseBody.isNotEmpty()) {
                                for (data in responseBody) {
                                    val dataApi = ModelKabupaten().apply {
                                        id = data.id
                                        nama = data.nama
                                    }
                                    modelKabupatenList.add(dataApi)
                                }
                                kabupatenAdapter?.notifyDataSetChanged()
                            } else {
                                showToast("Ups, tidak ada data!")
                            }
                        } else {
                            showToast("Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.")
                        }
                    }

                    override fun onFailure(call: Call<List<ModelKabupaten>>, t: Throwable) {
                        progressDialog?.dismiss()
                        showToast("Oops, terjadi kesalahan. Coba ulangi beberapa saat lagi.")
                    }
                })
            } catch (e: Exception) {
                showToast("Oops, terjadi kesalahan. Coba ulangi beberapa saat lagi.")
            } finally {
                progressDialog?.dismiss()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@KabupatenActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}