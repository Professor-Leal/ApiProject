package com.rafaelleal.android.apiproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelleal.android.apiproject.R
import com.rafaelleal.android.apiproject.adapters.NewsAdapter
import com.rafaelleal.android.apiproject.adapters.NewsListener
import com.rafaelleal.android.apiproject.api.models.Article
import com.rafaelleal.android.apiproject.databinding.FragmentNewsBinding
import com.rafaelleal.android.apiproject.utils.isInternetAvailable
import com.rafaelleal.android.apiproject.utils.nav
import com.rafaelleal.android.apiproject.utils.toast
import com.rafaelleal.android.apiproject.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.newsArticles.collect { articles ->
                adapter.submitList(articles)
                withContext(Dispatchers.Main) {
                    binding.rvPesquisa.adapter = adapter
                }
            }
        }
        if (!isInternetAvailable()) {
            toast("Internet Não disponível")
        }
    }

    val adapter = NewsAdapter(
        object : NewsListener {
            override fun onItemClick(article: Article) {
                viewModel.setSelectedArticle(article)
                nav(R.id.action_newsFragment_to_selectedNewsFragment)
            }
        }
    )

    private fun setupRecyclerView() {
        binding.rvPesquisa.adapter = adapter
        binding.rvPesquisa.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun setup() {
        setupViews()
        setupClickListeners()
        setupRecyclerView()
    }

    private fun setupClickListeners() {
        binding.tilPesquisa.setEndIconOnClickListener {
            onSearchClick()
        }
    }

    fun onSearchClick() {
        val input = binding.inputPesquisa.text.toString()

        if (!isInternetAvailable()) {
            toast("Internet Não disponível")
        } else {
            if (input.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Insira algo a ser pesquisado",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.collectNews(input)
            }
        }
    }

    private fun setupViews() {
        activity?.setTitle("Pesquisar Notícias")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}