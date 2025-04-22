package ui

import models.Archive
import models.Note

// Меню выбора заметки
class NoteListMenu(private val archive: Archive, private val archives: MutableList<Archive>) : Menu() {
    override fun displayOptions() {
        println("\nАрхив: ${archive.title}")
        println("Заметки:")
        println("0. Создать заметку")
        
        for (i in archive.notes.indices) {
            println("${i + 1}. ${archive.notes[i].title}")
        }
        
        println("${archive.notes.size + 1}. Выход")
    }
    
    override fun getMaxOption(): Int {
        return archive.notes.size + 1
    }
    
    override fun handleChoice(choice: Int): MenuResult {
        return when (choice) {
            0 -> MenuResult(CreateNoteMenu(archive, archives))
            in 1..archive.notes.size -> MenuResult(NoteScreen(archive.notes[choice - 1], archive, archives))
            archive.notes.size + 1 -> MenuResult(ArchiveListMenu(archives)) // Возврат к списку архивов
            else -> {
                println("Ошибка: неверный выбор.")
                MenuResult(this)
            }
        }
    }
}

// Меню создания заметки
class CreateNoteMenu(private val archive: Archive, private val archives: MutableList<Archive>) : Menu() {
    override fun displayOptions() {
        println("\nСоздание заметки в архиве: ${archive.title}")
        println("Введите название заметки (или 0 для отмены):")
    }
    
    override fun getMaxOption(): Int = Int.MAX_VALUE
    
    override fun handleChoice(choice: Int): MenuResult {
        if (choice == 0) {
            return MenuResult(NoteListMenu(archive, archives))
        }
        
        // В обычном случае тут бы создавалась заметка, но логика создания перемещена в processInput
        return MenuResult(NoteListMenu(archive, archives))
    }
    
    // Переопределяем processInput для ввода названия и содержимого заметки
    override fun processInput(maxOption: Int): Int {
        val title = scanner.nextLine().trim()
        
        if (title == "0") {
            return 0
        }
        
        println("Введите содержимое заметки:")
        val content = scanner.nextLine().trim()
        
        try {
            val newNote = Note(title, content)
            archive.addNote(newNote)
            println("Заметка \"$title\" создана.")
        } catch (e: IllegalArgumentException) {
            println("Ошибка: ${e.message}")
            displayOptions()
            return processInput(maxOption)
        }
        
        // После создания заметки возвращаемся к списку заметок
        return 0 // Специальное значение, которое handleChoice преобразует в возврат к NoteListMenu
    }
}

// Экран просмотра заметки
class NoteScreen(private val note: Note, private val archive: Archive, private val archives: MutableList<Archive>) : Menu() {
    override fun displayOptions() {
        println("\nЗаметка: ${note.title}")
        println("Содержимое:")
        println(note.content)
        println("\n0. Выход")
    }
    
    override fun getMaxOption(): Int = 0
    
    override fun handleChoice(choice: Int): MenuResult {
        return MenuResult(NoteListMenu(archive, archives))
    }
} 