package com.android.news.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.common.BaseActivity
import com.android.common.BaseMvpActivity
import com.android.news.R
import com.android.news.contract.EverythingContract
import com.android.news.contract.NewsDetailsContract
import com.android.news.model.Article
import com.android.news.presenter.EverythingPresenter
import com.android.news.presenter.NewsDetailsPresenter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailsActivity : BaseMvpActivity<NewsDetailsContract.View, NewsDetailsContract.Presenter>(),
NewsDetailsContract.View {

    override val presenter: NewsDetailsContract.Presenter by viewModel<NewsDetailsPresenter>()
    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        article = intent.getSerializableExtra(NEWS_DETAILS_DATA) as Article
        setupNewsDetails()
        readButton.setOnClickListener { article?.url?.let { url -> readFullArticle(url) } }
        newsDetailToolbar.setNavigationOnClickListener { finish() }
        setupToolbar()
    }

    private fun setupNewsDetails(){
        Picasso.get().load(article?.urlToImage).into(detailsImageView)
        detailsTitleTextView.text = article?.title
        detailsSourceTextView.text = article?.source?.name
        detailsAuthorTextView.text = article?.author as CharSequence?
        detailsDateTextView.text = article?.publishedAt
        detailsContentTextView.text = article?.content as CharSequence?
    }

    private fun readFullArticle(url: String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun setupToolbar(){
        newsDetailToolbar.inflateMenu(R.menu.details_menu)
        newsDetailToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.saveArticle -> {
                    presenter.saveArticle(article!!)
                    val drawable = it.icon
                    drawable.setColorFilter(resources.getColor(R.color.colorSaved), PorterDuff.Mode.SRC_IN)
                    it.icon = drawable
                    showSnackbar()
                }
            }
                true
        }
    }

    private fun showSnackbar(){
        val snackbar = Snackbar
            .make(newsDetailsActivity, "Добавлено!", Snackbar.LENGTH_SHORT)
        val text = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        text.setTextColor(resources.getColor(R.color.colorWhite))
        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorGray))
        snackbar.show()
    }
}
