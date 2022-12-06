package com.example.a9thili.fragments;

import android.icu.number.Scale;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.a9thili.R;
import com.example.a9thili.adapters.CategoryAdapter;
import com.example.a9thili.models.CategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    //category recycler view
    RecyclerView catRecyclerview;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //firestore
    FirebaseFirestore db ;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //creation de view
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerview=root.findViewById(R.id.rec_category);
        db= FirebaseFirestore.getInstance();

        //image slider
        ImageSlider imageSlider=root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount on shoes items ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount on perfumes  ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"70% off  ", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        //category
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        //Get data
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel=document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                        }
                    }
                });



        return root;
    }
}