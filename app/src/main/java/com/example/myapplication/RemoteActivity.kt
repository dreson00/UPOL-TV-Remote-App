package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Remote
import com.example.myapplication.databinding.ActivityRemoteBinding
import com.example.myapplication.viewModels.RemoteActivityViewModel
import com.example.myapplication.viewModels.RemoteActivityViewModelFactory

@Suppress("DEPRECATION")
class RemoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRemoteBinding
    lateinit var viewModel: RemoteActivityViewModel
    private lateinit var remote: Remote
    private lateinit var brandName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        remote = intent.extras?.getSerializable("remote") as Remote

        viewModel = ViewModelProvider(
            this,
            RemoteActivityViewModelFactory(application, remote)
        )[RemoteActivityViewModel::class.java]

        binding.remoteName.text = remote.name
        viewModel.brandName.observe(this, Observer {
            brandName = it
            binding.remoteBrandName.text = it
        })

        binding.remoteBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.remoteDeleteButton.setOnClickListener {
            viewModel.delete_remote(remote)
            onBackPressedDispatcher.onBackPressed()
        }

        binding.powerBtn.setOnClickListener {
            viewModel.transmit(
                getString(R.string.power),
                binding.remoteActivityMainLayout,
                getString(R.string.no_ir_port)
            )
        }

        binding.volumeUpBtn.setOnClickListener {
            viewModel.transmit(
                getString(R.string.volume_up),
                binding.remoteActivityMainLayout,
                getString(R.string.no_ir_port)
            )
        }

        binding.volumeDownBtn.setOnClickListener {
            viewModel.transmit(
                getString(R.string.volume_down),
                binding.remoteActivityMainLayout,
                getString(R.string.no_ir_port)
            )
        }

        binding.nextProgramBtn.setOnClickListener {
            viewModel.transmit(
                getString(R.string.next_program),
                binding.remoteActivityMainLayout,
                getString(R.string.no_ir_port)
            )
        }

        binding.previousProgramBtn.setOnClickListener {
            viewModel.transmit(
                getString(R.string.previous_program),
                binding.remoteActivityMainLayout,
                getString(R.string.no_ir_port)
            )
        }
    }
}


