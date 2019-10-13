package com.kenschenke.ftabuddy;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlashCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlashCardsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FlashCardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlashCardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FlashCardsFragment newInstance(String param1, String param2) {
        FlashCardsFragment fragment = new FlashCardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flash_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonEthernet = view.findViewById(R.id.buttonEthernet);
        buttonEthernet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlashCard(R.string.flashcard_ethernet);
            }
        });

        Button buttonComeHere = view.findViewById(R.id.buttonComeHere);
        buttonComeHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlashCard(R.string.flashcard_come_here);
            }
        });

        Button buttonTurnOnRobot = view.findViewById(R.id.buttonTurnOnRobot);
        buttonTurnOnRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlashCard(R.string.flashcard_turn_on_robot);
            }
        });

        Button buttonReboot = view.findViewById(R.id.buttonReboot);
        buttonReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlashCard(R.string.flashcard_reboot_robot);
            }
        });
    }

    private void showFlashCard(int stringId) {
        Intent intent = new Intent(getView().getContext(), FlashCardActivity.class);
        intent.putExtra("Content", stringId);

        startActivityForResult(intent, 1);
    }

}
