package gohleng.apps.itunes_appetiser_apps.ui.master

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gohleng.apps.itunes_appetiser_apps.data.model.Track
import gohleng.apps.itunes_appetiser_apps.databinding.ViewholderListRowBinding

class TrackListAdapter(private val onClickListener: OnTrackClickListener) :
    RecyclerView.Adapter<TrackListViewHolder>() {
    interface OnTrackClickListener {
        fun onTrackClick(id: Long)
    }

    private val trackList = ArrayList<Track>()

    fun setTrackList(trackList: List<Track>) {
        this.trackList.clear()
        this.trackList.addAll(trackList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder {
        val binding: ViewholderListRowBinding =
            ViewholderListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackListViewHolder(binding, onClickListener)
    }

    override fun getItemCount(): Int = trackList.size

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) =
        holder.bind(trackList[position])
}

class TrackListViewHolder(
    private val trackBinding: ViewholderListRowBinding,
    private val onClickListener: TrackListAdapter.OnTrackClickListener
) :
    RecyclerView.ViewHolder(trackBinding.root) {

    private lateinit var track: Track

    fun bind(track: Track) {
        this.track = track
        trackBinding.txtName.text = track.trackName
        trackBinding.txtArtist.text = track.artistName
        trackBinding.txtLabel.text = track.shortDescription
        trackBinding.txtPrice.text = String.format("%s %s", track.currency, track.trackPrice.toString())
        Glide.with(trackBinding.imgTrack.context)
            .load(track.artworkUrl100)
            .into(trackBinding.imgTrack)

        trackBinding.root.setOnClickListener {
            onClickListener.onTrackClick(track.trackId)
        }
    }
}