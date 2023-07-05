package com.example.cardiocare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class RecordListTest {

    /**
     * This is a method to create mock recordList
     * This simply creates a instance of RecordList and also adds a mock Record and returns the list
     *  @return the recordList
     */
    private RecordList mockRecordList(){
        RecordList recordList = RecordList.getInstance();
        recordList.addRecord(mockRecord());
        return recordList;
    }

    /**
     * This is a method to create mock Record
     *  @return the record
     */
    private Record mockRecord(){
        return new Record(120,80,75,"11:05","15-7-2023","Good!");
    }

    /**
     * This is a method to test add record operation
     * It adds a record and checks the size of the list, expected 1
     * Then adds another record and again checks the size, expected 2
     * Then checks if the last record exists in list or not
     * Then deletes all the record from the list
     */
    @Test
    public void testAddRecord(){
        //initializing the recordList
        RecordList recordList = mockRecordList();
        //checking the size of the record after one data insertion
        assertEquals(1,recordList.getRecords().size());
        Record record  = new Record(125,78,78,"11:05","15-7-2023","Great!");
        //inserting another data
        recordList.addRecord(record);
        //checking the size of the record after another dara insertion
        assertEquals(2,recordList.getRecords().size());
        //checking if the record exits in the list
        assertTrue(recordList.getRecords().contains(record));
        //deleting all the records
        recordList.deleteAllRecords();
    }

    /**
     * This tests the add exception in list
     * Simply same record cannot be inserted twice, so it will simply throw IllegalArgumentException
     */
    @Test
    public void testAddRecordException(){
        RecordList recordList = mockRecordList();
        assertEquals(1,recordList.getRecords().size());
        Record record = mockRecord();
        recordList.addRecord(record);
        assertThrows(IllegalArgumentException.class, () -> {
            recordList.addRecord(record);
        });
        recordList.deleteAllRecords();
    }

    /**
     * This tests get record method
     * In this, two data have been inserted and after every insertion the size of the list has been checked
     * Also checked if appropriate data is present at expected index.
     */
    @Test
    public void testGetRecords(){
        RecordList recordList = RecordList.getInstance();
        Record record  = new Record(125,78,60,"10:05","15-7-2023","Nice!");
        recordList.addRecord(record);
        //Checking the size of the recordList
        assertEquals(1,recordList.getCount());
        assertTrue(recordList.getRecords().contains(record));
        assertEquals(0,recordList.getRecords().indexOf(record));
        recordList.deleteAllRecords();
    }

    /**
     * This tests get record method
     * In this, two data have been inserted and after every insertion the size of the list has been checked
     * Then collected the record list in a sorted order based on systolic pressure so the last record should be placed at index:0
     */
    @Test
    public void testGetSortedRecords(){
        RecordList recordList = RecordList.getInstance();
        Record record1  = new Record(125,78,60,"10:05","15-7-2023","");
        recordList.addRecord(record1);
        Record record2  = new Record(122,78,60,"10:05","15-7-2023","");
        recordList.addRecord(record2);
        //checking the expected output in sorted recordList
        assertEquals(0,recordList.getRecords(true).indexOf(record2));
        recordList.deleteAllRecords();
    }

    /**
     * This tests get record method
     * In this, one data have been inserted and after insertion the size of the list has been checked
     * Then deleted the record and check if the size becomes 0 or not
     * And the tried to delete from empty list and expected Exception
     */
    @Test
    public void testDeleteRecord(){
        RecordList recordList = mockRecordList();
        assertEquals(1,recordList.getCount());
        recordList.deleteRecord(0);
        assertEquals(0,recordList.getCount());
        //trying to delete from empty recordList
        assertThrows(IllegalArgumentException.class, () -> {
            recordList.deleteRecord(0);
        });
        recordList.deleteAllRecords();
    }

    /**
     * This tests get record method
     * In this, one data have been inserted and after insertion the size of the list has been checked
     * Then a new record object has been created and an update operation has been performed at index = ( previous data)
     * Then checked if updated data is present on the list at expected index
     */
    @Test
    public void testUpdateRecord(){
        RecordList recordList = RecordList.getInstance();
        Record record  = new Record(125,78,60,"12:05","15-7-2023","");
        recordList.addRecord(record);
        assertEquals(1,recordList.getCount());
        Record updatedRecord =  new Record(124,76,60,"12:04","15-5-2023","");
        recordList.updateRecord(0,updatedRecord);
        assertEquals(0,recordList.getRecords().indexOf(updatedRecord));
        assertEquals(124,recordList.getRecord(0).getSystolicPressure());
        //trying to update at illegal position
        assertThrows(IllegalArgumentException.class, () -> {
            recordList.updateRecord(1,updatedRecord);
        });
        recordList.deleteAllRecords();
    }

}

