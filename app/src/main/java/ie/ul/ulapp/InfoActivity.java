package ie.ul.ulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class InfoActivity extends MenuActivity {

    private ImageView contactInfoImage, GuidesImage, ParkingImage, ULImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ULImage = findViewById(R.id.InfoPageImage);
        contactInfoImage = findViewById(R.id.contactInfoImage);
        GuidesImage = findViewById(R.id.GuidesInfoImage);
        ParkingImage = findViewById(R.id.ParkingInfoImage);

        ULImage.setImageResource(R.drawable.ul_logo);
        contactInfoImage.setImageResource(R.drawable.carpool);
        contactInfoImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intent = new Intent(InfoActivity.this, DownloadData.class);
            startActivity(intent);
            }
        });
        GuidesImage.setImageResource(R.drawable.carpool);
        ParkingImage.setImageResource(R.drawable.carpool);
    }

}