package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListModel extends ViewModel {
    private ArrayList<SchedulerEvent> data = new ArrayList<>();
    private MutableLiveData<List<SchedulerEvent>> listModel = new MutableLiveData<>(data);

    public MutableLiveData<List<SchedulerEvent>> getListModel(){
        return listModel;
    }
    public ArrayList<SchedulerEvent> getData(){
        return data;
    }
    public void addToList(SchedulerEvent newVal){
        data.add(newVal);
        listModel.setValue(data);
    }

}
