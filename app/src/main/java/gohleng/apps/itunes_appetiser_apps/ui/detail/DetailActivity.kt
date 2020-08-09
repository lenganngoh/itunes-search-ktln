package gohleng.apps.itunes_appetiser_apps.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gohleng.apps.itunes_appetiser_apps.R
import gohleng.apps.itunes_appetiser_apps.data.model.Track
import gohleng.apps.itunes_appetiser_apps.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TRACK_ID = "extra_track_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private val vm: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupUIAction()
    }

    private fun setupObservers() {
        vm.getTrack(intent.getLongExtra(EXTRA_TRACK_ID, 0)).observe(this, Observer {
            updateView(it)
        })
    }

    private fun setupUIAction() {
        iconClose.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateView(track: Track) {
        binding.txtName.text = track.trackName
        binding.txtPrice.text = String.format("%s %s", track.currency, track.trackPrice)
        binding.txtArtist.text = String.format("%s: %s", resources.getString(R.string.str_artist), track.artistName)
        binding.txtGenre.text = String.format("%s: %s", resources.getString(R.string.str_genre), track.primaryGenreName)
        binding.txtLabel.text = track.shortDescription
        binding.txtLongDescription.text = track.longDescription
        Glide.with(binding.imgTrack.context)
            .load(track.artworkUrl100)
            .into(binding.imgTrack)
    }
}