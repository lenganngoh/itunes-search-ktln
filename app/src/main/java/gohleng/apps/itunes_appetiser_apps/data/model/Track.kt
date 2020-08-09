package gohleng.apps.itunes_appetiser_apps.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey val trackId: Long = 0,
    val trackName: String = "",
    val artistName: String = "",
    val artworkUrl100: String = "",
    val currency: String = "",
    val trackPrice: Double = 0.0,
    val primaryGenreName: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val description: String = ""
)