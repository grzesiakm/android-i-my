package com.example.bloodpurification.screens.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bloodpurification.R
import com.example.bloodpurification.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private lateinit var viewModel: InputViewModel
    private lateinit var binding: FragmentInputBinding
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_input, container,false)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[InputViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        navController = findNavController()

        binding.inputButton.setOnClickListener {
            updateViewModelData()
        }
        return binding.root
    }

    private fun updateViewModelData() {
        var missingInput = false

        val fieldsArray: Array<EditText> = arrayOf(
            binding.editText1,
            binding.editText2,
            binding.editText3,
            binding.editText4,
            binding.editText5,
            binding.editText6)

        for (editText: EditText in fieldsArray) {
            if (editText.text.toString() == "") {
                editText.error = getString(R.string.inputRequiredError)
                missingInput = true
            }
            else if (editText.text.toString().toDouble() <= 0.toDouble()) {
                editText.error = getString(R.string.invalidValueError)
            }
        }

        if (!missingInput) {
            viewModel.updateCPre(binding.editText1.text.toString().toDouble())
            viewModel.updateVTotal(binding.editText2.text.toString().toDouble())
            viewModel.updateClearanceInter(binding.editText3.text.toString().toDouble())
            viewModel.updateGenRate(binding.editText4.text.toString().toDouble())
            viewModel.updateClearanceAvg(binding.editText5.text.toString().toDouble())
            viewModel.updateTTreatment(binding.editText6.text.toString().toDouble())

            viewModel.updateGraphSeries()
            navController.navigate(R.id.action_input_to_simulation)
        }
    }
}