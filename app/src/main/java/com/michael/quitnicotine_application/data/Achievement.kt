package com.michael.quitnicotine_application.data

class Achievement(
    _achievementName: String,
    _condition: Int) {

    private var achievementName = _achievementName   // название достижения
    private var condition = _condition   // требуемое условие
    private var achievementStatus = false   // статус (выполнено или нет)

    private var progressPercent: Int = 0

    fun getAchievementName() = achievementName

    fun getAchievementStatus() = achievementStatus

    fun getCondition() = condition

    fun getProgressPercent() = progressPercent

    // Метод для обновления статуса и прогресса
    fun updateStatusAndProgress(userCondition: Int){
        achievementStatus = userCondition >= condition

        val progress =  (userCondition * 100) / condition
        progressPercent = if (progress >= 100){
            100
        } else{
            progress
        }
    }
}