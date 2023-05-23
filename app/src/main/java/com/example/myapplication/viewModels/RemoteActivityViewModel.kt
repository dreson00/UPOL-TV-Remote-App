package com.example.myapplication.viewModels

import android.app.Application
import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.myapplication.Utilities.RemoteManager
import com.example.myapplication.data.*
import com.example.myapplication.repositories.RemoteRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RemoteActivityViewModel(app: Application, private val remote: Remote) :
    AndroidViewModel(app) {
    private lateinit var remoteTransmissionDataDatabase: RemoteTransmissionDataDatabase
    private lateinit var brandDao: RemoteBrandDao
    private lateinit var buttonDao: RemoteButtonDao
    private lateinit var protocolInfoDao: ProtocolInfoDao
    private lateinit var repository: RemoteRepository

    var brandName: MutableLiveData<String> = MutableLiveData("")
    private lateinit var buttons: List<RemoteButton>
    private lateinit var protocolInfo: ProtocolInfo
    private lateinit var irManager: ConsumerIrManager
    private lateinit var remoteManager: RemoteManager

    private var vibrator: Vibrator


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                remoteTransmissionDataDatabase = RemoteTransmissionDataDatabase.getDatabase(app)
                brandDao = remoteTransmissionDataDatabase.getRemoteBrandDao()
                buttonDao = remoteTransmissionDataDatabase.getRemoteButtonDao()
                protocolInfoDao = remoteTransmissionDataDatabase.getProtocolInfoDao()
                repository = RemoteRepository(RemoteDatabase.getDatabase(app).getRemoteDao())

                brandName.postValue(brandDao.findById(remote.brand_id).brand_name)
                buttons = buttonDao.getButtonsByBrandId(remote.brand_id)
                protocolInfo = protocolInfoDao.getProtocolInfoByBrandId(remote.brand_id)

                irManager =
                    getApplication<Application>().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager
                remoteManager = RemoteManager(irManager, protocolInfo)
            }
        }
        vibrator =
            getApplication<Application>().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


    }

    fun delete_remote(remote: Remote) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            repository.deleteRemote(remote)
        }
    }

    // zavibruje (podle verze API) a vyšle signál, pokud mobil nemá IR port, zobrazí se uživateli zpráva
    fun transmit(buttonName: String, snackbarView: View, noSuccessMessage: String) {
        Log.w("info", "kliknuto na $buttonName")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, 20))
        } else {
            vibrator.vibrate(100)
        }

        val button = buttons.find { it.button_name == buttonName } ?: buttons.first()
        if (!remoteManager.transmit(button.command, button.command_format, button.bits)) {
            Snackbar.make(snackbarView, noSuccessMessage, Snackbar.LENGTH_SHORT).show()
        }
    }
}

class RemoteActivityViewModelFactory(private val app: Application, private val remote: Remote) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemoteActivityViewModel::class.java)) {
            return RemoteActivityViewModel(app, remote) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}