package com.example.scholarsnews.models

data class Scholarship(
    val id: String = "",
    val title: String = "",
    val institution: String = "", // kurum
    val amount: String = "",      // miktar
    val description: String = "",
    val deadline: String = "",    // Son başvuru tarihi
    val imageUrl: String = "",
    val url: String
)