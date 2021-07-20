package com.veen.mobulous.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veen.mobulous.R
import com.veen.mobulous.Utlis.MyApi
import com.veen.mobulous.Utlis.NetworkConnection
import com.veen.mobulous.adapter.MainAdapter
import com.veen.mobulous.databinding.FragmentHomeBinding
import com.veen.mobulous.db.AddToCart
import com.veen.mobulous.db.CartDatabase
import com.veen.mobulous.model.Restaurant
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
//    private var binding = FragmentHomeBinding
    private lateinit var adapter: MainAdapter
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        lifecycleScope.launch {
            zomotoapi()
        }
        return binding.root
    }

    private suspend fun zomotoapi() {
        try {
            val savedcart: List<AddToCart> = CartDatabase(requireContext()).getNoteDao().getalldate()
            val response = MyApi.invoke().getdata()
            if (response.isSuccessful && response.body() != null) {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                val data = response.body()
                bindfoodzomotofood(data!!.restaurants, savedcart)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error Occurred: ${response.message()}",
                    Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun bindfoodzomotofood(restaurant: List<Restaurant>, savedcart: List<AddToCart>) {
        try {
            val orientation: Int = requireActivity().getResources().getConfiguration().orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                binding.recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                binding.recyclerView.setItemAnimator(SlideInUpAnimator())
                adapter= MainAdapter(requireContext(), restaurant, savedcart)
                binding.recyclerView.adapter=adapter
                adapter.notifyDataSetChanged()
            } else {
                binding.recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
                binding.recyclerView.setItemAnimator(SlideInUpAnimator())
                adapter= MainAdapter(requireContext(), restaurant, savedcart)
                binding.recyclerView.adapter=adapter
                adapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}