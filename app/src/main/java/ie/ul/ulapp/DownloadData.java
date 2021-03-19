package ie.ul.ulapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DownloadData extends MenuActivity {

    String url = "https://www.ul.ie/contact-information";
    Document doc = null;
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data);

        textView = findViewById(R.id.downloadStatus);
        Button button = findViewById(R.id.getData);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textView.setText("WORKING");
                new DataGrabber().execute(); // execute the asynctask below
            }
        });

    }
    //New class for the Asynctask, where the data will be fetched in the background
    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //This is where we update the UI with the acquired data
            if (doc != null){
                //doc.title(), doc.body()

                Elements links = doc.select("a");
                Elements sections = doc.select("section");
                Elements logo = doc.select(".spring-logo--container");
                Elements pagination = doc.select("#pagination_control");
                Elements divsDescendant = doc.select("header div");
                Elements divsDirect = doc.select("header > div");

                textView.setText(sections.toString());
            }else{
                textView.setText("FAILURE");
            }
        }
    }
}