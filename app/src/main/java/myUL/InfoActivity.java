package myUL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import myUL.guides.GuidesActivity;

public class InfoActivity extends ActionBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView ULImage = findViewById(R.id.InfoPageImage);
        ImageView contactInfoImage = findViewById(R.id.contactInfoImage);
        ImageView guidesImage = findViewById(R.id.GuidesInfoImage);
        ImageView parkingImage = findViewById(R.id.ParkingInfoImage);

        ULImage.setImageResource(R.drawable.ul_logo);
        contactInfoImage.setImageResource(R.drawable.contact);
        contactInfoImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intent = new Intent(InfoActivity.this, ContactInfo.class);
            startActivity(intent);
            }
        });
        guidesImage.setImageResource(R.drawable.guides);
        guidesImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, GuidesActivity.class);
                startActivity(intent);
            }
        });
        parkingImage.setImageResource(R.drawable.parking);
        parkingImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, ParkingInfo.class);
                startActivity(intent);
            }
        });
    }

}