package ru.spartak.surfandroidschool.domain

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPreferenceHelper @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("userSharedPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveData(key:String, value: String?) {
        editor.putString(key, value).apply()
    }

    fun loadData(key: String):String? {
        return sharedPreferences.getString(key, null)
    }

    fun clear() {
        editor.clear().apply()
    }

}