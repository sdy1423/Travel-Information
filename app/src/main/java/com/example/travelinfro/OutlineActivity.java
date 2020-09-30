package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class OutlineActivity extends AppCompatActivity {

    String contentid;
    int contentTypeId;
    TextView mTvTitle,mTvOverview;
    ImageView mIvPic;
    ArrayList<OutlineDataItem> outlineDataItems = null;
    OutlineDataItem Item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outline);

        mTvTitle = findViewById(R.id.outline_tv_title);
        mTvOverview = findViewById(R.id.outline_tv_overview);
        mIvPic = findViewById(R.id.outline_iv_pic);

        Intent intent = getIntent();
        contentid =  intent.getStringExtra("contentid");
        contentTypeId = intent.getIntExtra("contenttypeid",0);
        Log.e("contentid",""+contentid);
        Log.e("contenttypeid",""+contentTypeId);

        OutlineAsyncTask outlineAsyncTask = new OutlineAsyncTask();
        outlineAsyncTask.execute();
    }
    public class OutlineAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String key="gG6IBYwr8ShiV%2FSaKXdiCnNi3eRGRu7t5%2Fq%2FfwniInp4vwyaNeJJF4Jrg%2BRpaHMbiF4ir4%2BanO7frXH4%2BAy7kw%3D%3D";
            String id = String.valueOf(contentTypeId);
            String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+key+"&contentTypeId="+id+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            try{
                boolean b_firstimage=false;
                boolean b_overview=false;
                boolean b_title=false;
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is,"UTF-8"));
                String tag;
                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            outlineDataItems = new ArrayList<OutlineDataItem>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item") && Item!=null){
                                outlineDataItems.add(Item);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("item")){
                                Item = new OutlineDataItem();
                            }
                            if(parser.getName().equals("title")){
                                b_title=true;
                            }
                            if(parser.getName().equals("firstimage")){
                                b_firstimage=true;
                            }
                            if(parser.getName().equals("overview")){
                                b_overview=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(b_title){
                                Item.setTitle(parser.getText());
                                b_title=false;
                            }else if(b_firstimage){
                                Item.setFirstimage(parser.getText());
                                b_firstimage=false;
                            }
                            else if(b_overview){
                                Item.setOverview(parser.getText());
                                b_overview=false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("onPost","items"+outlineDataItems.isEmpty());

            mTvTitle.setText(outlineDataItems.get(0).getTitle());
            mTvOverview.setText(outlineDataItems.get(0).getOverview());
            String imageURL = outlineDataItems.get(0).getFirstimage();
            Glide.with(getApplicationContext()).load(imageURL).into(mIvPic);

        }
    }
}
