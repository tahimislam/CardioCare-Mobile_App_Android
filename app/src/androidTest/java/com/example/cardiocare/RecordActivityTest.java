package com.example.cardiocare;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecordActivityTest {
    @Rule
    public ActivityScenarioRule<RecordActivity> activityRule =
            new ActivityScenarioRule<>(RecordActivity.class);

    @Test
    public void testAddRecord(){
        onView(withId(R.id.main_create_button)).perform(click());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }

    @Test
    public void testUpdateRecord(){
        onView(withId(R.id.main_create_button)).perform(click());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,CustomRecyclerViewAction.clickChildViewWithId(R.id.r_r_v_i_edit_button)));

        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(clearText());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("130"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        Record record = RecordList.getInstance().getRecord(0);
        assertThat(record.getSystolicPressure(),is(130));

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }

    @Test
    public void testDeleteRecord(){
        onView(withId(R.id.main_create_button)).perform(click());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,CustomRecyclerViewAction.clickChildViewWithId(R.id.r_r_v_i_delete_button)));

        onView(withId(R.id.delete_record_dialog)).check(matches(isDisplayed()));
        onView(withId(R.id.delete_record_dialog_confirm_button)).perform(click());

        int size = RecordList.getInstance().getCount();
        assertThat(size,is(0));

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }

    @Test
    public void testDetails(){
        onView(withId(R.id.main_create_button)).perform(click());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,CustomRecyclerViewAction.clickChildViewWithId(R.id.r_r_v_i_view_button)));

        onView(withId(R.id.activity_record_details_sys_pressure)).check(matches(withText("120 mmHg")));
        onView(withId(R.id.activity_record_details_dia_pressure)).check(matches(withText("80 mmHg")));
        onView(withId(R.id.activity_record_details_heart_rate)).check(matches(withText("150 bits/min")));
        onView(withId(R.id.activity_record_details_time_measured)).check(matches(withText("1:5")));
        onView(withId(R.id.activity_record_details_date_measured)).check(matches(withText("22-07-2023")));

        Espresso.pressBack();

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }

    @Test
    public void testIntentOpen(){
        Intents.init();
        onView(withId(R.id.main_create_button)).perform(click());
        Intents.intended(hasComponent(InsertRecordActivity.class.getName()));
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,CustomRecyclerViewAction.clickChildViewWithId(R.id.r_r_v_i_view_button)));

        Intents.intended(hasComponent(RecordDetailsActivity.class.getName()));

        Record record = RecordList.getInstance().getRecord(0);

        onView(withId(R.id.activity_record_details_sys_pressure)).check(matches(withText("120 mmHg")));
        onView(withId(R.id.activity_record_details_dia_pressure)).check(matches(withText("80 mmHg")));
        onView(withId(R.id.activity_record_details_heart_rate)).check(matches(withText("150 bits/min")));
        onView(withId(R.id.activity_record_details_time_measured)).check(matches(withText("1:5")));
        onView(withId(R.id.activity_record_details_date_measured)).check(matches(withText("22-07-2023")));

        Espresso.pressBack();

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());

    }

    @Test
    public void testAddRecordToDb(){
        List<Record> records = new ArrayList<Record>();

        assertThat(records.size(),is(0));
        onView(withId(R.id.main_create_button)).perform(click());
//        Record record = new Record(120,80,150,"1:5","22-07-2022","");
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        records = Helpers.getRecordsFromDB(InstrumentationRegistry.getInstrumentation().getTargetContext());

        assertThat(records.size(),is(1));

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }

    @Test
    public  void testUpdateRecordToDb(){
        List<Record> records = new ArrayList<Record>();
        assertThat(records.size(),is(0));

        onView(withId(R.id.main_create_button)).perform(click());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,CustomRecyclerViewAction.clickChildViewWithId(R.id.r_r_v_i_edit_button)));

        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(clearText());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("130"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        records = Helpers.getRecordsFromDB(InstrumentationRegistry.getInstrumentation().getTargetContext());

        Record record = records.get(0);
        assertThat(record.getSystolicPressure(),is(130));

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }

    @Test
    public void testDeleteRecordFromDb(){
        List<Record> records = new ArrayList<Record>();

        assertThat(records.size(),is(0));
        onView(withId(R.id.main_create_button)).perform(click());
        onView(withId(R.id.coe_record_systolic_edit_text_input)).perform(typeText("120"));
        onView(withId(R.id.coe_record_diastolic_edit_text_input)).perform(typeText("80"));
        onView(withId(R.id.coe_record_heart_rate_edit_text_input)).perform(typeText("150"));
        onView(withId(R.id.coe_record_time_measured_edit_text_input)).perform(typeText("1:5"));
        onView(withId(R.id.coe_record_date_edit_text_input)).perform(typeText("22-07-2023"));
        onView(withId(R.id.coe_record_action_button)).perform(click());

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,CustomRecyclerViewAction.clickChildViewWithId(R.id.r_r_v_i_delete_button)));

        //onView(withId(R.id.delete_record_dialog)).check(matches(isDisplayed()));
        onView(withId(R.id.delete_record_dialog_confirm_button)).perform(click());

        records = Helpers.getRecordsFromDB(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertThat(records.size(),is(0));

        RecordList.getInstance().deleteAllRecords();
        Helpers.setRecordsToDB(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecordList.getInstance().getRecords());
    }
}

