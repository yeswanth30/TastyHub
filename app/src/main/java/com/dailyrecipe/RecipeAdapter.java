package com.dailyrecipe;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
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

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.imageViewee);
            nameTextView = itemView.findViewById(R.id.textView4552);
            typeTextView = itemView.findViewById(R.id.textView4541);
            caloriesTextView = itemView.findViewById(R.id.textView354454);
            timeTextView = itemView.findViewById(R.id.textView567);
            servingTextView = itemView.findViewById(R.id.textView688);
            food=itemView.findViewById(R.id.food);


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
            timeTextView.setText( recipe.getTime());
            servingTextView.setText( recipe.getServing());

            byte[] imageBytes = recipe.getImage();
            if (imageBytes != null && imageBytes.length > 0) {
                recipeImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
            }
        }
    }
}
