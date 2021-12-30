package com.aqwas.trendingrepositories.home.domain.response

class ModelTrendingRepositories(
    var author: String?,
    var name: String?,
    var avatar: String?,
    var url: String?,
    var description: String?,
    var language: String?,
    var languageColor: String?,
    var stars: Int,
    var forks: Int,
    var currentPeriodStars: Int,
)

