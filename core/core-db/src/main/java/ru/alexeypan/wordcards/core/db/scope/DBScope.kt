package ru.alexeypan.wordcards.core.db.scope

import android.content.Context
import androidx.room.Room
import ru.alexeypan.wordcards.core.db.APP_DATABASE_NAME
import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.injector.Scope

class DBScope(context: Context) : Scope {

  val database: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
    .allowMainThreadQueries()
    .build()
}