package th.in.armno.omfgandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by armno on 6/24/15 AD.
 */
public class JSONAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "https://covers.openlibrary.org/b/id/";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return mJsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again
        if (view == null) {
            // inlfate the custom row layout from you xml
            view = mInflater.inflate(R.layout.row_book, null);

            // create a new "holder" with subviews
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) view.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView) view.findViewById(R.id.text_title);
            holder.authorTextView = (TextView) view.findViewById(R.id.text_author);

            // hang onto this holder for future recycle
            view.setTag(holder);
        } else {
            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) view.getTag();
        }

        // get the current book's data in JSON form
        JSONObject jsonObject = (JSONObject) getItem(i);

        // see if there is a cover id in the object
        if (jsonObject.has("cover_i")) {
            // grab the cover id out of the object
            String imageID = jsonObject.optString("cover_i");

            // construct the image url (specific to api)
            String imageUrl = IMAGE_URL_BASE + imageID + "-S.jpg";

            // use Picasso to load the image
            // temporarily have a placeholder in case it's slow to load
            Picasso.with(mContext).load(imageUrl).placeholder(R.drawable.ic_books).into(holder.thumbnailImageView);
        } else {
            // if there is no cover id in th object, use a placeholder
            holder.thumbnailImageView.setImageResource(R.drawable.ic_books);
        }

        String bookTitle = "";
        String authorName = "";

        if (jsonObject.has("title")) {
            bookTitle = jsonObject.optString("title");
        }

        if (jsonObject.has("author_name")) {
            authorName = jsonObject.optJSONArray("author_name").optString(0);
        }

        // send these strings to the textview for display
        holder.titleTextView.setText(bookTitle);
        holder.authorTextView.setText(authorName);

        return view;
    }

    public void updateData(JSONArray jsonArray) {
        // update the adapter's dataset
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView authorTextView;
    }
}
