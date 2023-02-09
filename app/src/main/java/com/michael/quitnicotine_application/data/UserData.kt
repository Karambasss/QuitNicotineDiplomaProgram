package com.michael.quitnicotine_application.data

class UserData(_name: String, _cigarettesCount: Int,
               _packetPrice: Int, _dayCount: Int = 0,
               _achievements: MutableList<Achievement>,
               _savedMoney: Double = 0.0, _savedCigarettes: Int = 0) {

    // Константы
    private val cigarettesInAPacket = 20    // сигарет в пачке
    private val userName = _name    // имя пользователя
    private val cigarettesCount = _cigarettesCount  // количество сигарет, выкуренных пользователем за день
    private val packetPrice = _packetPrice  // цена пачки сигарет
    private var achievements = _achievements   // достижения

    // Изменяющиеся данные
    private var dayCount = _dayCount     // кол-во дней с отказа от курения
    private var savedMoney = _savedMoney  // сэкономленные деньги
    private var savedCigarettes = _savedCigarettes    // сколько сигарет не было выкурено с момента отказа от курения

    fun getUserName() = userName

    fun getCigarettesCount() = cigarettesCount

    fun getPacketPrice() = packetPrice

    fun getDayCount() = dayCount

    fun getAchievements() = achievements

    fun getSavedMoney() = savedMoney

    fun getSavedCigarettes() = savedCigarettes

    fun getCigarettesInAPacket() = cigarettesInAPacket

    // Метод для обновления сэкономленных денег
    fun updateSavedMoney(){
        savedMoney+= (packetPrice/(cigarettesInAPacket / cigarettesCount))
    }

    // Метод для обновления количества не выкуренных сигарет
    fun updateSavedCigarettes(){
        savedCigarettes+= (cigarettesCount * dayCount)
    }

    // Метод дл обновления количества дней с момента отказа от курения
    fun updateDayCount(newDay: Int){
        dayCount += newDay
    }
}