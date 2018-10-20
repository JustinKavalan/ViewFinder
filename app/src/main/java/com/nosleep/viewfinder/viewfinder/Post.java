package com.nosleep.viewfinder.viewfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class Post extends AppCompatActivity {

    Button btnClose;
    Button btnPost;
    ImageView ivPostPreview;
    EditText etCaption;
    RatingBar rbRating;
    double longitude;
    double latitude;
    double star = 5.0;
    String caption = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnClose = findViewById(R.id.btnClose);
        btnPost = findViewById(R.id.btnPost);
        ivPostPreview = findViewById(R.id.ivPostPreview);
        etCaption = findViewById(R.id.etCaption);
        rbRating = findViewById(R.id.rbRating);

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                star = rbRating.getRating();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caption = etCaption.getText().toString();
                //Where the post is sent to firebase
            }
        });
    }
}
