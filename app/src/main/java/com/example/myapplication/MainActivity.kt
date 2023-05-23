package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.RemotesAdapter
import com.example.myapplication.data.Remote
import com.example.myapplication.data.RemoteDatabase
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewModels.MainActivityViewModel


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), RemotesAdapter.RemoteListItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var remoteDatabase: RemoteDatabase
    lateinit var viewModel: MainActivityViewModel
    lateinit var adapter: RemotesAdapter

    private val updateRemote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val remoteName = result.data?.getStringExtra("remote_name")
                if (remoteName != null) {

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainActivityViewModel::class.java]

        switchTheme(viewModel.getCurrentTheme())

        viewModel.allRemotes.observe(this) { list ->
            list?.let {
                adapter.updateRemotes(list)
            }
        }

        remoteDatabase = RemoteDatabase.getDatabase(this)

    }

    private fun initUI() {
        adapter = RemotesAdapter(this, this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val remoteName = result.data?.getSerializableExtra("remote_name") as String?
                    val brandId = result.data?.getSerializableExtra("brand_id") as Int?
                    if (remoteName != null && brandId != null) {
                        viewModel.insert(remoteName, brandId)
                    }
                }
            }

        binding.modeSwitch.setOnClickListener {
            switchTheme(viewModel.switchTheme())
        }

        binding.addRemoteFab.setOnClickListener {
            getContent.launch(Intent(this, AddRemote::class.java))
        }
    }

    private fun switchTheme(newTheme: String) {
        if (newTheme == "dark_theme") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.modeSwitch.setImageResource(R.drawable.baseline_light_mode_24)
        } else if (newTheme == "light_theme") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.modeSwitch.setImageResource(R.drawable.baseline_dark_mode_24)
        }
    }

    override fun onRemoteClick(remote: Remote) {
        val intent = Intent(this, RemoteActivity::class.java)
        intent.putExtra("remote", remote)
        startActivity(intent)
    }
}