package com.example.uasmobile.network

import com.example.uasmobile.kabupaten.ModelKabupaten
import com.example.uasmobile.pesantren.ModelPesantren
import com.example.uasmobile.provinsi.ModelMain
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/provinsi.json")
    fun getListProvinsi(): Call<List<ModelMain>>

    @GET("kabupaten/{id_provinsi}.json")
    fun getListKabupaten(@Path("id_provinsi") idProvinsi: String): Call<List<ModelKabupaten>>

    @GET("/pesantren/{id_kab_kota}.json")
    fun getListPesantren(@Path("id_kab_kota") idKabupaten: String): Call<List<ModelPesantren>>
}


