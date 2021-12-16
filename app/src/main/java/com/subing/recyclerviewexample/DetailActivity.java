package com.subing.recyclerviewexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.transition.Transition;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailActivity extends AppCompatActivity {

    // Extra name for the ID parameter: MainActivity에서 넘어온 Intent.Extra
    public static final String EXTRA_PARAM_ID = "detail:_id";

    // View name of the header image, title, content. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";
    public static final String VIEW_NAME_HEADER_CONTENT = "detail:header:content";

    private ImageView mHeaderImageView;
    private TextView mHeaderTitle;
    private TextView mHeaderContent;
    private TextView mBodyContent;

    Data mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve the correct Item instance, using the ID provided in the Intent
        mData = Data.getItem(getIntent().getStringExtra(EXTRA_PARAM_ID));

        mHeaderImageView = findViewById(R.id.imageview_header);
        mHeaderTitle = findViewById(R.id.textview_title);
        mHeaderContent = findViewById(R.id.textview_content);
        mBodyContent = findViewById(R.id.textview_body);

        loadItem();
        addLink();

        ViewCompat.setTransitionName(mHeaderImageView, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(mHeaderTitle, VIEW_NAME_HEADER_TITLE);
        ViewCompat.setTransitionName(mHeaderContent, VIEW_NAME_HEADER_CONTENT);

    }

    private void loadItem() {
        mHeaderTitle.setText(getString(R.string.image_header, mData.getTitle(), "subin"));
        mHeaderContent.setText(mData.getContent());
        mHeaderImageView.setImageResource(mData.getPhoto());
    }

    private void addLink(){
        Pattern pattern = Pattern.compile("여기");

        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return mData.getTitle();
            }
        };

        Linkify.addLinks(mBodyContent, pattern, "https://ko.wikipedia.org/wiki/", null, transformFilter);
    }

//    private void loadThumbnail() {
//        Picasso.get()
//                .load(mData.getPhoto())
//                .noFade()
//                .into(mHeaderImageView);
//    }
//
//
//    private void loadFullSizeImage() {
//        Picasso.get()
//                .load(mData.getPhoto())
//                .noFade()
//                .noPlaceholder()
//                .into(mHeaderImageView);
//    }
//
//    @RequiresApi(21)
//    private boolean addTransitionListener() {
//        final Transition transition = getWindow().getSharedElementEnterTransition();
//
//        if (transition != null) {
//            // There is an entering shared element transition so add a listener to it
//            transition.addListener(new Transition.TransitionListener() {
//                @Override
//                public void onTransitionEnd(Transition transition) {
//                    // As the transition has ended, we can now load the full-size image
//                    loadFullSizeImage();
//
//                    // Make sure we remove ourselves as a listener
//                    transition.removeListener(this);
//                }
//
//                @Override
//                public void onTransitionStart(Transition transition) {
//                    // No-op
//                }
//
//                @Override
//                public void onTransitionCancel(Transition transition) {
//                    // Make sure we remove ourselves as a listener
//                    transition.removeListener(this);
//                }
//
//                @Override
//                public void onTransitionPause(Transition transition) {
//                    // No-op
//                }
//
//                @Override
//                public void onTransitionResume(Transition transition) {
//                    // No-op
//                }
//            });
//            return true;
//        }
//
//        // If we reach here then we have not added a listener
//        return false;
//    }
}