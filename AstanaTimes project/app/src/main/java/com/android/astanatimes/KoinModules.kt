package com.android.astanatimes

import com.android.news.NewsModule
import org.koin.core.module.Module

object KoinModules {
    val modules: List<Module> =
        listOf(
            NewsModule.create()
        )
}
