package cl.fabianbaez.technews.data.remote

import cl.fabianbaez.technews.data.Remote
import cl.fabianbaez.technews.data.remote.model.RemoteNews
import cl.fabianbaez.technews.data.remote.retrofit.Webservice
import javax.inject.Inject

class RemoteImpl @Inject constructor(private val webservice: Webservice) : Remote {
    override suspend fun getNews(): RemoteNews = webservice.getNews()
}