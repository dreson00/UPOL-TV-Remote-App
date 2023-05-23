package com.example.myapplication.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.myapplication.data.Remote
import com.example.myapplication.data.RemoteDatabase
import com.example.myapplication.repositories.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: RemoteRepository
    val allRemotes: LiveData<List<Remote>>
    private val preferences = PreferenceManager.getDefaultSharedPreferences(app)

    init {
        repository = RemoteRepository(RemoteDatabase.getDatabase(app).getRemoteDao())
        allRemotes = repository.getAllRemotes()
    }

    // vloží nový ovladač do repository
    fun insert(remoteName: String, brandId: Int) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            repository.insert(remoteName, brandId)
        }
    }

    // vrátí aktuální theme z shared pref.
    fun getCurrentTheme(): String {
        return preferences.getString("theme", "dark_theme") ?: "dark_theme"
    }

    // změní hodnotu theme v shared pref. na opačnou hodnotu
    fun switchTheme(): String {
        var theme = getCurrentTheme()

        if (theme == "dark_theme") {
            theme = "light_theme"
        } else if (theme == "light_theme") {
            theme = "dark_theme"
        }

        with(preferences.edit()) {
            putString("theme", theme)
            commit()
        }

        return theme
    }
}