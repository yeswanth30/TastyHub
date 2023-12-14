package com.dailyrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.dailyrecipe.dbhelper.RecipeDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class insert extends AppCompatActivity {

    EditText first, first1, first2, first22,first33,first123;
    Button submit;
    private final int GALLERY_REQ_CODE = 1000;
    ImageView imageview1;
    String userid;
    Button SelectImage;
    private Uri selectedImage;
    private Bitmap imageTostore;
    private RecipeDao recipeDao;
int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);

        first = findViewById(R.id.first);
        first1 = findViewById(R.id.first1);
        first2 = findViewById(R.id.first2);
        first22 = findViewById(R.id.first22);
        first33 = findViewById(R.id.first33);
        imageview1 = findViewById(R.id.imageview123);
        SelectImage = findViewById(R.id.SelectImage);
        submit = findViewById(R.id.submit);
        first123=findViewById(R.id.first123);

        RecipeDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "recipe-db").build();
        recipeDao = db.recipeDao();

        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
        SharedPreferences sharedpreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
         userId = sharedpreferences.getInt("userid", -1);
        Log.d("InsertActivity", "UserID: " + userId);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = first1.getText().toString();
                String calories = first22.getText().toString();
                String date = first.getText().toString();
                String type = first2.getText().toString();
                String serving = first33.getText().toString();
                String description = first123.getText().toString();





                long times = System.currentTimeMillis();
                SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                String formats = dates.format(new Date(times));

                // Convert Bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageTostore.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // Create Recipe object
                Recipe recipe = new Recipe();
                recipe.setName(name);
                recipe.setType(type);
                recipe.setTime(date);
                recipe.setImage(byteArray);
                recipe.setCalories(calories);
                recipe.setServing(serving);
                recipe.setDescription(description);

                recipe.setUserId(Integer.parseInt(String.valueOf(userId)));
                recipe.setTimestamp(formats);
                Log.d("InsertActivity", "Recipe Details: " + recipe.toString());
                // Insert recipe into Room database
                insertRecipe(recipe);
            }
        });
    }

    private void insertRecipe(Recipe recipe) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RecipeDao recipeDao = RecipeDatabase.getInstance(insert.this).recipeDao();

                long result = recipeDao.insertRecipe(recipe);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != -1) {
                            showToast("Data added to the database");
                        } else {
                            showToast("Error inserting data");
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE) {
            selectedImage = data.getData();
            imageview1.setImageURI(selectedImage);
            try {
                imageTostore = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
