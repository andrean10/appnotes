package com.kontrakanprojects.notesapp.view.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.notesapp.databinding.ItemNoteBinding
import com.kontrakanprojects.notesapp.model.Notes

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.DashboardAdapterViewHolder>() {

    private val listNotes = ArrayList<Notes>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setData(notes: List<Notes>?) {
        if (notes == null) return
        listNotes.clear()
        listNotes.addAll(notes)
        notifyDataSetChanged()
    }

    fun getData() = listNotes

    fun addData(notes: Notes) {
        listNotes.add(notes)
        notifyDataSetChanged()
    }

    fun editData(position: Int, notes: Notes) {
        listNotes[position] = notes
        notifyItemChanged(position)
    }

    fun deleteData(position: Int) {
        listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardAdapter.DashboardAdapterViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardAdapterViewHolder(binding)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onBindViewHolder(
        holder: DashboardAdapter.DashboardAdapterViewHolder,
        position: Int
    ) {
        holder.bind(position, listNotes[position])
    }

    override fun getItemCount() = listNotes.size

    inner class DashboardAdapterViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, notes: Notes) {
            with(binding) {
                tvTitle.text = notes.title
                tvDescription.text = notes.description

                itemView.setOnClickListener { onItemClickCallBack?.onItemClicked(position, notes) }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(position: Int, notes: Notes)
    }
}