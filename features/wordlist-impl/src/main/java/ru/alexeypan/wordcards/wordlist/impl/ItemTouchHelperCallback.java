package ru.alexeypan.wordcards.wordlist.impl;

import android.graphics.Canvas;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {

  private final RecyclerView.Adapter adapter;
  private List<T> dataList;
  private OnSlideListener<T> mListener;

  public ItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter) {
    this.adapter = checkIsNull(adapter);
  }

  public ItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, OnSlideListener<T> listener) {
    this.adapter = checkIsNull(adapter);
    this.mListener = listener;
  }

  public void setDataList(List<T> dataList) {
    this.dataList = dataList;
  }

  private <T> T checkIsNull(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    return t;
  }

  public void setOnSlideListener(OnSlideListener<T> mListener) {
    this.mListener = mListener;
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    int dragFlags = 0;
    int slideFlags = 0;
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof SlideLayoutManager) {
      slideFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    }
    return makeMovementFlags(dragFlags, slideFlags);
  }

  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    return false;
  }

  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    viewHolder.itemView.setOnTouchListener(null);
    int layoutPosition = viewHolder.getLayoutPosition();
    T remove = dataList.remove(layoutPosition);
    adapter.notifyDataSetChanged();
    if (mListener != null) {
      mListener.onSlided(viewHolder, remove, direction == ItemTouchHelper.LEFT ? ItemConfig.SLIDED_LEFT : ItemConfig.SLIDED_RIGHT);
    }
    if (adapter.getItemCount() == 0) {
      if (mListener != null) {
        mListener.onClear();
      }
    }
  }

  @Override
  public boolean isItemViewSwipeEnabled() {
    return false;
  }

  @Override
  public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    View itemView = viewHolder.itemView;
    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
      float ratio = dX / getThreshold(recyclerView, viewHolder);
      if (ratio > 1) {
        ratio = 1;
      } else if (ratio < -1) {
        ratio = -1;
      }
      itemView.setRotation(ratio * ItemConfig.DEFAULT_ROTATE_DEGREE);
      if (mListener != null) {
        if (ratio != 0) {
          mListener.onSliding(viewHolder, ratio, ratio < 0 ? ItemConfig.SLIDING_LEFT : ItemConfig.SLIDING_RIGHT);
        } else {
          mListener.onSliding(viewHolder, ratio, ItemConfig.SLIDING_NONE);
        }
      }
    }
  }

  @Override
  public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);
    viewHolder.itemView.setRotation(0f);
  }

  private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
  }

}
