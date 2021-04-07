package myUL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Gets the contact information from the UL website using an AsyncTask to run it on a background thread and JSoup to scrape the data. Formats and displays the information on screen in a TextView within a ScrollView.
 */
public class ContactInfo extends ActionBar {

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

    /**
     * New class for the AsyncTask, where the data will be fetched in the background when the page is opened
     */
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

                //Get HTML elements from the site page
                Element main = doc.select("div.pagecontent").first();
                Element header = doc.select("h2.db-header-section-title").first();
                Element undergrad = doc.selectFirst("div#accordion_20907_panel_3");
                Element postgrad = doc.selectFirst("div#accordion_20907_panel_4");
                Element international = doc.selectFirst("div#accordion_20907_panel_5");
                Element comms_officer = doc.selectFirst("div#accordion_20909_panel_7");
                Element arts_faculty = doc.selectFirst("div#accordion_20918_panel_9");
                Element education_faculty = doc.selectFirst("div#accordion_20918_panel_10");
                Element scieng_faculty = doc.selectFirst("div#accordion_20918_panel_11");
                Element kemmy = doc.selectFirst("div#accordion_20918_panel_12");

                //Format string for output
                String contact = main.toString() + header.toString() + "<h4>Undergraduate Admissions</h4>" + undergrad.toString() + "<h4>Postgraduate &amp; Flexible Learning Admissions</h4>"
                        + postgrad.toString() + "<h4>International Student Admissions</h4>" + international.toString() + "<h2>University Press Office</h2>"
                        + "<h4>Communications Officer (Press Queries ONLY) </h4>" + comms_officer.toString() + "<h2>Faculty Directories</h2>"
                        + "<h4>Faculty of Arts, Humanities &amp; Social Sciences</h4>" + arts_faculty.toString() + "<h4>Faculty of Education &amp; Health Sciences</h4>"
                        + education_faculty.toString() + "<h4>Faculty of Science of Engineering</h4>" + scieng_faculty.toString()
                        + "<h4>Kemmy Business School</h4>" + kemmy.toString();

                //Set the text of the TextView, HTMLCompat method used to ensure HTML format in output is right
                textView.setText(HtmlCompat.fromHtml(contact, HtmlCompat.FROM_HTML_MODE_LEGACY));
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }else{
                //Couldn't get the content of the web page, set error message in TextView
                textView.setText("Couldn't get page content");
            }
        }
    }
}