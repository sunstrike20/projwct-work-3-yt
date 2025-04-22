package models

// Класс для хранения заметки
class Note(val title: String, val content: String) {
    init {
        require(title.isNotBlank()) { "Название заметки не может быть пустым" }
        require(content.isNotBlank()) { "Содержимое заметки не может быть пустым" }
    }
} 