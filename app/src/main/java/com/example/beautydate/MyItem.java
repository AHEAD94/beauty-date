package com.example.beautydate;

import android.util.Log;

public class MyItem {
    String snapShot;

    String serialNumber;
    String itemName;
    String openDate;
    String expDate;
    String alarmState = "켬";
    String life;
    String calculated;

    public MyItem() {

    }

    public MyItem(String myItemSnapShot) {
        this.snapShot = myItemSnapShot;
    }

    public void setItemInfo() {
        String tempSt = this.snapShot;
        String cutSt;

        cutSt = tempSt.substring(tempSt.indexOf("name") + 5, tempSt.indexOf(", SN"));
        this.itemName = (cutSt);
        tempSt = tempSt.substring(tempSt.indexOf(", SN") + 2);
        if (tempSt.contains("calculated")) {
            cutSt = tempSt.substring(tempSt.indexOf("SN") + 3, tempSt.indexOf(","));
            this.serialNumber = cutSt;
            tempSt = tempSt.substring(tempSt.indexOf(",") + 2);

            cutSt = tempSt.substring(tempSt.indexOf("openDate") + 9, tempSt.indexOf(","));
            this.openDate = (cutSt);
            tempSt = tempSt.substring(tempSt.indexOf(",") + 2);

            cutSt = tempSt.substring(tempSt.indexOf("calculated") + 11, tempSt.indexOf(","));
            this.calculated = (cutSt);
            tempSt = tempSt.substring(tempSt.indexOf(",") + 2);

            cutSt = tempSt.substring(tempSt.indexOf("expDate") + 8, tempSt.indexOf(","));
            this.expDate = (cutSt);
            tempSt = tempSt.substring(tempSt.indexOf(",") + 2);

            cutSt = tempSt.substring(tempSt.indexOf("life") + 5, tempSt.indexOf("}"));
            this.life = (cutSt);
        }

        Log.d("itemName", (this.itemName));
        if (this.serialNumber != null) {
            Log.d("serialNumber", this.serialNumber);
            Log.d("openDate", (this.openDate));
            Log.d("calculated", (this.calculated));
            Log.d("expDate", (this.expDate));
            Log.d("life", (this.life));
        }
    }

    String getItemName() {
        return this.itemName;
    }

    String getSerialNumber() { return this.serialNumber; }

    String getOpenDate() {
        return this.openDate;
    }

    String getCalculated() { return this.calculated; }

    String getExpirationDate() {
        return this.expDate;
    }

    String getLife() { return this.life; }

    String getAlarmState() { return this.alarmState; }
}
