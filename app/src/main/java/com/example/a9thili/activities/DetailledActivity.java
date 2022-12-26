package com.example.a9thili.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a9thili.R;
import com.example.a9thili.models.NewProductsModel;
import com.example.a9thili.models.PopularProductsModel;
import com.example.a9thili.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailledActivity extends AppCompatActivity {

    ImageView detailedImg,addItems,removeItems;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    int totalQuantity=1;
    int totalPrice=0;
    //new products
    NewProductsModel newProductsModel=null;
    //toolbar
    Toolbar toolbar;
    //popular products
    PopularProductsModel popularProductsModel=null;
    //show all
    ShowAllModel showAllModel=null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailled);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        //toolbar
        toolbar=findViewById(R.id.detailled_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final Object object=getIntent().getSerializableExtra("detailed");
        if(object instanceof NewProductsModel){
            newProductsModel=(NewProductsModel) object;

        }else if (object instanceof PopularProductsModel){
            popularProductsModel=(PopularProductsModel) object;
        }
        else if (object instanceof ShowAllModel){
            showAllModel=(ShowAllModel) object;
        }
        quantity=findViewById(R.id.quantity);
        detailedImg=findViewById(R.id.detailled_img);
        name=findViewById(R.id.detailled_name);
        rating=findViewById(R.id.rating);
        description=findViewById(R.id.detailled_desc);
        price=findViewById(R.id.detailled_price);

        addToCart=findViewById(R.id.add_to_cart);
        buyNow=findViewById(R.id.buy_now);

        addItems=findViewById(R.id.add_item);
        removeItems=findViewById(R.id.remove_item);
//new proucts
        if(newProductsModel !=null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            name.setText(newProductsModel.getName());
            //add total price
            totalPrice=newProductsModel.getPrice()*totalQuantity;
        }
///popular products
        if(popularProductsModel !=null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());

            totalPrice=popularProductsModel.getPrice()*totalQuantity;

        }
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());
            totalPrice=showAllModel.getPrice()*totalQuantity;
        }


        //buy now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DetailledActivity.this,AddressActivity.class);
                if(newProductsModel!=null){
                    intent.putExtra("item",newProductsModel);
                }
                if(popularProductsModel!=null){
                    intent.putExtra("item",popularProductsModel);
                }
                if(showAllModel!=null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);
            }
        });
        //add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsModel!=null){
                        totalPrice=newProductsModel.getPrice()*totalQuantity;
                    }
                    if (popularProductsModel!=null){
                        totalPrice=popularProductsModel.getPrice()*totalQuantity;
                    }
                    if (showAllModel!=null){
                        totalPrice=showAllModel.getPrice()*totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }

    private void addToCart() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        final HashMap<String ,Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("ProductPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",totalQuantity);
        cartMap.put("totalPrice",totalPrice);


        firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailledActivity.this,"Added to Cart ",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}