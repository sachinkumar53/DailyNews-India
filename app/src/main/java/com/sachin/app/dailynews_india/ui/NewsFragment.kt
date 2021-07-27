package com.sachin.app.dailynews_india.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.paginate.Paginate
import com.sachin.app.dailynews_india.ArticlesAdapter
import com.sachin.app.dailynews_india.R
import com.sachin.app.dailynews_india.api.NewsApiClient
import com.sachin.app.dailynews_india.api.NewsApiService
import kotlinx.android.synthetic.main.news_fragment.*

private const val ARG_CATEGORY = "category"

class NewsFragment : Fragment() {

    private var category: String? = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { category = it.getString(ARG_CATEGORY) }
    }

    private lateinit var viewModel: NewsViewModel
    private val adapter by lazy { ArticlesAdapter() }
    private val newsApiService by lazy { NewsApiClient.client!!.create(NewsApiService::class.java) }
    private var loading = true
    private var page = 1

    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    private val callback = object : Paginate.Callbacks {
        override fun onLoadMore() {
            loading = true
            page = page.inc()
            loadNews()
        }

        override fun isLoading() = loading

        override fun hasLoadedAllItems() =
            linearLayoutManager.itemCount == viewModel.getTotalResults()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.news_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingStub = view.findViewById<ViewStub>(R.id.loading_stub)
        loadingStub.visibility = View.VISIBLE

        news_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NewsFragment.adapter
        }

        (ViewModelProvider(this).get(NewsViewModel::class.java)).apply {
            getArticleLiveData().observe(this@NewsFragment) {
                if (loadingStub.visibility == View.VISIBLE)
                    loadingStub.visibility = View.GONE
                adapter.submitList(it)
            }
        }.also {
            viewModel = it
            loadNews()
        }

        adapter.setOnItemClickListener {
            try {
                Log.d("TAG", "onViewCreated: ${it.content}")
                startActivity(Intent(Intent(requireContext(), WebViewActivity::class.java).apply {
                    putExtra("url", it.url)
                }))
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error showing this news", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        Paginate.with(news_recycler_view, callback)
            .addLoadingListItem(true)
            .setLoadingTriggerThreshold(4)
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun loadNews() {
        viewModel.loadNews(newsApiService, category, page) {
            loading = false
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(category: String) = NewsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, category)
            }
        }
    }

}