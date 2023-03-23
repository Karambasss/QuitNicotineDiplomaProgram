package com.michael.quitnicotine_application.data

class Disease(_diseaseName: String, _diseaseImage: Int, _diseaseDetailedText: String) {
    private val diseaseName = _diseaseName
    private val diseaseImage = _diseaseImage
    private val diseaseDetailedText = _diseaseDetailedText

    fun getDiseaseName() = diseaseName

    fun getDiseaseImage() = diseaseImage

    fun getDiseaseDetailedText() = diseaseDetailedText
}