package th.in.armno.omfgandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
//import android.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider;

import com.squareup.picasso.Picasso;

/**
 * Created by armno on 6/25/15 AD.
 */
public class DetailActivity extends ActionBarActivity {

    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    String mImageURL;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // tell the activity which xml layout is right
        setContentView(R.layout.activity_detail);

        // Enable the "up" button for more navigation options
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // access the image view from xml
        ImageView imageView = (ImageView) findViewById(R.id.img_cover);

        // 13. unpack the coverID from its trip inside your intent
        String coverID = this.getIntent().getExtras().getString("coverID");

        // see if there is a valid coverid
        if (coverID.length() > 0) {
            // use the id to construct an image url
            mImageURL = IMAGE_URL_BASE + coverID + "-L.jpg";

            // use Picasso to load the image
            Picasso.with(this).load(mImageURL).placeholder(R.drawable.img_books_loading).into(imageView);
        }
    }

    private void setShareIntent() {
        // create an intent with the contents of the textview
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageURL);

        // make sure the provider knows it should work with that intent
        mShareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu
        // this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // access the share item defined menu xml
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        // Access the object responsible for putting together the shareing submenu
        if (shareItem != null) {
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        }

        setShareIntent();


        return true;
    }
}
