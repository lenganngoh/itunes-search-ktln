package gohleng.apps.itunes_appetiser_apps.ui.master

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gohleng.apps.itunes_appetiser_apps.data.model.Track
import gohleng.apps.itunes_appetiser_apps.data.repository.TrackRepository
import gohleng.apps.itunes_appetiser_apps.test.COUNTRY
import gohleng.apps.itunes_appetiser_apps.test.MEDIA

class MasterViewModel @ViewModelInject constructor(private val repo: TrackRepository) : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAll(): LiveData<List<Track>> {
        return repo.getAll()
    }

    fun search(term: String) {
        isLoading.value = true
        repo.search(term, COUNTRY, MEDIA)
    }
}