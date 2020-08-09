package gohleng.apps.itunes_appetiser_apps.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import gohleng.apps.itunes_appetiser_apps.data.local.TrackDao
import gohleng.apps.itunes_appetiser_apps.data.model.Track
import gohleng.apps.itunes_appetiser_apps.data.remote.TrackService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val remote: TrackService,
    private val local: TrackDao
) {

    @SuppressLint("CheckResult")
    fun search(term: String, country: String, media: String) {
        remote.search(term, country, media)
            .subscribeOn(Schedulers.io())
            .subscribe { response, _ ->
                if (response.isSuccessful) {
                    local.clear()
                    response.body()?.results?.forEach {
                        local.insert(it)
                    }
                }
            }
    }

    fun getAll(): LiveData<List<Track>> = local.getAll()

    fun getTrack(id: Long): LiveData<Track> = local.getItem(id)
}