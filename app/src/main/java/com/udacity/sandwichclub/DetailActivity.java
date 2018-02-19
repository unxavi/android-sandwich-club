package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnowAsTitleTV = findViewById(R.id.also_know_as_title_tv);
        TextView alsoKnowAsTV = findViewById(R.id.also_known_tv);
        TextView originTitleTV = findViewById(R.id.origin_title_tv);
        TextView originTV = findViewById(R.id.origin_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        TextView ingredientsTitleTV = findViewById(R.id.ingredients_title_tv);
        TextView descriptionTitleTV = findViewById(R.id.description_title_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnowAsTitleTV.setVisibility(View.GONE);
            alsoKnowAsTV.setVisibility(View.GONE);
        } else {
            alsoKnowAsTV.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            originTitleTV.setVisibility(View.GONE);
            originTV.setVisibility(View.GONE);
        } else {
            originTV.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTV.setVisibility(View.GONE);
            ingredientsTitleTV.setVisibility(View.GONE);
        } else {
            ingredientsTV.setText(TextUtils.join(", ", sandwich.getIngredients()));
        }

        if (TextUtils.isEmpty(sandwich.getDescription())) {
            descriptionTitleTV.setVisibility(View.GONE);
            descriptionTV.setVisibility(View.GONE);
        } else {
            descriptionTV.setText(sandwich.getDescription());
        }


    }
}
