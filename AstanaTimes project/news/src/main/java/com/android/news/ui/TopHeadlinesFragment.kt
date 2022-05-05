package com.android.news.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.common.BaseFragment
import com.android.news.R
import com.android.news.contract.TopHeadlinesContract
import com.android.news.model.Article
import com.android.news.presenter.TopHeadlinesPresenter
import kotlinx.android.synthetic.main.fragment_everything.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NEWS_DETAILS_DATA = "NEWS_DETAILS_DATA"

class TopHeadlinesFragment : BaseFragment<TopHeadlinesContract.View, TopHeadlinesContract.Presenter>(),
    TopHeadlinesContract.View {

    override val presenter: TopHeadlinesContract.Presenter by viewModel<TopHeadlinesPresenter>()
    private var newsAdapter: NewsAdapter? = null
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_top_headlines, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData(KEYWORD, PAGE_SIZE)
        presenter.reloadData()
        setupAdapter()
    }

    override fun setupAdapter() {
        newsAdapter = NewsAdapter(
            newsClickListener = {
                showNewsDetails(it)
            }
        )
        val newsManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        topHeadlinesRecyclerView.apply {
            layoutManager = newsManager
            adapter = newsAdapter
        }
        topHeadlinesRecyclerView.addOnScrollListener(object : PaginationScrollListener(newsManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                presenter.incrementPage()
                presenter.loadMoreData(KEYWORD, PAGE_SIZE)
                isLoading = false
            }

        })
    }

    override fun show(articles: List<Article>) {
        newsAdapter?.addItems(articles)
    }

    override fun showProgressBar() {
        topHeadlinesProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        topHeadlinesProgressBar.visibility = View.GONE
    }

    override fun showNewsDetails(article: Article) {
        val intent = Intent(context, NewsDetailsActivity::class.java).apply {
            putExtra(NEWS_DETAILS_DATA, article)
        }
        startActivity(intent)
    }

    override fun showMore(articles: List<Article>) {
        newsAdapter?.appendData(articles)
    }


}