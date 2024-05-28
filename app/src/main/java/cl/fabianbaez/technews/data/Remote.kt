package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.data.remote.model.RemoteNews

interface Remote {
    suspend fun getNews(): RemoteNews
}