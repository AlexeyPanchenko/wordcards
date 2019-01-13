package ru.alexeypan.wordcards.injector

import java.util.*

object Injector {

  private val scopes = HashMap<Class<*>, Scope>()

  fun <T : Scope> registerScope(key: Class<T>, scope: Scope){
    scopes[key] = scope
  }

  fun <T : Scope> openScope(clazz: Class<T>): T {
    val scope = scopes[clazz] ?: throw throwNotRegisterException(clazz)
    scope.open()
    return scope as T
  }

  fun <T : Scope> closeScope(clazz: Class<T>): T {
    val scope = scopes[clazz] ?: throw throwNotRegisterException(clazz)
    scope.close()
    return scope as T
  }

  private fun throwNotRegisterException(clazz: Class<*>): IllegalStateException {
    return IllegalStateException("Scope ${clazz.name} is not register. Register it in you Application class")
  }
}
