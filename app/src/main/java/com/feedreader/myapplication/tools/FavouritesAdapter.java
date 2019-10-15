package com.feedreader.myapplication.tools;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feedreader.myapplication.R;
import com.feedreader.myapplication.WebViewActivity;
import com.feedreader.myapplication.data.RSSElement;

import java.util.ArrayList;

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

}
