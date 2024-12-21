package com.example.beautydate;

import android.util.Log;

public class CosmeticDescription {
    String itemName;
    String itemCategory;
    int lifePeriodOfItem;

    String snapShot;

    public CosmeticDescription() {

    }

    public CosmeticDescription(String snap) {
        this.snapShot = snap;
    }

    void setDescription() {
        String tempSt = this.snapShot;
        String cutSt;

        cutSt = tempSt.substring(tempSt.indexOf("name") + 5, tempSt.indexOf(", category"));
        this.itemName = cutSt;
        tempSt = tempSt.substring(tempSt.indexOf(", category") + 2);

        cutSt = tempSt.substring(tempSt.indexOf("category") + 9, tempSt.indexOf("}"));
        this.itemCategory = cutSt;

        Log.d("itemName", this.itemName);
        Log.d("itemCategory", this.itemCategory);
    }

    String getItemName() {
        return this.itemName;
    }

    String getItemCategory() {
        return this.itemCategory;
    }

    int getLifePeriodOfItem() {
        return lifePeriodOfItem;
    }

    void setLifePeriodOfItem(int life) {
        this.lifePeriodOfItem = life;
    }
}
