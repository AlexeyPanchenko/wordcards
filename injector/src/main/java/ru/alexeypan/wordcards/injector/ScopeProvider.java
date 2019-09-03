package ru.alexeypan.wordcards.injector;

import org.jetbrains.annotations.NotNull;

public interface ScopeProvider<SCOPE extends Scope> {
  @NotNull
  SCOPE get();
}
