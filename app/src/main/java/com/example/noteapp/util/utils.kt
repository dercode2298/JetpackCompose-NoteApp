package com.example.noteapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(time: Long):String{
    val date = Date(time)
    val format = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
    return format.format(date)
}