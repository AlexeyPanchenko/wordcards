package ru.alexeypan.wordcards.injector

object Injector {

  private val scopes = HashMap<Class<*>, Scope>()

  fun <T : Scope> registerScope(key: Class<T>, scope: Scope, overwrite: Boolean = false){
    if (overwrite) {
      scopes[key] = scope
    } else if (!scopes.containsKey(key)) {
      scopes[key] = scope
    }
  }

  fun <T : Scope> unregisterScope(key: Class<T>){
    scopes.remove(key)
  }

  fun <T : Scope> openScope(clazz: Class<T>): T {
    val scope = scopes[clazz] ?: throw throwNotRegisterException(clazz)
    scope.open()
    return scope as T
  }

  fun <T : Scope> openScope(clazz: Class<T>, scope: Scope, overwrite: Boolean = false): T {
    registerScope(clazz, scope, overwrite)
    return openScope(clazz)
  }

  fun <T : Scope> closeScope(clazz: Class<T>, unregister: Boolean = false): T {
    val scope = scopes[clazz] ?: throw throwNotRegisterException(clazz)
    scope.close()
    if (unregister) {
      unregisterScope(clazz)
    }
    return scope as T
  }

  private fun throwNotRegisterException(clazz: Class<*>): IllegalStateException {
    return IllegalStateException("Scope ${clazz.name} is not register. Register it in you Application class")
  }
}
