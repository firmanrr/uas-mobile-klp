package com.example.uasmobile.provinsi

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobile.R
import com.example.uasmobile.network.ApiService
import com.example.uasmobile.network.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var rvDaftarProvinsi: RecyclerView? = null
    private var linearNoData: LinearLayout? = null
    private var modelMainList: MutableList<ModelMain> = ArrayList()
    private var mainAdapter: MainAdapter? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvDaftarProvinsi = findViewById(R.id.rvDaftarProvinsi)
        linearNoData = findViewById(R.id.linearNoData)
        progressDialog = ProgressDialog(this).apply {
            setTitle("Mohon Tunggu...")
            setCancelable(false)
            setMessage("Sedang menampilkan data")
        }
        linearNoData?.visibility = View.GONE
        mainAdapter = MainAdapter(this, modelMainList)
        rvDaftarProvinsi?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mainAdapter
        }
        getListProvinsi()
    }

    private fun getListProvinsi() {
        progressDialog?.show()

        val retrofit = Retrofit.Builder()
            .baseUrl(Url.BASEURL_PROV) // Replace with your actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getListProvinsi()

        call.enqueue(object : Callback<List<ModelMain>> {
            override fun onResponse(
                call: Call<List<ModelMain>>,
                response: Response<List<ModelMain>>
            ) {
                progressDialog?.dismiss()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.isNotEmpty()) {
                        modelMainList.addAll(responseBody)
                        mainAdapter?.notifyDataSetChanged()
                    } else {
                        showToast("Ups, tidak ada data!")
                    }
                } else {
                    showToast("Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.")
                }
            }

            override fun onFailure(call: Call<List<ModelMain>>, t: Throwable) {
                progressDialog?.dismiss()
                showToast("Oops, terjadi kesalahan. Coba ulangi beberapa saat lagi.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}
