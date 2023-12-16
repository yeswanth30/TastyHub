package com.dailyrecipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailyrecipe.dbhelper.RecipeDatabase;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private Context context;

    public RecipeAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodwraper, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImageView;
        TextView nameTextView;
        TextView typeTextView;
        TextView caloriesTextView;
        TextView timeTextView;
        TextView servingTextView;
        RelativeLayout food;
        ImageView likeimage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.imageViewee);
            nameTextView = itemView.findViewById(R.id.textView4552);
            typeTextView = itemView.findViewById(R.id.textView4541);
            caloriesTextView = itemView.findViewById(R.id.textView354454);
            timeTextView = itemView.findViewById(R.id.textView567);
            servingTextView = itemView.findViewById(R.id.textView688);
            food = itemView.findViewById(R.id.food);
            likeimage = itemView.findViewById(R.id.likeimage);

            setupClickListeners();
        }

        private void setupClickListeners() {
            likeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedRecipeId = recipes.get(getAdapterPosition()).getRecipeId();
                    Log.d("RecipeAdapter", "Like image clicked for recipe ID: " + clickedRecipeId);

                    toggleFavoriteStatus(clickedRecipeId);
                }
            });

            food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedRecipeId = recipes.get(getAdapterPosition()).getRecipeId();
                    String clickedRecipeName = recipes.get(getAdapterPosition()).getName();
                    String clickedRecipecalories = recipes.get(getAdapterPosition()).getCalories();
                    String clickedRecipetype = recipes.get(getAdapterPosition()).getType();
                    String clickedRecipetime = recipes.get(getAdapterPosition()).getTime();
                    String clickedRecipeserving = recipes.get(getAdapterPosition()).getServing();
                    byte[] clickedRecipeImage = recipes.get(getAdapterPosition()).getImage();

                    Log.d("RecipeViewHolder", "Clicked on recipe with ID: " + clickedRecipeId);

                    Intent intent = new Intent(v.getContext(), recipieinfo.class);
                    intent.putExtra("recipieid", clickedRecipeId);
                    intent.putExtra("recipie_name", clickedRecipeName);
                    intent.putExtra("recipie_calories", clickedRecipecalories);
                    intent.putExtra("recipie_type", clickedRecipetype);
                    intent.putExtra("recipie_time", clickedRecipetime);
                    intent.putExtra("recipie_serving", clickedRecipeserving);
                    intent.putExtra("recipie_image", clickedRecipeImage);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Recipe recipe) {
            // Bind data to views
            nameTextView.setText(recipe.getName());
            typeTextView.setText(recipe.getType());
            caloriesTextView.setText(recipe.getCalories());
            timeTextView.setText(recipe.getTime());
            servingTextView.setText(recipe.getServing());

            byte[] imageBytes = recipe.getImage();
            if (imageBytes != null && imageBytes.length > 0) {
                recipeImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));

                int likedColor = Color.parseColor("#FFA500");
                int defaultColor = Color.parseColor("#B2B7C6");
                likeimage.setColorFilter(recipe.isLiked() ? likedColor : defaultColor, PorterDuff.Mode.SRC_IN);
            }
        }

        private void toggleFavoriteStatus(int recipeId) {
            Log.d("RecipeAdapter", "toggleFavoriteStatus for recipe ID: " + recipeId);
            new ToggleFavoriteAsyncTask().execute(recipeId);
        }

        private int getCurrentUserId() {
            SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            return sharedPreferences.getInt("userid", -1);
        }

        private class ToggleFavoriteAsyncTask extends AsyncTask<Integer, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Integer... params) {
                int recipeId = params[0];

                RecipeDatabase db = RecipeDatabase.getInstance(context);
                FavoriteDao favoriteDao = db.favoriteDao();

                int userId = getCurrentUserId();

                Favorite existingFavorite = favoriteDao.getFavorite(userId, recipeId);

                if (existingFavorite == null) {
                    Favorite newFavorite = new Favorite();
                    newFavorite.setUserId(userId);
                    newFavorite.setRecipeId(recipeId);
                    long id = favoriteDao.insertFavorite(newFavorite);
                    return id > 0;
                } else {
                    favoriteDao.deleteFavorite(existingFavorite);
                    return true;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);

                if (success) {
                    Recipe currentRecipe = recipes.get(getAdapterPosition());
                    currentRecipe.setLiked(!currentRecipe.isLiked());

                    int likedColor = Color.parseColor("#FFA500");
                    int defaultColor = Color.parseColor("#B2B7C6");
                    likeimage.setColorFilter(currentRecipe.isLiked() ? likedColor : defaultColor);

                    if (currentRecipe.isLiked()) {
                        Toast.makeText(context, "Recipe Liked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Recipe Unliked", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Toggle Favorite Failed", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }
}








