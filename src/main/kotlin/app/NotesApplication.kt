package app

import models.Archive
import ui.ArchiveListMenu
import ui.Menu
import ui.MenuResult

// Приложение для управления заметками
class NotesApplication {
    private val archives = mutableListOf<Archive>()
    
    fun start() {
        var currentMenu: Menu = ArchiveListMenu(archives)
        
        while (true) {
            currentMenu.displayOptions()
            val choice = currentMenu.processInput(currentMenu.getMaxOption())
            val result = currentMenu.handleChoice(choice)
            
            if (result.nextMenu == null) {
                break // Выход из приложения
            }
            
            currentMenu = result.nextMenu
        }
    }
} 