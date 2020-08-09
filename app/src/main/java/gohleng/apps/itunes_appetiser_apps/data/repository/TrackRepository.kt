package gohleng.apps.itunes_appetiser_apps.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import gohleng.apps.itunes_appetiser_apps.data.local.TrackDao
import gohleng.apps.itunes_appetiser_apps.data.model.Track
import gohleng.apps.itunes_appetiser_apps.data.remote.TrackResponse
import gohleng.apps.itunes_appetiser_apps.data.remote.TrackService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val remote: TrackService,
    private val local: TrackDao
) {

    @SuppressLint("CheckResult")
    fun search(term: String, country: String, media: String): Single<Response<TrackResponse>> {
        return remote.search(term, country, media)
    }

    fun clear() {
        local.clear()
    }

    fun insert(track: Track) {
        local.insert(track)
    }

    fun getAll(): LiveData<List<Track>> = local.getAll()

    fun getTrack(id: Long): LiveData<Track> = local.getTrack(id)
}