package com.veen.mobulous.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veen.mobulous.R
import com.veen.mobulous.Utlis.PageRefresh
import com.veen.mobulous.adapter.FileAdapter
import com.veen.mobulous.adapter.MainAdapter
import com.veen.mobulous.databinding.FragmentFileBinding
import com.veen.mobulous.db.AddToCart
import com.veen.mobulous.db.CartDatabase
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class FileFragment : Fragment(), PageRefresh {
    private lateinit var binding: FragmentFileBinding
    private lateinit var adapter: FileAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_file, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_file, container, false)
        val savedcart: List<AddToCart> = CartDatabase(requireContext()).getNoteDao().getalldate()
        bindfilezomotofood(savedcart)
        return binding.root
    }

    private fun bindfilezomotofood(savedcart: List<AddToCart>) {
        try {
            if (savedcart!= null) {
                binding.textView.visibility = View.GONE
            }
            val orientation: Int = requireActivity().getResources().getConfiguration().orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                binding.recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                binding.recyclerView.setItemAnimator(SlideInUpAnimator())
                adapter= FileAdapter(requireContext(), savedcart, this)
                binding.recyclerView.adapter=adapter
                adapter.notifyDataSetChanged()
            } else {
                binding.recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
                binding.recyclerView.setItemAnimator(SlideInUpAnimator())
                adapter= FileAdapter(requireContext(), savedcart, this)
                binding.recyclerView.adapter=adapter
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun OnUpdatePage() {
        val savedcart: List<AddToCart> = CartDatabase(requireContext()).getNoteDao().getalldate()
        bindfilezomotofood(savedcart)
    }
}