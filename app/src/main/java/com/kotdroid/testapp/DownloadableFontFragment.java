package com.kotdroid.testapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.FontRequest;
import android.provider.FontsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

/**
 * Created by user12 on 21/3/18.
 */

public class DownloadableFontFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private TextView textView;
    private TextView tvFontRequest;

    private String[] fontRequestArray;

    private ListPopupWindow listPopupWindow;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloadable_fonts, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.tvText);
        tvFontRequest = view.findViewById(R.id.tvRequestFont);
        tvFontRequest.setOnClickListener(this);

        fontRequestArray = getResources().getStringArray(R.array.font_requests);


    }


    @Override public void onClick(View view) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, fontRequestArray);
        listPopupWindow = new ListPopupWindow(getContext());
        listPopupWindow.setAnchorView(tvFontRequest);
        listPopupWindow.setModal(true);
        listPopupWindow.setAdapter(arrayAdapter);
        listPopupWindow.setOnItemClickListener(this);
        listPopupWindow.show();
    }

    @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        FontRequest fontRequest = new FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms", fontRequestArray[i]);

        FontsContract.FontRequestCallback callback = new FontsContract.FontRequestCallback() {
            @Override public void onTypefaceRetrieved(Typeface typeface) {
                textView.setText(getString(R.string.dummy_text));
                textView.setTypeface(typeface);

            }

            @Override public void onTypefaceRequestFailed(int reason) {
                Log.e("failed due to", "abc");
            }
        };

        Handler handler = new Handler();
        FontsContract.requestFonts(getContext(), fontRequest, handler, null,
                callback);

        listPopupWindow.dismiss();
    }
}
