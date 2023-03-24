package com.michael.quitnicotine_application.data

class Organ(_organName: String, _organImage: Int, _organDetailedText: String) {
    private val organName = _organName
    private val organImage = _organImage
    private val organDetailedText = _organDetailedText
    private var visible = false

    fun getOrganName() = organName

    fun getOrganImage() = organImage

    fun getOrganDetailedText() = organDetailedText

    fun isVisible() = visible

    fun setVisible(_visible: Boolean){
        visible = _visible
    }
}