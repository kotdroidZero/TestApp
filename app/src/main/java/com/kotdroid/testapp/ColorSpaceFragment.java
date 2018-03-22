package com.kotdroid.testapp;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user12 on 21/3/18.
 */

public class ColorSpaceFragment extends Fragment {

    @BindView(R.id.tvAdobeRgb) TextView tvAdobeRgb;
    @BindView(R.id.tvDisplayP3) TextView tvDisplayP3;
    @BindView(R.id.tvRgb) TextView tvRgb;
    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_space,container,false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        //color instance in sRGB
        Color c=Color.valueOf(1.0f,0.26f,0.43f);

        //color instance in AdobeRGB
        Color d=Color.valueOf(1.0f,0.26f,0.43f,1.0f,ColorSpace.get(ColorSpace.Named.ADOBE_RGB));

        //from AdobeRGB to DisplayP3
        Color e=d.convert(ColorSpace.get(ColorSpace.Named.DISPLAY_P3));

        ColorSpace cs=ColorSpace.get(ColorSpace.Named.ADOBE_RGB);
        Log.e("is wide gamut ?",String.valueOf(cs.isWideGamut()));
        Log.e("get color model",String.valueOf(cs.getModel()));
        Log.e("component ",String.valueOf(cs.getComponentCount()));

        ColorSpace.Connector connector=ColorSpace.connect(ColorSpace.get(ColorSpace.Named.SRGB),ColorSpace.get(ColorSpace.Named.ADOBE_RGB));

        //changing the white point with chromatic adaption
        ColorSpace d50=ColorSpace.adapt(ColorSpace.get(ColorSpace.Named.SRGB),ColorSpace.ILLUMINANT_D50);





    }
}
