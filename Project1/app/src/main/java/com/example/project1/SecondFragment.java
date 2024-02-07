package com.example.project1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.FragmentFirstBinding;
import com.example.project1.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private EventListModel model;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(this).get(EventListModel.class);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_thirdFragment);
            }
        });
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        binding.submitEvent.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                ArrayList<SchedulerEvent> events = model.getData();
                Log.d("Hello", events.toString());
                android.text.Editable val = binding.eventName.getText();
                android.text.Editable val2 = binding.eventTime.getText();
                android.text.Editable val3 = binding.descriptionInput.getText();
                ArrayList<String> days = new ArrayList<>();

                if(val!=null&&val2!=null&&val3!=null){
                    String eventName = val.toString();
                    String eventTime = val2.toString();
                    String description = val3.toString();
                    if(binding.mondayCheck.isChecked()){
                        days.add("Monday");
                    }
                    if(binding.tuesdayCheck.isChecked()){
                        days.add("Tuesday");
                    }
                    if(binding.wednesdayCheck.isChecked()){
                        days.add("Wednesday");
                    }
                    if(binding.thursdayCheck.isChecked()){
                        days.add("Thursday");
                    }
                    if(binding.fridayCheck.isChecked()){
                        days.add("Friday");
                    }
                    try{
                        SchedulerEvent newEvent = new SchedulerEvent(eventName, eventTime, days,description);
                        boolean cont = true;
                        for(SchedulerEvent event: events){
                            if(event.checkOverlap(newEvent)){
                                cont = false;
                                binding.errorText.setTextColor(getResources().getColor(R.color.red));
                                binding.errorText.clearComposingText();
                                binding.errorText.setText(R.string.overlapError);
                                break;
                            }
                        }
                        if(cont){
                            binding.errorText.clearComposingText();
                            binding.errorText.setTextColor(getResources().getColor(R.color.green));
                            binding.errorText.setText(R.string.succesfulAdd);
                            binding.errorText.setVisibility(View.VISIBLE);
                            model.addToList(newEvent);
                            resetViews();
                        }
                    }catch( IllegalArgumentException e){
                        binding.errorText.setTextColor(getResources().getColor(R.color.red));
                        binding.errorText.clearComposingText();
                        binding.errorText.setText(R.string.timeError);
                        binding.errorText.setVisibility(View.VISIBLE);
                    }
                }

            }

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void resetViews(){
        binding.mondayCheck.setChecked(false);
        binding.tuesdayCheck.setChecked(false);
        binding.wednesdayCheck.setChecked(false);
        binding.thursdayCheck.setChecked(false);
        binding.fridayCheck.setChecked(false);
        Objects.requireNonNull(binding.eventTime.getText()).clear();
        Objects.requireNonNull(binding.eventName.getText()).clear();
        Objects.requireNonNull(binding.descriptionInput.getText()).clear();
    }

}