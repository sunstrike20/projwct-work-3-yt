package models

import models.Note

// Класс для хранения архива заметок
class Archive(val title: String) {
    init {
        require(title.isNotBlank()) { "Название архива не может быть пустым" }
    }
    
    val notes = mutableListOf<Note>()
    
    fun addNote(note: Note) {
        notes.add(note)
    }
} 