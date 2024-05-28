package cl.fabianbaez.technews.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteHit(
    @SerializedName("_highlightResult") val highlightResult: RemoteHighlightResult?,
    @SerializedName("_tags") val tags: List<String>?,
    val author: String?,
    val children: List<Int?>?,
    @SerializedName("comment_text") val commentText: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("created_at_i") val createdAtI: Int?,
    @SerializedName("num_comments") val numComments: Int?,
    val objectID: String?,
    @SerializedName("parent_id") val parentId: Int?,
    val points: Int?,
    @SerializedName("story_id") val storyId: Int?,
    @SerializedName("story_title") val storyTitle: String?,
    @SerializedName("story_url") val storyUrl: String?,
    val title: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val url: String?
)