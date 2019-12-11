package com.as1124.ui.layout.recycler;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public abstract class RecyclerListInitializer implements Serializable {

    public abstract void initRecyclerList(RecyclerView container);
}
