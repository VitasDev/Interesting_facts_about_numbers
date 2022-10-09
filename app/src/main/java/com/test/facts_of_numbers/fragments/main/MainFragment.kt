package com.test.facts_of_numbers.fragments.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.facts_of_numbers.NumbersService
import com.test.facts_of_numbers.R
import com.test.facts_of_numbers.databinding.FragmentMainBinding
import com.test.facts_of_numbers.model.Numbers
import com.test.facts_of_numbers.viewmodel.NumberViewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {

    private lateinit var mNumberViewModel: NumberViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private lateinit var retrofit: NumbersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        job = Job()
        binding = FragmentMainBinding.inflate(inflater, container, false)

        //init Retrofit
        retrofit = Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NumbersService::class.java)

        //RecyclerView
        val adapter = ListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mNumberViewModel = ViewModelProvider(this)[NumberViewModel::class.java]
        mNumberViewModel.readAllData.observe(viewLifecycleOwner, Observer { numbers ->
            adapter.setData(numbers)
        })

        binding.btnRandom.setOnClickListener {
            requestToApi(true)
        }

        binding.btnSearch.setOnClickListener {
            if (binding.enterNumTxt.text.isNotEmpty()) {
                requestToApi(false)
            } else {
                Toast.makeText(requireContext(), "Please enter the number!", Toast.LENGTH_SHORT).show()
            }
        }

        //Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun requestToApi(random: Boolean) {
        launch {
            binding.recyclerview.visibility = View.INVISIBLE
            binding.progressCircular.visibility = View.VISIBLE

            val result = withContext(Dispatchers.IO) {
                if (random) {
                    retrofit.getRandomFact().execute()
                } else {
                    retrofit.getFactNumber(binding.enterNumTxt.text.toString().toInt())
                        .execute()
                }
            }

            binding.recyclerview.visibility = View.VISIBLE
            binding.progressCircular.visibility = View.GONE

            if (result.isSuccessful) {
                val resultNumber = result.body()?.number
                val resultFact = result.body()?.text.toString()

                //Create Numbers Object
                val numbers = Numbers(0, resultNumber!!, resultFact)
                //Add Data to Database
                mNumberViewModel.addNumber(numbers)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllNumbers()
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteAllNumbers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _, ->
            mNumberViewModel.deleteAllNumbers()
            Toast.makeText(requireContext(), "Successfully removed everything", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}