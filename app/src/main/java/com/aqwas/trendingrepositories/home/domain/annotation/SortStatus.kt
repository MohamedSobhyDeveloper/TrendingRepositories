package com.aqwas.trendingrepositories.home.domain.annotation

import androidx.annotation.IntDef


@IntDef(
    SortStatus.SORT_DEFAULT,
    SortStatus.SORT_NAME,
    SortStatus.SORT_STAR
)
@Retention(AnnotationRetention.SOURCE)
annotation class SortStatus {
    companion object {
        const val SORT_DEFAULT = 1
        const val SORT_NAME = 2
        const val SORT_STAR = 3
    }
}

