package com.example.newsapp.utils


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type


fun Any.serialize(): String = Gson().toJson(this)

fun <T> String.deserialize(clazz: Class<T>): T {
    val obj = Gson().fromJson(this, clazz)

    return obj as T
}