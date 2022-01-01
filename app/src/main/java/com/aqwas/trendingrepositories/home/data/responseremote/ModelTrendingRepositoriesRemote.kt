package com.aqwas.trendingrepositories.home.data.responseremote

import com.google.gson.annotations.SerializedName

class ModelTrendingRepositoriesRemote(
    @SerializedName("author") val author: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("languageColor") val languageColor: String?,
    @SerializedName("stars") val stars: String?,
    @SerializedName("forks") val forks: String?,
    @SerializedName("currentPeriodStars") val currentPeriodStars: Int,
)

