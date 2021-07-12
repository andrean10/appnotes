package com.kontrakanprojects.notesapp.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kontrakanprojects.notesapp.R
import com.kontrakanprojects.notesapp.databinding.ActivityDashboardBinding
import com.kontrakanprojects.notesapp.model.Notes
import com.kontrakanprojects.notesapp.session.UserPreference
import com.kontrakanprojects.notesapp.utils.DataNotes
import com.kontrakanprojects.notesapp.view.dashboard.adapter.DashboardAdapter
import com.kontrakanprojects.notesapp.view.detail.NotesActivity
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var userPreference: UserPreference

    private val TAG = DashboardActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)
        setAdapter()

        binding.fabAdd.setOnClickListener { moveToDetail() }
    }

    private fun setAdapter() {
        binding.tvWelcome.text =
            getString(R.string.welcome_screen, getTimeDay(), userPreference.getUser())

        // set adapter
        dashboardAdapter = DashboardAdapter()
        with(binding.rv) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            this.adapter = dashboardAdapter
        }

        dashboardAdapter.setData(DataNotes.getNotes())

        dashboardAdapter.setOnItemClickCallBack(object : DashboardAdapter.OnItemClickCallBack {
            override fun onItemClicked(position: Int, notes: Notes) {
                val intent = Intent(this@DashboardActivity, NotesActivity::class.java).apply {
                    putExtra(NotesActivity.EXTRA_NOTES_POSITION, position)
                    putExtra(NotesActivity.EXTRA_NOTES, notes)
                }
                startActivityForResult(intent, NotesActivity.REQUEST_EDIT)
            }
        })
    }

    private fun moveToDetail() {
        startActivityForResult(
            Intent(this, NotesActivity::class.java),
            NotesActivity.REQUEST_ADD
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            NotesActivity.REQUEST_ADD -> {
                if (resultCode == NotesActivity.RESULT_ADD && data != null) {
                    val notes = data.getParcelableExtra<Notes>(NotesActivity.EXTRA_NOTES)
                    if (notes != null) {
                        dashboardAdapter.addData(notes)
                    }
                }
            }
            NotesActivity.REQUEST_EDIT -> {
                when (resultCode) {
                    NotesActivity.RESULT_EDIT -> {
                        if (resultCode == NotesActivity.RESULT_EDIT && data != null) {
                            val position = data.getIntExtra(NotesActivity.EXTRA_NOTES_POSITION, 0)
                            val notes = data.getParcelableExtra<Notes>(NotesActivity.EXTRA_NOTES)
                            if (notes != null) {
                                dashboardAdapter.editData(position, notes)

                                Log.d(TAG, "onActivityResult: Dijalankan")
                            }
                        }
                    }
                    NotesActivity.RESULT_DELETE -> {
                        if (resultCode == NotesActivity.RESULT_DELETE && data != null) {
                            val position = data.getIntExtra(NotesActivity.EXTRA_NOTES_POSITION, 0)
                            dashboardAdapter.deleteData(position)

                            Log.d(TAG, "onActivityResult: Dijalankan")
                        }
                    }
                }
            }
        }
    }

    private fun isDataExist(state: Boolean) {
        with(binding) {
            if (state) {
                layoutFound.visibility = View.VISIBLE
                layoutNotFound.visibility = View.GONE
            } else {
                layoutFound.visibility = View.GONE
                layoutNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun getTimeDay(): String {
        val c: Calendar = Calendar.getInstance()
        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> "Pagi"
            in 12..15 -> "Siang"
            in 16..18 -> "Sore"
            in 19..23 -> "Malam"
            in 0..4 -> "Malam"
            else -> "Waktu tidak terdefinisi"
        }
    }
}