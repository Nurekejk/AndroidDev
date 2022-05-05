package com.android.news.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.common.BaseFragment
import com.android.news.R
import com.android.news.contract.EverythingContract
import com.android.news.model.Article
import com.android.news.presenter.EverythingPresenter
import kotlinx.android.synthetic.main.fragment_everything.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val KEYWORD = "apple"
const val PAGE_SIZE = 15

class EverythingFragment : BaseFragment<EverythingContract.View, EverythingContract.Presenter>(),
    EverythingContract.View {

    override val presenter: EverythingContract.Presenter by viewModel<EverythingPresenter>()
    private var newsAdapter: NewsAdapter? = null
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_everything, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData(KEYWORD, PAGE_SIZE)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadData(KEYWORD, PAGE_SIZE)
            swipeRefreshLayout.isRefreshing = false
        }
        setupAdapter()


    }

    override fun setupAdapter() {
        newsAdapter = NewsAdapter(
            newsClickListener = {showNewsDetails(it)}
        )
        val newsManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        everythinRecyclerView.apply {
            layoutManager = newsManager
            adapter = newsAdapter
        }

        everythinRecyclerView.addOnScrollListener(object : PaginationScrollListener(newsManager){
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