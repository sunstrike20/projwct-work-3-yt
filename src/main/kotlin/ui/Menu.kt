package ui

import java.util.*

// Базовый класс для меню выбора
abstract class Menu {
    protected val scanner = Scanner(System.`in`)
    
    // Общая логика обработки ввода для всех меню
    open fun processInput(maxOption: Int): Int {
        require(maxOption >= 0) { "Максимальная опция должна быть не меньше 0" }
        
        var userInput: Int
        while (true) {
            try {
                val input = scanner.nextLine()
                userInput = input.toInt()
                
                if (userInput in 0..maxOption) {
                    return userInput
                } else {
                    println("Ошибка: Введите число от 0 до $maxOption.")
                    displayOptions()
                }
            } catch (e: NumberFormatException) {
                println("Ошибка: Введите число.")
                displayOptions()
            }
        }
    }
    
    // Метод для отображения опций меню (переопределяется в подклассах)
    abstract fun displayOptions()
    
    // Метод для обработки выбора пользователя (переопределяется в подклассах)
    abstract fun handleChoice(choice: Int): MenuResult
    
    // Метод для получения максимального номера опции в меню
    abstract fun getMaxOption(): Int
}

// Результат выбора пункта меню
data class MenuResult(val nextMenu: Menu?) 