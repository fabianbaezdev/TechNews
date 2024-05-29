package cl.fabianbaez.technews.factory

import cl.fabianbaez.technews.data.local.model.LocalHit
import cl.fabianbaez.technews.data.remote.model.RemoteHit
import cl.fabianbaez.technews.domain.model.Hit

object HitFactory {
    fun makeHit() = Hit(
        id = "id",
        author = "author",
        createdAt = "createdAt",
        title = "title",
        url = "url"
    )

    fun makeLocalHit() = LocalHit(
        id = "id",
        author = "author",
        createdAt = "createdAt",
        title = "title",
        url = "url",
        hide = false
    )

    fun makeRemoteHit() = RemoteHit(
        author = "author",
        createdAt = "createdAt",
        storyTitle = "title",
        title = "title",
        url = "url",
        children = null,
        highlightResult = null,
        tags = null,
        commentText = null,
        createdAtI = null,
        numComments = null,
        objectID = "id",
        parentId = null,
        points = null,
        storyId = null,
        storyUrl = null,
        updatedAt = null
    )

    fun makeRemoteHitWithOnlyStoryTitle() = RemoteHit(
        author = "author",
        createdAt = "createdAt",
        storyTitle = "title",
        title = null,
        url = "url",
        children = null,
        highlightResult = null,
        tags = null,
        commentText = null,
        createdAtI = null,
        numComments = null,
        objectID = "id",
        parentId = null,
        points = null,
        storyId = null,
        storyUrl = null,
        updatedAt = null
    )

    fun makeRemoteHitWithOnlyTitle() = RemoteHit(
        author = "author",
        createdAt = "createdAt",
        storyTitle = null,
        title = "title",
        url = "url",
        children = null,
        highlightResult = null,
        tags = null,
        commentText = null,
        createdAtI = null,
        numComments = null,
        objectID = "id",
        parentId = null,
        points = null,
        storyId = null,
        storyUrl = null,
        updatedAt = null
    )

    fun makeRemoteHitWithoutData() = RemoteHit(
        author = null,
        createdAt = null,
        storyTitle = null,
        title = null,
        url = null,
        children = null,
        highlightResult = null,
        tags = null,
        commentText = null,
        createdAtI = null,
        numComments = null,
        objectID = null,
        parentId = null,
        points = null,
        storyId = null,
        storyUrl = null,
        updatedAt = null
    )
}