package gohleng.apps.itunes_appetiser_apps.data.remote

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackService {
    @GET("/search")
    fun search(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query("media") media: String
    ): Single<Response<TrackResponse>>
}