package com.example.myapplication.repositories

import androidx.lifecycle.LiveData
import com.example.myapplication.data.Remote
import com.example.myapplication.data.RemoteDao


class RemoteRepository(private val remoteDao: RemoteDao) {

    fun getAllRemotes(): LiveData<List<Remote>> = remoteDao.getAll()

    suspend fun insert(remoteName: String, brandId: Int) {
        remoteDao.insert(Remote(0, remoteName, brandId))
    }

    suspend fun deleteRemote(remote: Remote) {
        remoteDao.delete(remote)
    }
}