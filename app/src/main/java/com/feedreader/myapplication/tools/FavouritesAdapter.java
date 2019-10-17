package com.feedreader.myapplication.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.feedreader.myapplication.AddSitesShowActivity;
import com.feedreader.myapplication.R;
import com.feedreader.myapplication.WebViewActivity;
import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Author: Zixin Ye
 * This class sets a style for Favourites shown by favourites_layout.It is the bridge between the data and the UI.
 * It connects the background data to the front UI and is a carrier to display the data.
 */

public class FavouritesAdapter extends BaseAdapter {


    private Context content;
    private ArrayList<RSSElement> RSSElementList;


    public FavouritesAdapter(Context context, ArrayList<RSSElement> rssElementList) {
        this.content = context;
        this.RSSElementList = rssElementList;
    }


    @Override
    public int getCount() {
        return RSSElementList.size();
    }


    @Override
    public Object getItem(int position) {
        return RSSElementList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.content).inflate(R.layout.slide_list, null);
            viewHolder = new ViewHolder();
            viewHolder.contentView = (TextView) convertView.findViewById(R.id.news);
            viewHolder.menuView = (TextView) convertView.findViewById(R.id.slide_list_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.contentView.setText(RSSElementList.get(position).getTitle() + "\r\n" + RSSElementList.get(position).getPubDate());//Use the collected news title and date as the news to display on each line
        viewHolder.contentView.setTag(RSSElementList.get(position).getLink());// Set Url of news to be redirected
        viewHolder.contentView.setGravity(Gravity.LEFT);//Text to the left（horizontally）
        viewHolder.contentView.setGravity(Gravity.CENTER_VERTICAL); //Text to the Center Vertical
        viewHolder.contentView.setBackgroundColor(Color.WHITE);
        viewHolder.contentView.setTextColor(Color.BLACK);
        //viewHolder.contentView.setTypeface(viewHolder.contentView.getTypeface(), Typeface.BOLD);
        viewHolder.contentView.setPadding(50,10,50,10);


        //Click to jump to the specified news
        viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", v.getTag().toString());
                startActivity(FavouritesAdapter.this.content, intent, null);
            }
        });


        //Delete
        final RSSElement rssElement = RSSElementList.get(position);
        viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RSSElementList.remove(rssElement);
                notifyDataSetChanged();

                Toast.makeText(
                        getApplicationContext(),
                        "Successfully removed "+rssElement.title,
                        Toast.LENGTH_SHORT
                ).show();

                saveNewsList(RSSElementList, new File (Environment.getExternalStorageDirectory().getPath() + "/newsList.xml"));
            }
        });


        SlideLayout slideLayout = (SlideLayout) convertView;
        slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());

        return convertView;
    }

    public SlideLayout slideLayout = null;

    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener {

        @Override
        public void onOpen(SlideLayout layout) {

            slideLayout = layout;
        }

        @Override
        public void onMove(SlideLayout layout) {
            if (slideLayout != null && slideLayout != layout) {
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onClose(SlideLayout layout) {
            if (slideLayout == layout) {
                slideLayout = null;
            }
        }
    }


    class ViewHolder {
        public TextView contentView;
        public TextView menuView;
    }


    public void saveNewsList(ArrayList<RSSElement> RSSElementList, File file) {
        ArrayList<RSSElement> newsList = RSSElementList;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();

            Element newsListElement = dom.createElement("newsList");

            for (int i=0;i<newsList.size();i++) {
                RSSElement news = newsList.get(i);

                Element newsElem = dom.createElement("news");

                newsElem.setAttribute("title", news.getTitle());
                newsElem.setAttribute("link", news.getLink());
                newsElem.setAttribute("pubdate", news.getPubDate());

                newsListElement.appendChild(newsElem);
            }

            Transformer t = TransformerFactory.newInstance().newTransformer();
            DOMSource ds = new DOMSource(newsListElement);
            StreamResult sr = new StreamResult(file);

            t.transform(ds, sr);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
