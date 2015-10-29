package com.baidu.stickyheadergridview.sample;

import android.app.ListActivity;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.baidu.stickyheadergridview.ExpandableGridView;
import com.baidu.stickyheadergridview.R;
import com.baidu.stickyheadergridview.StickyGridHeadersGridView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        StickyGridHeadersGridView.OnHeaderClickListener, StickyGridHeadersGridView.OnHeaderLongClickListener {


    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager.setAdapter(new MyPagerAdapter());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onHeaderClick(AdapterView<?> parent, View view, long id) {

    }

    @Override
    public boolean onHeaderLongClick(AdapterView<?> parent, View view, long id) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class MyPagerAdapter extends PagerAdapter{
        CharSequence[] titles = new CharSequence[]{
                "缓存", "图片", "视频", "语音"
        };

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.header_gridview, container, false);
            GridView mGridView = (GridView) contentView.findViewById(R.id.asset_grid);
            mGridView.setOnItemClickListener(MainActivity.this);
            mGridView.setAdapter(new StickyGridHeadersSimpleArrayAdapter<String>(
                    getApplicationContext(), getResources().getStringArray(R.array.countries),
                    R.layout.header, R.layout.item));
            ((StickyGridHeadersGridView) mGridView).setOnHeaderClickListener(MainActivity.this);
            ((StickyGridHeadersGridView) mGridView).setOnHeaderLongClickListener(MainActivity.this);
            mGridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            container.addView(contentView);*/

            View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.expandablegridview, container, false);
            final ExpandableGridView gridView = (ExpandableGridView) contentView.findViewById(R.id.expandableGridView);
            final MyExpandableListAdapter expandableListAdapter = new MyExpandableListAdapter();
            gridView.setAdapter(expandableListAdapter);
            container.addView(contentView);
            for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
                gridView.expandGroup(i);
            }
            return contentView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = { "People Names", "Dog Names1", "Cat Names","People Names", "Dog Names", "Cat Names","People Names", "Dog Names", "Cat Names", "Fish Names" };
        private String[][] children = {
                { "Arnold", "Barry", "Chuck", "David" },
                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
                { "Fluffy", "Snuggles" },
                { "Arnold", "Barry", "Chuck", "David" },
                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
                { "Fluffy", "Snuggles" },
                { "Arnold", "Barry", "Chuck", "David" },
                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
                { "Fluffy", "Snuggles" },
                { "Goldy", "Bubbles" }
        };

        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericTextView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(MainActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 16, 0, 16);
            return textView;
        }

        public ImageView getGenericChildView(){
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (90* Resources.getSystem().getDisplayMetrics().density));
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(R.drawable.pic);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }



        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            Log.d("gonggaofeng","childPosition="+childPosition);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (90* Resources.getSystem().getDisplayMetrics().density));
            FrameLayout layout = new FrameLayout(MainActivity.this);
            layout.setLayoutParams(lp);

            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(R.drawable.pic);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layout.addView(imageView, lp);
            TextView textView = new TextView(MainActivity.this);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            textView.setText(String.valueOf(childPosition));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16*Resources.getSystem().getDisplayMetrics().density);
            layout.addView(textView);
            return layout;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            TextView textView = getGenericTextView();
            textView.setBackgroundColor(Color.GRAY);
            textView.setText(getGroup(groupPosition).toString()+groupPosition);
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }



    }
}
