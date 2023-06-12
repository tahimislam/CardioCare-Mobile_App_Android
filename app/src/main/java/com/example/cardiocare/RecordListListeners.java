package com.example.cardiocare;

public interface RecordListListeners {
    /**
     * This is a interface to delete a particular record from the list via recycler view
     * @param position
     */
    void onRecordDeleteListener(int position);
}