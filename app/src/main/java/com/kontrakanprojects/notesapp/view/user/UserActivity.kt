package com.kontrakanprojects.notesapp.view.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kontrakanprojects.notesapp.databinding.ActivityUserBinding
import com.kontrakanprojects.notesapp.session.UserPreference
import com.kontrakanprojects.notesapp.view.dashboard.DashboardActivity

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var userPreference: UserPreference

    private val isUserDone = true

    companion object {
        private const val NAME_NOT_NULL = "Nama tidak boleh kosong!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textWatcher()

        userPreference = UserPreference(this)

        binding.btnNext.setOnClickListener { set() }
    }

    private fun textWatcher() {
        with(binding) {
            etNama.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length!! == 0) {
                        tiNama.error = NAME_NOT_NULL
                        btnNext.visibility = View.GONE
                    } else {
                        btnNext.visibility = View.VISIBLE
                        tiNama.error = null
                    }
                }
            })
        }
    }

    private fun set() {
        val nama = binding.etNama.text.toString().trim()
        userPreference.setUser(nama) // set nama user
        userPreference.setStateApp(isUserDone)

        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}