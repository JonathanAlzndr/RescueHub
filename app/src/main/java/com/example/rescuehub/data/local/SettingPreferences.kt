package com.example.rescuehub.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit {
            it[USERNAME] = user.username
            it[IS_LOGIN] = true
            it[IS_FIRST_LAUNCH] = false
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[USERNAME] ?: "",
                it[IS_FIRST_LAUNCH] ?: true,
                it[IS_LOGIN] ?: false
            )
        }
    }

    suspend fun saveLaunchStatus(isFirstLaunch: Boolean) {
        dataStore.edit {
            it[IS_FIRST_LAUNCH] = isFirstLaunch
            it[IS_LOGIN] = false
            it[USERNAME] = ""
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[USERNAME] = ""
            it[IS_LOGIN] = false
            it[IS_FIRST_LAUNCH] = false
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

        private val USERNAME = stringPreferencesKey("username")
        private val IS_LOGIN = booleanPreferencesKey("is_login")
        private val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private const val TAG = "SettingPreferences"
    }
}