package com.upadhyay.nilay.todoappsqlite;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
/**
 * Created by nilay on 8/15/2017.
 */

public class DatePickerFragment extends DialogFragment {
    OnDateSetListener onDateSet;

    public DatePickerFragment(){

    }
    public void setCallBack(OnDateSetListener onDate){
        onDateSet = onDate;
    }
    private int year,month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getContext(),R.style.AppDialogTheme,onDateSet,year,month,day);
    }
}
