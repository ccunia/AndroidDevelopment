package com.example.ccunia.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CCunia on 6/26/2018.
 */

public class RecipeList implements Parcelable {

    private final String TAG = RecipeList.class.getSimpleName();
    int id;
    String name;
    ArrayList<IngredientList> ingredients;
    ArrayList<StepsRecipe> steps;
    int servings;
    String image;

    public RecipeList(){
    }


    protected RecipeList(Parcel in) {

        //ingredients = new ArrayList<>();

        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(IngredientList.CREATOR);
        this.steps = in.createTypedArrayList(StepsRecipe.CREATOR);
        this.servings = in.readInt();
        this.image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);

    }

    public static final Creator<RecipeList> CREATOR = new Creator<RecipeList>() {
        @Override
        public RecipeList createFromParcel(Parcel in) {
            return new RecipeList(in);
        }

        @Override
        public RecipeList[] newArray(int size) {
            return new RecipeList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IngredientList> getIngredients() {
        return ingredients;
    }

//    public void setIngredients(List<IngredientList> ingredients) {
//        this.ingredients = ingredients;
//    }

    public void setIngredients(ArrayList<IngredientList> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<StepsRecipe> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepsRecipe> stepsRecipe) {
        this.steps = stepsRecipe;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
