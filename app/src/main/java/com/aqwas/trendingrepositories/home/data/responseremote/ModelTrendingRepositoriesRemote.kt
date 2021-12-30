package com.aqwas.trendingrepositories.home.data.responseremote

import com.google.gson.annotations.SerializedName

class ModelTrendingRepositoriesRemote(
    @SerializedName("author") var author: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("avatar") var avatar: String?,
    @SerializedName("url") var url: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("language") var language: String?,
    @SerializedName("languageColor") var languageColor: String?,
    @SerializedName("stars") var stars: Int,
    @SerializedName("forks") var forks: Int,
    @SerializedName("currentPeriodStars") var currentPeriodStars: Int,
)

