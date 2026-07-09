package com.example.clea_orange.Note

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.clea_orange.data.AppDatabase
import com.example.clea_orange.data.entity.NoteEntity
import com.example.clea_orange.databinding.ActivityNoteFormBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteFormBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)

        setupToolbar()

        binding.btnSaveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Add Note"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val note = NoteEntity(
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        lifecycleScope.launch(Dispatchers.IO) {
            database.noteDao().insert(note)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@NoteFormActivity, "Note saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}