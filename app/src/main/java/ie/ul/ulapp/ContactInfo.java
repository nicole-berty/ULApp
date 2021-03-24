package ie.ul.ulapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Gets the contact information from the UL website using an AsyncTask. Formats and displays the information on screen.
 */
public class ContactInfo extends MenuActivity {

    final String url = "https://www.ul.ie/contact-information";
    Document doc = null;
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        textView = findViewById(R.id.downloadStatus);
        textView.setText("\nGetting info..");
        new DataGrabber().execute(); // execute the AsyncTask below
    }

    //New class for the AsyncTask, where the data will be fetched in the background
    private class DataGrabber extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
           //Attempt to connect to the website using JSoup
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //If the retrieved doc isn't null, display the content
            if (doc != null) {
                //Element contactInfo = doc.select("div.pagecontent").first();
                Element contactInfo = doc.select("h3").first();
                Element c2 = doc.select("p").first();
                String contact = "<br><b><h3>" + removeHTMLElements(contactInfo) + "</h3></b><br><h5>Address: " + removeHTMLElements(c2) + "<br>Email: </h5>" ;
                //textView.setText(contact);
                textView.setText(HtmlCompat.fromHtml(contact, HtmlCompat.FROM_HTML_MODE_LEGACY));
          //      textView.setText(removeHTMLElements(contactInfo));
            }else{
                textView.setText("Couldn't get page content");
            }
        }

        /**
         * Removes the HTML elements from the given element
         * @param element HTML element which needs to be converted to String
         * @return String containing the text from the HTML element provided
         */
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