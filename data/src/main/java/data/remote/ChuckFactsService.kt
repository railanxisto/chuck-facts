package br.com.railanxisto.chuckfacts.data.remote

import br.com.railanxisto.chuckfacts.data.remote.config.RetrofitManager
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckFactsService {

    companion object {
        fun create(): ChuckFactsService {
            val retrofit = RetrofitManager()
            return retrofit.retrofitInstance().create(ChuckFactsService::class.java)
        }
    }

    @GET("jokes/categories")
    fun getCategories(): Single<List<String>>

    @GET("jokes/search")
    fun getFacts(@Query("query") query: String): Single<Response<FactsResponse>>
}