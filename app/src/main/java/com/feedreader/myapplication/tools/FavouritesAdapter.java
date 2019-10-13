package com.feedreader.myapplication.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feedreader.myapplication.R;
import com.feedreader.myapplication.WebViewActivity;
import com.feedreader.myapplication.data.Content;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This class sets a style for Favourites shown by favourites_layout
 */

public class FavouritesAdapter extends BaseAdapter {


    private Context content;
    private ArrayList<Content> datas;
    private Bundle bundle;

    public FavouritesAdapter(Context context, ArrayList<Content> datas) {
        this.content = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
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
            viewHolder.contentView = (TextView) convertView.findViewById(R.id.content);
            viewHolder.menuView = (TextView) convertView.findViewById(R.id.menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.contentView.setText(datas.get(position).getContent());

        viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", ((TextView) v).getText());
                startActivity(FavouritesAdapter.this.content, intent, bundle);
            }
        });
        final Content content = datas.get(position);
        viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(content);
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