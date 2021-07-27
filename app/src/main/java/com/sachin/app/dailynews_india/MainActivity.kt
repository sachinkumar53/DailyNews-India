package com.sachin.app.dailynews_india

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.material.tabs.TabLayoutMediator
import com.sachin.app.dailynews_india.api.NewsApiClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        NewsApiClient.init(this)

        main_pager.adapter = NewsPagerAdapter(this, categories)
        main_pager.offscreenPageLimit = categories.size

        TabLayoutMediator(tab_layout, main_pager) { tab, position ->
            tab.text =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                    categories[position].capitalize(resources.configuration.locales[0])
                else categories[position].capitalize(resources.configuration.locale)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.findViewById<LinearLayout>(R.id.search_bar).layoutTransition = LayoutTransition()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*if (item.itemId == R.id.action_search) {
            TransitionManager.beginDelayedTransition(toolbar)
            item.expandActionView()
        }*/

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity"
        private val categories = arrayOf(
            "general",
            "science",
            "technology",
            "health",
            "sports",
            "entertainment",
            "business"
        )
    }
}