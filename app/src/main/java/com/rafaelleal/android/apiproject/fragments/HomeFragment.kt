package com.rafaelleal.android.apiproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelleal.android.apiproject.R
import com.rafaelleal.android.apiproject.adapters.NewsAdapter
import com.rafaelleal.android.apiproject.adapters.NewsListener
import com.rafaelleal.android.apiproject.api.models.Article
import com.rafaelleal.android.apiproject.databinding.FragmentHomeBinding
import com.rafaelleal.android.apiproject.utils.isInternetAvailable
import com.rafaelleal.android.apiproject.utils.nav
import com.rafaelleal.android.apiproject.utils.toast
import com.rafaelleal.android.apiproject.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topHeadlinesArticles.collect { articles ->
                    adapter.submitList(articles)
                    binding.rvTopHeadlines.adapter = adapter
                }
            }
        }

        if (!isInternetAvailable()){
            toast("Internet Não disponível")
        }
    }


    private fun setup() {
        setupViews()
        setupClickListeners()
        setupRecyclerView()
    }

    private fun setupViews() {
        activity?.setTitle("Home")
    }

    private fun setupClickListeners() {
        binding.apply {
            btnNews.setOnClickListener {
                nav(R.id.action_homeFragment_to_newsFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val adapter = NewsAdapter(
        object : NewsListener {
            override fun onItemClick(article: Article) {
                viewModel.setSelectedArticle(article)
                nav(R.id.action_homeFragment_to_selectedNewsFragment)
            }
        }
    )

    private fun setupRecyclerView() {
        binding.rvTopHeadlines.adapter = adapter
        binding.rvTopHeadlines.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }


}