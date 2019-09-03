package ru.alexeypan.wordcards.injector

interface Scope {
  /**
   * Override it if you want to release all resources of your scope.
   */
  fun close() {}
}

class LazyScope<SCOPE : Scope>(
  private val factory: ScopeFactory<SCOPE>
) : Scope, ScopeProvider<SCOPE> {

  @Volatile
  private var scope: SCOPE? = null

  override fun get(): SCOPE {
    if (scope == null) {
      synchronized(this) {
        if (scope == null) {
          scope = factory.create()
        }
      }
    }
    return scope!!
  }

  override fun close() {
    scope?.close()
    scope = null
  }
}