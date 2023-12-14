package com.dailyrecipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyrecipe.dbhelper.RecipeDatabase;

import java.lang.ref.WeakReference;

public class recipieinfo extends AppCompatActivity {
    TextView leftText, textView111, textView334, textView522, centerText, textviewdown, recipeDescription;
    ImageView rightHalfCornerImage,leftImage23;
    Integer recipieid;
    RecipeDao recipeDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipieinfo);

        leftText = findViewById(R.id.leftText);
        textView111 = findViewById(R.id.textView111);
        textView334 = findViewById(R.id.textView334);
        textView522 = findViewById(R.id.textView522);
        centerText = findViewById(R.id.centerText);
        leftImage23=findViewById(R.id.leftImage23);
        rightHalfCornerImage = findViewById(R.id.rightHalfCornerImage);

        recipeDescription = findViewById(R.id.textviewdown);

        leftImage23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent163 = new Intent(recipieinfo.this, mains.class);
                startActivity(intent163);
            }
        });


        recipeDao = RecipeDatabase.getInstance(getApplicationContext()).recipeDao();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int recipeId = bundle.getInt("recipieid", -1);
            String recipeName = bundle.getString("recipie_name", "");
            String recipecalories = bundle.getString("recipie_calories", "");
            String recipetype = bundle.getString("recipie_type", "");
            String recipetime = bundle.getString("recipie_time", "");
            String recipeserving = bundle.getString("recipie_serving", "");
            byte[] recipeImage = bundle.getByteArray("recipie_image");

            if (recipeId != -1) {
                leftText.setText(recipeName);
                textView111.setText(recipecalories);
                textView334.setText(recipetime);
                textView522.setText(recipeserving);
                centerText.setText(recipetype);

                if (recipeImage != null && recipeImage.length > 0) {
                    rightHalfCornerImage.setImageBitmap(BitmapFactory.decodeByteArray(recipeImage, 0, recipeImage.length));
                }

                new LoadRecipeDescriptionTask(recipeId, recipeDescription, recipeDao).execute();
            }
        }
    }

    static class LoadRecipeDescriptionTask extends AsyncTask<Void, Void, String> {
        private final int recipeId;
        private final WeakReference<TextView> textViewWeakReference;
        private final RecipeDao recipeDao;

        LoadRecipeDescriptionTask(int recipeId, TextView textView, RecipeDao recipeDao) {
            this.recipeId = recipeId;
            this.textViewWeakReference = new WeakReference<>(textView);
            this.recipeDao = recipeDao;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Call the DAO method to get the description
                Recipe recipe = recipeDao.getRecipeById(recipeId);
                if (recipe != null) {
                    return recipe.getDescription();

                } else {
                    return null;
                }
            } catch (Exception e) {
                Log.e("LoadRecipeDescriptionTask", "Error fetching recipe description", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String recipeDescription) {
            super.onPostExecute(recipeDescription);

            TextView textView = textViewWeakReference.get();
            if (textView != null) {
                textView.setText(recipeDescription);
            }
        }
    }
}






















