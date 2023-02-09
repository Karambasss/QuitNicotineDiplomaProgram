package com.michael.quitnicotine_application.data

class Achievement(
    _achievementName: String,
    _condition: Int) {

    private var achievementName = _achievementName   // название достижения
    private var condition = _condition   // требуемое условие
    private var achievementStatus = false   // статус (выполнено или нет)

    fun getAchievementName() = achievementName

    fun getAchievementStatus() = achievementStatus

    fun getCondition() = condition

    // Метод для обновления статуса
    fun updateStatus(userCondition: Int){
        achievementStatus = condition == userCondition
    }
}