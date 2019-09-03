package ru.alexeypan.wordcards.injector

import kotlin.reflect.KClass

class Injector {

  companion object : SingletonHolder<Injector>({ Injector() }) {

    fun <SCOPE : Scope> get(scopeClass: KClass<SCOPE>) = getInstance().getScope(scopeClass)
  }

  private val scopes = HashMap<KClass<*>, LazyScope<*>>()

  fun <SCOPE : Scope> getScope(scopeClass: KClass<SCOPE>): SCOPE {
    val lazyScope: LazyScope<SCOPE> = scopes[scopeClass] as LazyScope<SCOPE>?
      ?: throw IllegalStateException("Init scope $scopeClass first")
    return lazyScope.get()
  }

  fun <SCOPE : Scope> putScope(scopeClass: KClass<SCOPE>, factory: ScopeFactory<SCOPE>) {
    scopes.put(scopeClass, LazyScope(factory))
  }

  fun close(scopeClass: KClass<out Scope>) {
    scopes.get(scopeClass)?.close()
  }
}

open class SingletonHolder<out T : Any>(creator: () -> T) {

  private var creator: (() -> T)? = creator
  @Volatile
  private var instance: T? = null

  fun getInstance(): T {
    val localInstance = instance
    if (localInstance != null) {
      return localInstance
    }

    synchronized(this) {
      val syncInstance = instance
      if (syncInstance != null) {
        return syncInstance
      } else {
        val createdInstance = creator!!.invoke()
        instance = createdInstance
        creator = null
        return createdInstance
      }
    }
  }
}