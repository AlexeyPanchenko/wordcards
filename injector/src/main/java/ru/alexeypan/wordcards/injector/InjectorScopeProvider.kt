package ru.alexeypan.wordcards.injector

import kotlin.reflect.KClass

class InjectorScopeProvider<SCOPE : Scope>(
  private val scopeClass: KClass<SCOPE>
) : ScopeProvider<SCOPE> {
  override fun get(): SCOPE = Injector.get(scopeClass)
}