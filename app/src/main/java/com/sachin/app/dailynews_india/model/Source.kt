package com.sachin.app.dailynews_india.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Source(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)