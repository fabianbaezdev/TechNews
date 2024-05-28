package cl.fabianbaez.technews.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteHighlightResult(
    val author: RemoteNewsObject?,
    @SerializedName("comment_text") val commentText: RemoteNewsObject?,
    @SerializedName("story_title") val storyTitle: RemoteNewsObject?,
    @SerializedName("story_url") val storyUrl: RemoteNewsObject?,
    val title: RemoteNewsObject?,
    val url: RemoteNewsObject?
)