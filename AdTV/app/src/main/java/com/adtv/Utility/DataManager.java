package com.adtv.Utility;

import java.util.ArrayList;

public class DataManager {

    /**
     * References to singleton instance of this class
     */
    private static DataManager sDataManager = null;
    private static ArrayList<String> mUrlList = null;

    private DataManager() {
        mUrlList = new ArrayList<>();
    }

    /**
     * Returns the current instance of this class
     *
     * @return instance of data manager
     */
    public static DataManager getInstance() {
        if (sDataManager == null)
            sDataManager = new DataManager();

        return sDataManager;
    }

    public ArrayList<String> getUrlList() {
        return mUrlList;
    }

    public void setUrlList(ArrayList<String> mUrlList) {
        this.mUrlList = mUrlList;
    }
}
