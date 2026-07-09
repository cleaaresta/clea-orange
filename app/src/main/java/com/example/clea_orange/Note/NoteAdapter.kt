package com.example.clea_orange.Note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clea_orange.data.entity.NoteEntity
import com.example.clea_orange.databinding.ItemNoteBinding

class NoteAdapter(
    private var list: List<NoteEntity>,
    private val fragment: NotesFragment
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = list[position]
        holder.binding.tvNoteTitle.text = note.title
        holder.binding.tvNoteContent.text = note.content

        holder.binding.btnDelete.setOnClickListener {
            fragment.deleteNote(note)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<NoteEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}