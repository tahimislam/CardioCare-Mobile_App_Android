package com.example.cardiocare;

import java.util.Comparator;

public class RecordComparatorOnSystolicPressure implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return r1.getSystolicPressure() - r2.getSystolicPressure();
    }
}
