package ru.alexeypan.wordcards.core.ui.recycler;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

public interface VHFactory<HOLDER extends BaseVH> {
  @NonNull
  HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType);
}
