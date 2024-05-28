package cl.fabianbaez.technews.factory

import cl.fabianbaez.technews.data.remote.model.RemoteNews
import cl.fabianbaez.technews.factory.HitFactory.makeRemoteHit

object NewsFactory {
    fun makeRemoteNews() = RemoteNews(
        hits = listOf(makeRemoteHit()),
        exhaustive = null,
        exhaustiveNbHits = null,
        exhaustiveTypo = null,
        hitsPerPage = null,
        nbHits = null,
        nbPages = null,
        page = null,
        params = null,
        processingTimeMS = null,
        processingTimingsMS = null,
        query = null,
        serverTimeMS = null
    )
}