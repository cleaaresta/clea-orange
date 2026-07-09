package com.example.clea_orange.Note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clea_orange.data.AppDatabase
import com.example.clea_orange.data.entity.NoteEntity
import com.example.clea_orange.databinding.FragmentNotesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: AppDatabase
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getInstance(requireContext())
        setupRecyclerView()

        binding.fabAddNote.setOnClickListener {
            val intent = Intent(requireContext(), NoteFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(listOf(), this)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    override fun onResume() {
        super.onResume()
        fetchNotes()
    }

    fun fetchNotes() {
        lifecycleScope.launch(Dispatchers.IO) {
            val notes = database.noteDao().getAll()
            withContext(Dispatchers.Main) {
                noteAdapter.updateData(notes)
            }
        }
    }

    fun deleteNote(note: NoteEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.noteDao().delete(note)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                fetchNotes()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}