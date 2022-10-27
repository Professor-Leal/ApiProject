package com.rafaelleal.android.apiproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.rafaelleal.android.apiproject.R
import com.rafaelleal.android.apiproject.api.models.Article
import com.rafaelleal.android.apiproject.databinding.FragmentSelectedNewsBinding
import com.rafaelleal.android.apiproject.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectedNewsFragment : Fragment() {
    private var _binding: FragmentSelectedNewsBinding? = null

    private val binding get() = _binding!!

    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectedNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        activity?.setTitle("Notícia Selecionada")
    }

    private fun setupObservers() {
        viewModel.selectedArticle.observe(viewLifecycleOwner){
            setupInitialView(it)
        }
    }

    private fun setupInitialView(article: Article) {
        binding.apply{
            tvTitulo.text = article.title
            tvAutor.text = article.author
            tvConteudo.text = article.content
            tvFonte.text = article.source?.name ?: "Fonte desconhecida"
            tvDescricao.text = article.description

            val urlImage = article.urlToImage
            if (!urlImage.isNullOrBlank()){
                Picasso.get()
                    .load(urlImage)
                    .resize(100, 150)
                    .centerInside()
                    .into(ivArticle)
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}