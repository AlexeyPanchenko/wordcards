package ru.alexeypan.wordcards.injector;

import org.jetbrains.annotations.NotNull;

public interface ScopeFactory<SCOPE extends Scope> {
  @NotNull
  SCOPE create();
}
