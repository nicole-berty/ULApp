package ulapp.myUL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBar
        implements OnMapReadyCallback ,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener
{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMinZoomPreference(15);
        mMap.setMaxZoomPreference(19);
        enableMyLocation();
        //mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        setupMap();
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            //PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
            //      Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }


    private void setupMap() {
        LatLng SportsArena = new LatLng(52.673462,-8.565409);
        LatLng FoundationBuilding = new LatLng(52.674402,-8.573531);
        LatLng UlLibrary = new LatLng(52.673143,-8.573349);
        LatLng CSIS = new LatLng(52.673897,-8.575682);
        LatLng MainBuilding = new LatLng(52.674005,-8.571857);

        LatLng MusicBuilding = new LatLng(52.678166,-8.569486);
        LatLng HealthSciences = new LatLng(52.677515,-8.569014);
        LatLng GEMS = new LatLng(52.678217,-8.568161);
        LatLng Stables = new LatLng(52.673013,-8.570822);
        LatLng AnalogDevices = new LatLng(52.673097,-8.569137);

        LatLng Kemmy = new LatLng(52.672453,-8.577361);
        LatLng Schumann = new LatLng(52.673019,-8.577887);
        LatLng Engineering = new LatLng(52.675056,-8.573059);
        LatLng Languages = new LatLng(52.675446,-8.573509);
        LatLng ULBoathouse = new LatLng(52.675758,-8.583509);

        LatLng Schrodinger = new LatLng(52.673925,-8.567522);
        LatLng Tierney = new LatLng(52.674413,-8.577212);
        LatLng Stokes = new LatLng(52.674907,-8.572708);
        LatLng PlasseyHouse = new LatLng(52.674315,-8.570765);
        LatLng PESS = new LatLng(52.674569,-8.568084);

        LatLng MSSI = new LatLng(52.673678,-8.568352);
        LatLng ParamedicBuilding = new LatLng(52.678510,-8.566729);
        LatLng Pavilion = new LatLng(52.679044,-8.569866);
        LatLng NorthCampusPitches = new LatLng(52.680072,-8.571625);
        LatLng MaguirePitches = new LatLng(52.672589,-8.558319);

        LatLng SportsBar = new LatLng(52.673124,-8.564272);
        LatLng Scholars = new LatLng(52.673006,-8.569744);
        LatLng InternationalBusinessCentre = new LatLng(52.673722,-8.577876);
        LatLng StudentsUnion = new LatLng(52.673202,-8.570141);
        LatLng Paddocks = new LatLng(52.672834,-8.571197);

        LatLng FreeCarPark1 = new LatLng(52.674883,-8.579421);
        LatLng FreeCarPark2 = new LatLng(52.671465,-8.565538);
        LatLng FreeCarPark3 = new LatLng(52.679805,-8.569915);
        LatLng FreeCarPark4 = new LatLng(52.673157,-8.578498);
        LatLng PaidCarPark1 = new LatLng(52.674705,-8.575602);

        LatLng PaidCarPark2 = new LatLng(52.672467,-8.564765);
        LatLng PaidCarPark3 = new LatLng(52.672480,-8.569272);
        LatLng StaffCarPark1 = new LatLng(52.673820,-8.566568);
        LatLng StaffCarPark2 = new LatLng(52.675121,-8.574400);
        LatLng StaffCarPark3 = new LatLng(52.672181,-8.576803);
        //mMap.addCircle(new CircleOptions().center(SportsArena).radius(10));
        mMap.addMarker(new MarkerOptions().position(SportsArena).title("UL SportArena").icon(BitmapDescriptorFactory.fromResource(R.drawable.swimming)));
        mMap.addMarker(new MarkerOptions().position(FoundationBuilding).title("Foundation Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.theater)));
        mMap.addMarker(new MarkerOptions().position(UlLibrary).title("UL Library").icon(BitmapDescriptorFactory.fromResource(R.drawable.books)));
        mMap.addMarker(new MarkerOptions().position(CSIS).title("CSIS").icon(BitmapDescriptorFactory.fromResource(R.drawable.csis)));
        mMap.addMarker(new MarkerOptions().position(MainBuilding).title("Main Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));

        mMap.addMarker(new MarkerOptions().position(MusicBuilding).title("World Academy of Music").icon(BitmapDescriptorFactory.fromResource(R.drawable.musicnote)));
        mMap.addMarker(new MarkerOptions().position(HealthSciences).title("Health Sciences").icon(BitmapDescriptorFactory.fromResource(R.drawable.dna)));
        mMap.addMarker(new MarkerOptions().position(GEMS).title("GEMS").icon(BitmapDescriptorFactory.fromResource(R.drawable.medical)));
        mMap.addMarker(new MarkerOptions().position(Stables).title("Stables").icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
        mMap.addMarker(new MarkerOptions().position(AnalogDevices).title("Analog Devices").icon(BitmapDescriptorFactory.fromResource(R.drawable.circuitboard)));

        mMap.addMarker(new MarkerOptions().position(Kemmy).title("Kemmy School of Business").icon(BitmapDescriptorFactory.fromResource(R.drawable.businessman)));
        mMap.addMarker(new MarkerOptions().position(Schumann).title("Schumann").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(Engineering).title("Engineering Research Building ").icon(BitmapDescriptorFactory.fromResource(R.drawable.engineering)));
        mMap.addMarker(new MarkerOptions().position(Languages).title("Languages Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(ULBoathouse).title("UL Boathouse").icon(BitmapDescriptorFactory.fromResource(R.drawable.rowboat)));

        mMap.addMarker(new MarkerOptions().position(Schrodinger).title("Schrodinger Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.cat)));
        mMap.addMarker(new MarkerOptions().position(Tierney).title("Tierney Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(Stokes).title("Stokes Research Institute ").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(PlasseyHouse).title("Plassey House").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(PESS).title("PESS").icon(BitmapDescriptorFactory.fromResource(R.drawable.exercise)));

        mMap.addMarker(new MarkerOptions().position(MSSI).title("MSSI").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(ParamedicBuilding).title("Schumann").icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(Pavilion).title("The Pavilion").icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
        mMap.addMarker(new MarkerOptions().position(NorthCampusPitches).title("North Campus pitches").icon(BitmapDescriptorFactory.fromResource(R.drawable.football)));
        mMap.addMarker(new MarkerOptions().position(MaguirePitches).title("Maguire Pitches").icon(BitmapDescriptorFactory.fromResource(R.drawable.football)));

        mMap.addMarker(new MarkerOptions().position(SportsBar).title("Sports Bar").icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
        mMap.addMarker(new MarkerOptions().position(Scholars).title("Scholars Club").icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
        mMap.addMarker(new MarkerOptions().position(InternationalBusinessCentre).title("International Business Centre").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(StudentsUnion).title("Students Centre").icon(BitmapDescriptorFactory.fromResource(R.drawable.building)));
        mMap.addMarker(new MarkerOptions().position(Paddocks).title("Paddocks").icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));

        mMap.addMarker(new MarkerOptions().position(FreeCarPark1).title("Free Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.greencar)));
        mMap.addMarker(new MarkerOptions().position(FreeCarPark2).title("Free Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.greencar)));
        mMap.addMarker(new MarkerOptions().position(FreeCarPark3).title("Free Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.greencar)));
        mMap.addMarker(new MarkerOptions().position(FreeCarPark4).title("Free Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.greencar)));
        mMap.addMarker(new MarkerOptions().position(PaidCarPark1).title("Paid Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.orangecar)));

        mMap.addMarker(new MarkerOptions().position(PaidCarPark2).title("Paid Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.orangecar)));
        mMap.addMarker(new MarkerOptions().position(PaidCarPark3).title("Paid Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.orangecar)));
        mMap.addMarker(new MarkerOptions().position(StaffCarPark1).title("Staff Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar)));
        mMap.addMarker(new MarkerOptions().position(StaffCarPark2).title("Staff Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar)));
        mMap.addMarker(new MarkerOptions().position(StaffCarPark3).title("Staff Car Park").icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MainBuilding));
    }
}