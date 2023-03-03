package com.michael.quitnicotine_application.data

class UserData(_name: String, _cigarettesCount: Int,
               _packetPrice: Int,
               _achievements: MutableList<Achievement>) {

    // Константа
    private val cigarettesInAPacket = 20    // сигарет в пачке

    // Изменяющиеся данные - Данные о пользователе
    private var userName = _name    // имя пользователя
    private var cigarettesCount = _cigarettesCount  // количество сигарет, выкуренных пользователем за день
    private var packetPrice = _packetPrice  // цена пачки сигарет
    private var achievements = _achievements   // достижения

    // Изменяющиеся данные - Цели пользователя
    private var goal1DayCount : Int? = null
    private var goal2ProductName : String? = null
    private var goal2ProductPrice : Int? = null

    // Изменяющиеся данные
    private var dayCount = 0     // кол-во дней с отказа от курения
    private var savedMoney = 0.0  // сэкономленные деньги
    private var savedCigarettes = 0    // сколько сигарет не было выкурено с момента отказа от курения

    // Получение данных
    fun getUserName() = userName

    fun getCigarettesCount() = cigarettesCount

    fun getPacketPrice() = packetPrice

    fun getDayCount() = dayCount

    fun getAchievements() = achievements

    fun getSavedMoney() = savedMoney

    fun getSavedCigarettes() = savedCigarettes

    fun getCigarettesInAPacket() = cigarettesInAPacket

    fun getGoal1DayCount() = goal1DayCount

    fun getGoal2ProductName() = goal2ProductName

    fun getGoal2ProductPrice() = goal2ProductPrice

    // Изменение данных
    fun setUserName(_name : String) {
        userName = _name
    }

    fun setCigarettesCount(_cigarettesCount : Int) {
        cigarettesCount = _cigarettesCount
    }

    fun setPacketPrice(_packetPrice : Int) {
        packetPrice = _packetPrice
    }

    fun setGoal1DayCount(_dayCount : Int?) {
        goal1DayCount = _dayCount
    }

    fun setGoal2ProductName(_productName : String?){
        goal2ProductName = _productName
    }

    fun setGoal2ProductPrice(_productPrice : Int?){
        goal2ProductPrice = _productPrice
    }

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