package com.michael.quitnicotine_application.data

import android.net.Uri

class Idea(_ideaHeading: String, _ideaImage: Int, _ideaDetailedText: String) {
    private var ideaHeading = _ideaHeading
    private var ideaImage = _ideaImage
    private var ideaDetailedText = _ideaDetailedText
    private var visible = false

    fun getIdeaHeading() = ideaHeading

    fun getIdeaImage() = ideaImage

    fun getIdeaDetailedText() = ideaDetailedText

    fun isVisible() = visible

    fun setVisible(_visible: Boolean){
        visible = _visible
    }

}