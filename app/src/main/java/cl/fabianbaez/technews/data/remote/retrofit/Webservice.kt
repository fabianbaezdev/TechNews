package cl.fabianbaez.technews.data.remote.retrofit

import cl.fabianbaez.technews.data.remote.model.RemoteNews
import retrofit2.http.GET

interface Webservice {
    @GET("search_by_date?query=mobile")
    suspend fun getNews(): RemoteNews
}