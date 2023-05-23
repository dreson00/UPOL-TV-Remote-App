package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.RemoteBrand
import com.example.myapplication.databinding.ActivityAddRemoteBinding
import com.example.myapplication.viewModels.AddRemoteViewModel

class AddRemote : AppCompatActivity() {

    private lateinit var binding: ActivityAddRemoteBinding
    private lateinit var remoteName: String
    private lateinit var allBrands: List<RemoteBrand>
    private lateinit var selectedBrand: RemoteBrand
    private lateinit var viewModel: AddRemoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[AddRemoteViewModel::class.java]

        binding = ActivityAddRemoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val spinner: Spinner = binding.spinner

        viewModel.allBrands.observe(this, Observer {
            allBrands = it
            spinner.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                allBrands.map { it.brand_name }.toTypedArray()
            )
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedBrand = allBrands[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedBrand = allBrands.first()
            }
        }

        binding.addRemoteDoneButton.setOnClickListener {
            remoteName = binding.remoteNameField.text.toString()

            if (remoteName.isEmpty()) {
                remoteName = getString(R.string.remote_default_name)
            }

            val intent = Intent()
            intent.putExtra("remote_name", remoteName)
            intent.putExtra("brand_id", selectedBrand.brand_id)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


        binding.addRemoteBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}