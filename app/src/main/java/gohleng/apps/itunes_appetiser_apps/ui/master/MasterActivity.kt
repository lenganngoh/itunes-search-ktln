package gohleng.apps.itunes_appetiser_apps.ui.master

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gohleng.apps.itunes_appetiser_apps.databinding.ActivityMasterBinding
import gohleng.apps.itunes_appetiser_apps.ui.detail.DetailActivity
import gohleng.apps.itunes_appetiser_apps.ui.detail.DetailActivity.Companion.EXTRA_TRACK_ID
import kotlinx.android.synthetic.main.activity_master.*

@AndroidEntryPoint
class MasterActivity : AppCompatActivity(), TrackListAdapter.OnTrackClickListener {

    companion object {
        const val SHARED_PREF = "shared_pref"
        const val SHARED_PREF_TERM = "shared_pref_term"
    }

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityMasterBinding
    private val vm: MasterViewModel by viewModels()
    private lateinit var adapter: TrackListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSharedPref()
        setupTrackList()
        setupObservers()
        setupUIAction()
    }

    private fun setupSharedPref() {
        preferences = getSharedPreferences(SHARED_PREF, 0)
    }

    private fun setupUIAction() {
        binding.etSearch.setOnEditorActionListener { _, id, _ ->
            return@setOnEditorActionListener when (id) {
                EditorInfo.IME_ACTION_GO -> {
                    startSearch()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupTrackList() {
        binding.contList.setOnRefreshListener {
            startSearch()
        }
        adapter = TrackListAdapter(this)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
    }

    private fun setupObservers() {
        vm.isLoading.observe(this, Observer {
            binding.contList.isRefreshing = it
        })

        vm.getAll().observe(this, Observer {
            vm.isLoading.value = false
            if (!it.isNullOrEmpty()) {
                binding.contList.visibility = VISIBLE
                adapter.setTrackList(it)
            } else {
                binding.contList.visibility = GONE
            }
        })
    }

    private fun startSearch() {
        var term: String? = preferences.getString(SHARED_PREF_TERM, null)
        if (etSearch.text.toString().trim().isNotEmpty()) term = etSearch.text.toString()

        if (!term.isNullOrEmpty()) {
            preferences.edit().putString(SHARED_PREF_TERM, term).apply()
            vm.search(term)
        }
    }

    override fun onTrackClick(id: Long) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_TRACK_ID, id)
        }
        startActivity(intent)
    }
}