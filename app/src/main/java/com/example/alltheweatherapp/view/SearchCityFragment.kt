package com.example.alltheweatherapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alltheweatherapp.R
import com.example.alltheweatherapp.databinding.CitySearchLayoutBinding
import com.example.alltheweatherapp.di.DI
import com.example.alltheweatherapp.model.WeatherRepositoryImpl
import com.example.alltheweatherapp.viewmodel.WeatherSearchCity
import java.lang.Exception

private const val TAG = "SearchCityFragment"
class SearchCityFragment : Fragment() {

    private val viewModel: WeatherSearchCity by lazy {
        WeatherSearchCity.WeatherSearchCityProvider(DI.provideRepository())
            .create(WeatherSearchCity::class.java)
    }

    fun interface SelectedCity{
        fun selectedCity(geoId: Int)
    }

    private lateinit var adapter: CityListAdapter
    private lateinit var binding: CitySearchLayoutBinding
    private lateinit var listener: SelectedCity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is SelectedCity -> listener = context
            else -> throw IllegalAccessException("Host Activity does not Implement SelectedCity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CitySearchLayoutBinding.inflate(inflater)
        setupViews()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.cityLiveData.observe(viewLifecycleOwner){
            createAdapter(it)
        }
    }

    private fun createAdapter(dataset: WeatherRepositoryImpl.CityState) {
        when(dataset){
            is WeatherRepositoryImpl.CityState.Response->
                binding.cityList.adapter = CityListAdapter(dataset.cityList.cities, ::selectedCity)
            is WeatherRepositoryImpl.CityState.Error->
                binding.cityList.adapter = CityListAdapter(emptyList(), ::selectedCity)
            else ->
                throw Exception("Unknown error!")
        }
    }

    private fun setupViews() {
        binding.closeSearch.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        context?.let {
            Log.d(TAG, "setupViews: context")
            binding.cityList.layoutManager = LinearLayoutManager(context)
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.city_array)
            ).also { adapter ->
                binding.searchCity.setAdapter(adapter)
            }
        }

        binding.searchCity.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchCity(textView.text.toString())
                true
            } else false
        }
    }

    private fun selectedCity(geoId: Int){
        Log.d(TAG, "selectedCity: geoId")
        viewModel.storeSelectedCity(geoId)
        listener.selectedCity(geoId)
        binding.closeSearch.performClick()
    }

}