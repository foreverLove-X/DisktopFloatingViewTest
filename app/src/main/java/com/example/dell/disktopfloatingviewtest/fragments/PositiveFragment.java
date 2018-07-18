package com.example.dell.disktopfloatingviewtest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.dell.disktopfloatingviewtest.R;

public class PositiveFragment extends Fragment {
    private ImageButton photo, media, talk;
    private Button dels;
    private ImageButton tabs[];
    private RelativeLayout mRelativeLayout;

    public PositiveFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.positive_fragment, container, false);
        photo = view.findViewById (R.id.imgs);
        media = view.findViewById (R.id.avi);
        talk = view.findViewById (R.id.speak);
        dels= view.findViewById (R.id.error);
        mRelativeLayout = view.findViewById (R.id.img_demo);

        tabs = new ImageButton[]{photo, media, talk};
        tabs[0].setBackgroundResource (R.drawable.img_t);

        photo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                tabs[0].setBackgroundResource (R.drawable.img_t);
                tabs[1].setBackgroundResource (R.drawable.mp4_f);
                tabs[2].setBackgroundResource (R.drawable.talk_f);
            }
        });

        media.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                tabs[0].setBackgroundResource (R.drawable.img_f);
                tabs[1].setBackgroundResource (R.drawable.mp4_t);
                tabs[2].setBackgroundResource (R.drawable.talk_f);
            }
        });

        talk.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                tabs[0].setBackgroundResource (R.drawable.img_f);
                tabs[1].setBackgroundResource (R.drawable.mp4_f);
                tabs[2].setBackgroundResource (R.drawable.talk_t);
            }
        });

        dels.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                mRelativeLayout.setVisibility (View.GONE);
            }
        });

        return view;
    }
}
