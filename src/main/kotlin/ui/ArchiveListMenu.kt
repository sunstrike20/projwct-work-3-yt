package ui

import models.Archive
import models.Note

// Меню выбора архива
class ArchiveListMenu(private val archives: MutableList<Archive>) : Menu() {
    override fun displayOptions() {
        println("\nСписок архивов:")
        println("0. Создать архив")
        
        for (i in archives.indices) {
            println("${i + 1}. ${archives[i].title}")
        }
        
        println("${archives.size + 1}. Выход")
    }
    
    override fun getMaxOption(): Int {
        return archives.size + 1
    }
    
    override fun handleChoice(choice: Int): MenuResult {
        return when (choice) {
            0 -> MenuResult(CreateArchiveMenu(archives))
            in 1..archives.size -> MenuResult(NoteListMenu(archives[choice - 1], archives))
            archives.size + 1 -> MenuResult(null) // Выход из приложения
            else -> {
                println("Ошибка: неверный выбор.")
                MenuResult(this)
            }
        }
    }
}

// Меню создания архива
class CreateArchiveMenu(private val archives: MutableList<Archive>) : Menu() {
    override fun displayOptions() {
        println("\nСоздание архива")
        println("Введите название архива (или 0 для отмены):")
    }
    
    override fun getMaxOption(): Int = Int.MAX_VALUE
    
    override fun handleChoice(choice: Int): MenuResult {
        if (choice == 0) {
            return MenuResult(ArchiveListMenu(archives))
        }

        return MenuResult(ArchiveListMenu(archives))
    }
    
    // Переопределяем processInput для ввода названия архива
    override fun processInput(maxOption: Int): Int {
        val title = scanner.nextLine().trim()
        
        if (title == "0") {
            return 0
        }
        
        try {
            val newArchive = Archive(title)
            archives.add(newArchive)
            println("Архив \"$title\" создан.")
        } catch (e: IllegalArgumentException) {
            println("Ошибка: ${e.message}")
            displayOptions()
            return processInput(maxOption)
        }
        
        // После создания архива возвращаемся к списку архивов
        return 0 // Специальное значение, которое handleChoice преобразует в возврат к ArchiveListMenu
    }
} 