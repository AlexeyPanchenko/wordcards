package ru.alexeypan.wordcards.core.ui.recycler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public interface VHBinder<HOLDER extends RecyclerView.ViewHolder, MODEL> {

  void onBindViewHolder(@NonNull HOLDER holder, int position, @Nullable MODEL model);
}
