package com.example.hearthstone.ui

import androidx.lifecycle.ViewModel
import com.example.hearthstone.data.network.repo.HSRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(repo: HSRepo): ViewModel() {

    fun timberTime() {
        Timber.d("Zelda our function was called through DI")
    }
}
