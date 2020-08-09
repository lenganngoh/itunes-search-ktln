package gohleng.apps.itunes_appetiser_apps.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import gohleng.apps.itunes_appetiser_apps.data.model.Track
import gohleng.apps.itunes_appetiser_apps.data.repository.TrackRepository

class DetailViewModel @ViewModelInject constructor(private val repo: TrackRepository): ViewModel() {

    fun getTrack(id: Long): LiveData<Track> {
        return repo.getTrack(id)
    }
}