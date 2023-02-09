package com.michael.quitnicotine_application.data

class Achievement(_achievementName: String,
                  _achievementStatus: Boolean = false,
                  _condition: Int) {

    private var achievementName = _achievementName   // название достижения
    private var achievementStatus = _achievementStatus   // статус (выполнено или нет)
    private var condition = _condition   // требуемое условие

    fun getAchievementName() = achievementName

    fun getAchievementStatus() = achievementStatus

    fun getCondition() = condition

    // Метод для обновления статуса
    fun updateStatus(userCondition: Int){
        achievementStatus = condition == userCondition
    }
}