package com.example.chenx.sharebook;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class myItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter itemTouchHelperAdapter;

    public myItemTouchHelperCallBack(ItemTouchHelperAdapter itemTouchHelperAdapter){
        this.itemTouchHelperAdapter=itemTouchHelperAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeflags=ItemTouchHelper.LEFT;
        return makeMovementFlags(0,swipeflags);//可以向左滑动
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {

        return false;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final int position=viewHolder.getAdapterPosition();
        Snackbar.make(viewHolder.itemView,"Data Delete",Snackbar.LENGTH_SHORT)
                .setAction("撤回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemTouchHelperAdapter.onIteamInsert(position);
                    }
                }).show();
        itemTouchHelperAdapter.onIteamDelete(position);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }


    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
        //return super.isItemViewSwipeEnabled();
    }


}
