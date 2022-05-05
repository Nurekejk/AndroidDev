package com.android.news.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.common.BaseMvpActivity
import com.android.news.R
import com.android.news.contract.FavouriteNewsContract
import com.android.news.contract.NewsDetailsContract
import com.android.news.model.Article
import com.android.news.presenter.FavouriteNewsPresenter
import com.android.news.presenter.NewsDetailsPresenter
import kotlinx.android.synthetic.main.activity_favourite_news.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteNewsActivity : BaseMvpActivity<FavouriteNewsContract.View, FavouriteNewsContract.Presenter>(),
    FavouriteNewsContract.View  {

    override val presenter: FavouriteNewsContract.Presenter by viewModel<FavouriteNewsPresenter>()
    private var favouriteNewsAdapter: FavouriteNewsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_news)
        favouriteNewsToolbar.setNavigationOnClickListener { finish() }
        presenter.loadData()
        favouriteNewsAdapter = FavouriteNewsAdapter(
            newsClickListener = {
                showNewsDetails(it)
            },
            deleteClickListener = { article, position ->
                favouriteNewsAdapter?.removeItem(position)
                presenter.deleteItem(article)
            }
        )
        val newsManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        favouritesRecyclerView.apply {
            layoutManager = newsManager
            adapter = favouriteNewsAdapter
        }
    }

    override fun show(articles: List<Article>) {
        favouriteNewsAdapter?.addItems(articles)
    }

    override fun showNewsDetails(article: Article) {
        val intent = Intent(this, NewsDetailsActivity::class.java).apply {
            putExtra(NEWS_DETAILS_DATA, article)
        }
        startActivity(intent)
    }
}
