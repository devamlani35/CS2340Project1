package com.example.project1;

import static java.lang.String.*;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.ThirdFragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThirdFragment extends Fragment {
    private ThirdFragmentBinding binding;
    private EventListModel model;
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ThirdFragmentBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(this).get(EventListModel.class);


        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(EventListModel.class);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ThirdFragment.this)
                    .navigate(R.id.action_thirdFragment_to_SecondFragment);

            }
        });


        TableRow headerRow = new TableRow(getContext());
        headerRow.setBackgroundColor(getResources().getColor(R.color.navy));
        List<SchedulerEvent> listModel = model.getData();
        createCalenderEvents(listModel);


    }
    public void createCalenderEvents(List<SchedulerEvent> list){
        if(list==null){
            Log.d("NOOO","This is here");
            return;
        }
        for(SchedulerEvent event:list){
            CardView card = generateCard(event);
            ArrayList<String> days = event.getDays();
            if(days.contains("Monday")){
                binding.mondayStack.addView(card);
            }
            if(days.contains("Tuesday")){
                binding.tuesdayStack.addView(card);
            }
            if(days.contains("Wednesday")){
                binding.wednesdayStack.addView(card);
            }
            if(days.contains("Thursday")){
                binding.thursdayStack.addView(card);
            }
            if(days.contains("Friday")){
                binding.fridayStack.addView(card);
            }
        }
    }
    public CardView generateCard(SchedulerEvent event){
        CardView newCard = new CardView(requireContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        newCard.setLayoutParams(params);
        newCard.setPadding(0, 10, 0, 10);
        newCard.setCardBackgroundColor(getResources().getColor(R.color.navy));
        newCard.setRadius(5);
        LinearLayout cardLayout = new LinearLayout(requireContext());
        ViewGroup.LayoutParams layoutparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        cardLayout.setLayoutParams(layoutparams);
        TextView time = new TextView(requireContext());
        time.setTextColor(getResources().getColor(R.color.navy));
        time.setTextSize(8);
        time.setText(format("%s - %s", reformatTime(event.getStartTime()), reformatTime(event.getEndTime())));
        time.setPadding(0,5,0,5);
        TextView title = new TextView(requireContext());
        title.setTextSize(16);
        title.setTextColor(getResources().getColor(R.color.navy));
        title.setText(event.getName());
        title.setPadding(0,5,0,5);
        cardLayout.addView(time);
        cardLayout.addView(title);
        return newCard;
    }
    @SuppressLint("DefaultLocale")
    public String reformatTime(int time){
        String retString;
        String endString = "";
        if(time>=1200){
            endString="PM";
        }else{
            endString = "AM";
        }
        retString = format("%d:%d%s", time/100, time%1200, endString);
        return retString;
    }
}
