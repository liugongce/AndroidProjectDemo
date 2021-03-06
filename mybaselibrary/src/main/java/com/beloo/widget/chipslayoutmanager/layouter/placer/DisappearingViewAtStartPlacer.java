package com.beloo.widget.chipslayoutmanager.layouter.placer;

import android.support.v7.widget.RecyclerView;
import android.view.View;

class DisappearingViewAtStartPlacer extends AbstractPlacer {

    DisappearingViewAtStartPlacer(RecyclerView.LayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public void addView(View view) {
        getLayoutManager().addDisappearingView(view, 0);

//        Log.i("added disappearing view, position = " + getLayoutManager().getPosition(view));
//        Log.d("name = " + ((TextView)view.findViewById(R.id.tvName)).getText().toString());
    }
}
