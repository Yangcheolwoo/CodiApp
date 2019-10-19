package com.example.myapplication.Fragment.Menu4;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class ItemDataDecoration extends RecyclerView.ItemDecoration {
    private int size10;
    private int size5;

    public ItemDataDecoration(Context context){
        size10 = dpToPx(context,4);
        size5 = dpToPx(context,4);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if(position == 0 || position == 1){
            outRect.top = size10;
            outRect.bottom = size10;
        }else {
            outRect.bottom = size10;
        }

        GridLayoutManager.LayoutParams glm = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = glm.getSpanIndex();

        if(spanIndex == 0){
            outRect.left = size10;
            outRect.right = size5;
        }else if(spanIndex == 1){
            outRect.left = size5;
            outRect.right = size10;
        }

        outRect.top = size10;
        outRect.right = size10;
        outRect.left = size10;
        outRect.bottom = size10;
    }
    private int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
