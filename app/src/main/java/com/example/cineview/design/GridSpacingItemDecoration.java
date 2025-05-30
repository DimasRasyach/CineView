package com.example.cineview.design;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spanCount;       // Jumlah kolom
    private final int spacing;         // Spasi antar item (dalam px)
    private final boolean includeEdge; // Apakah tepi kiri/kanan dikasih spasi

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // posisi item
        int column = position % spanCount; // posisi kolom item

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount) { // baris pertama
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bawah
        } else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;

            if (position >= spanCount) {
                outRect.top = spacing; // item di atas baris pertama
            }
        }
    }
}
