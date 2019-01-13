package ru.alexeypan.wordcards.core.db.scope

import android.content.Context
import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.core.db.AppDatabaseImpl
import ru.alexeypan.wordcards.injector.Scope

class DBScope(private val context: Context) : Scope {

  private var appDatabase: AppDatabase? = null

  override fun open() {
    if (appDatabase == null) {
      appDatabase = AppDatabaseImpl.getInstance(context)
    }
  }

  override fun close() {
    AppDatabaseImpl.destroyInstance()
  }

  fun appDatabase(): AppDatabase? = appDatabase
}