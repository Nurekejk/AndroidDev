package com.android.astanatimes

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.android.news.ui.EverythingFragment
import com.android.news.ui.FavouriteNewsActivity
import com.android.news.ui.TopHeadlinesFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopHeadlinesFragment(), "Top Headlines")
        adapter.addFragment(EverythingFragment(), "Everything")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        setupToolbar()
    }

    private fun setupToolbar(){
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.favouriteNews -> {
                    val intent = Intent(this, FavouriteNewsActivity::class.java)
                    startActivity(intent)
                }
                R.id.directAuthorsMenuItem ->{
                    directAuthors()
                }
                R.id.shareMenuItem ->{
                    shareApplication()
                }
                R.id.helpMenuItem ->{
                    help()
                }
            }
            true
        }
    }

    private fun directAuthors(){
        var message = ""
        val dialog: MaterialDialog = MaterialDialog(this).show {
            input(hint = "Напишите нам о приложении..."){ materialDialog: MaterialDialog, charSequence: CharSequence ->
                charSequence.forEach { message += it }
            }
            positiveButton(R.string.submit){
                val mIntent = Intent(Intent.ACTION_SEND)
                mIntent.data = Uri.parse("mailto:")
                mIntent.type = "text/plain"
                mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("danik.encore@gmail.com"))
                mIntent.putExtra(Intent.EXTRA_SUBJECT, "The Astana Times")
                mIntent.putExtra(Intent.EXTRA_TEXT, message)
                startActivity(Intent.createChooser(mIntent, "Выберите почтовый клиент"))
            }
        }
    }

    private fun shareApplication(){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        val appLink = "https://play.google.com/store/apps/details?id=" + this.application.packageName
        intent.putExtra(Intent.EXTRA_TEXT, appLink)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "ПОДЕЛИТЬСЯ"))
    }

    private fun help(){
        MaterialDialog(this).show {
            title(text = "The Astana Times")
            message(text = resources.getText(R.string.about_app))
        }
    }


}
