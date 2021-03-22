package ie.ul.ulapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
                Element contactInfo = doc.select("div.pagecontent").first();

                textView.setText(removeHTMLElements(contactInfo));
            }else{
                textView.setText("FAILURE");
            }
        }

        private String removeHTMLElements(Element element) {
            String text = element.toString();
            boolean remove = false;
            int numOfOpenElements = 0;
            for (int i = 0; i < text.length(); i++) {
                char temp = text.charAt(i);
                if (text.charAt(i) == '<') {
                    remove = true;
                    numOfOpenElements++;
                }
                if (remove) {
                    if (i+1 < text.length()) {
                        text = text.substring(0, i) + text.substring(i + 1);
                    } else {
                        text = text.substring(0,i);
                    }
                    i--;
                }
                if ('>' == temp) {
                    if (numOfOpenElements == 1) {
                        remove = false;
                    }
                    numOfOpenElements--;
                }
            }
            text = text.replaceAll("\n ","\n");
            text = text.replaceAll("&nbsp;","");
            text = text.replaceAll("&amp;","");


            return text;
        }
    }
}