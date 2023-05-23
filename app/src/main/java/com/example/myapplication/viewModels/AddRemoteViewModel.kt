package com.example.myapplication.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.RemoteBrand
import com.example.myapplication.data.RemoteTransmissionDataDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddRemoteViewModel(app: Application) : AndroidViewModel(app) {
    var allBrands: MutableLiveData<List<RemoteBrand>> = MutableLiveData(listOf<RemoteBrand>())

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allBrands.postValue(
                    RemoteTransmissionDataDatabase.getDatabase(app).getRemoteBrandDao().getAll()
                )
            }
        }
    }


}