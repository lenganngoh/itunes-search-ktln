package gohleng.apps.itunes_appetiser_apps.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gohleng.apps.itunes_appetiser_apps.data.model.Track

@Dao
interface TrackDao {
    @Query("SELECT * FROM tracks")
    fun getAll() : LiveData<List<Track>>

    @Query("SELECT * FROM tracks WHERE trackId = :id")
    fun getItem(id: Long): LiveData<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Track)

    @Query("DELETE from tracks")
    fun clear()
}