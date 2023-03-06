package com.michael.quitnicotine_application.data

import android.net.Uri

class Category(_categoryName: String, _categoryImage: Int, _categoryLink: Uri) {
    private var categoryName = _categoryName
    private var categoryImage = _categoryImage
    private var categoryLink = _categoryLink

    fun getCategoryName() = categoryName

    fun getCategoryImage() = categoryImage

    fun getCategoryLink() = categoryLink
}