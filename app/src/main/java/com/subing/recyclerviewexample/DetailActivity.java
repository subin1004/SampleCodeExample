package com.subing.recyclerviewexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.transition.Transition;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.subing.recyclerviewexample.databinding.ActivityDetailBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailActivity extends AppCompatActivity {

    // dataBinding
    private ActivityDetailBinding binding;

    // Extra name for the ID parameter: MainActivity에서 넘어온 Intent.Extra
    public static final String EXTRA_PARAM_ID = "detail:_id";

    // View name of the header image, title, content. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";
    public static final String VIEW_NAME_HEADER_CONTENT = "detail:header:content";

    Data mData;

    // image DataBinding
    @BindingAdapter({"bind:photo"})
    public static void imgload(ImageView imageView, int resid) {
        imageView.setImageResource(resid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // Retrieve the correct Item instance, using the ID provided in the Intent
        mData = Data.getItem(getIntent().getStringExtra(EXTRA_PARAM_ID));
        binding.setSelected(mData);

        addLink();

        ViewCompat.setTransitionName(binding.imageviewHeader, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(binding.textviewTitle, VIEW_NAME_HEADER_TITLE);
        ViewCompat.setTransitionName(binding.textviewContent, VIEW_NAME_HEADER_CONTENT);

    }

    private void addLink(){
        Pattern pattern = Pattern.compile("여기");

        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return mData.getTitle();
            }
        };

        Linkify.addLinks(binding.textviewBody, pattern, "https://ko.wikipedia.org/wiki/", null, transformFilter);
    }

}