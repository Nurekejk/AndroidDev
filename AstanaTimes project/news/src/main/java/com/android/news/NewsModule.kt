package com.android.news


import androidx.room.Room
import com.android.common.InjectionModule
import com.android.news.interactor.NewsInteractor
import com.android.news.presenter.EverythingPresenter
import com.android.news.presenter.FavouriteNewsPresenter
import com.android.news.presenter.NewsDetailsPresenter
import com.android.news.presenter.TopHeadlinesPresenter
import com.android.news.repository.api.ApiService
import com.android.news.repository.room.AppDatabase
import com.android.news.repository.room.ArticleRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object NewsModule : InjectionModule {
    override fun create() = module {
        viewModel { TopHeadlinesPresenter(get()) }
        viewModel { EverythingPresenter(get()) }
        viewModel { NewsDetailsPresenter(get()) }
        viewModel { FavouriteNewsPresenter(get()) }

        single { ApiService.create() }
        single { NewsInteractor(get(), get()) }
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "myDB").fallbackToDestructiveMigration().build() }
        single { ArticleRepository(get<AppDatabase>().articleDAO()) }
    }
}
