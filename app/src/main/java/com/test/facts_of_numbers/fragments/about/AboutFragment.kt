package com.test.facts_of_numbers.fragments.about

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.facts_of_numbers.R
import com.test.facts_of_numbers.databinding.FragmentAboutBinding
import com.test.facts_of_numbers.viewmodel.NumberViewModel

class AboutFragment : Fragment() {

    private val args by navArgs<AboutFragmentArgs>()
    private lateinit var mNumberViewModel: NumberViewModel
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        mNumberViewModel = ViewModelProvider(this)[NumberViewModel::class.java]

        binding.aboutNumberTxt.text = args.currentNumber.number.toString()
        binding.aboutFactTxt.text = args.currentNumber.factOfNumber

        //Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNumber()
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteNumber() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _, ->
            mNumberViewModel.deleteNumber(args.currentNumber)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentNumber.number}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_aboutFragment_to_mainFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentNumber.number}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNumber.number}?")
        builder.create().show()
    }
}