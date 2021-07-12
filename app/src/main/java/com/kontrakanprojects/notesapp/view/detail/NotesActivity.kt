package com.kontrakanprojects.notesapp.view.detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kontrakanprojects.notesapp.R
import com.kontrakanprojects.notesapp.databinding.ActivityNotesBinding
import com.kontrakanprojects.notesapp.model.Notes

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding

    private var position = 0
    private var notes: Notes? = null
    private var isEdit = false

    companion object {
        const val EXTRA_NOTES_POSITION = "extra_notes_position"
        const val EXTRA_NOTES = "extra_notes"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_EDIT = 200
        const val RESULT_EDIT = 201
        const val RESULT_DELETE = 300
    }

    private val TAG = NotesActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbarTitle()

        notes = intent.getParcelableExtra(EXTRA_NOTES)

        if (notes != null) {
            position = intent.getIntExtra(EXTRA_NOTES_POSITION, 0)
            prepare()
            isEdit = true
        }
    }

    private fun prepare() {
        with(binding) {
            etTitle.setText(notes!!.title)
            etDescription.setText(notes!!.description)
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(getString(R.string.delete_notes_title))
            .setMessage(getString(R.string.delete_notes_body))
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                deleteNote(position)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.toolbar_notes, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setNotes()
            }
            R.id.delete -> showAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNotes() {
        with(binding) {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()

            Log.d(TAG, "setNotes: ")

            // prepare value
            if (isEdit) {
                editNote(position, title, description)
            } else {
                addNote(title, description)
            }
        }
    }

    private fun addNote(title: String, description: String) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_NOTES, Notes(title, description))
        }
        setResult(RESULT_ADD, resultIntent)
        finish()
    }

    private fun editNote(position: Int, title: String, description: String) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_NOTES_POSITION, position)
            putExtra(EXTRA_NOTES, Notes(title, description))
        }
        setResult(RESULT_EDIT, resultIntent)
        finish()

        Log.d(TAG, "editNote: Dijalankan")
    }

    private fun deleteNote(position: Int) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_NOTES_POSITION, position)
        }
        setResult(RESULT_DELETE, resultIntent)
        finish()

        Log.d(TAG, "deleteNote: Dijalankan")
    }

    private fun setToolbarTitle() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    override fun onBackPressed() {
        setNotes()
        super.onBackPressed()
    }
}