package com.kontrakanprojects.notesapp.utils

import com.kontrakanprojects.notesapp.model.Notes

object DataNotes {
    fun getNotes(): List<Notes> {

        val dataNotes = ArrayList<Notes>()

        with(dataNotes) {
            add(
                Notes(
                    "Deadline Tugas PAM",
                    "Mengerjakan Tugas PAM"
                )
            )
            add(
                Notes(
                    "Deadline Projek CBR",
                    "Memperbaiki beberapa tampilan dan routing aplikasi"
                )
            )
            add(
                Notes(
                    "Laporan Proposal Bab III",
                    "Beberapa poin yang harus diperbaiki yaitu teknik yang digunakan dalam" +
                            "mengolah data teks sehingga menjadi dokumen yang bisa di analisis"
                )
            )
        }

        return dataNotes
    }
}