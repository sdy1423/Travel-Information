package com.example.travelinfro;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class TravelActivity extends AppCompatActivity {

    Spinner mSpTravelState,mSpTravelCity,mSpTravelType;
    ArrayAdapter<CharSequence> mAdapterState,mAdapterType;
    ArrayAdapter mAdapterCity;
    ArrayList[] CityList = new ArrayList[17];
    Button mBtnSearch;

    //contentTypeId : Type 스피너에 의해 선택
    //areaCode : State 스피너에 의해 선택
    //sigunguCode : City 스피너에 의해 선택
    int contentTypeId=-1, areaCode=-1, sigunguCode=-1;

    ArrayList<TourDataItem> tourDataItems = null;
    TourDataItem Item = null;

    RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        InitializeCity();
        mRecycler = findViewById(R.id.travel_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));


        //지역선택,시군구선택,여행타입선택
        mSpTravelState = findViewById(R.id.travel_sp_state);
        mSpTravelCity = findViewById(R.id.travel_sp_city);
        mSpTravelType = findViewById(R.id.travel_sp_type);

        //시군구 스피너 세팅(디폴트로 서울)
        mAdapterCity = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,CityList[0]);
        mSpTravelCity.setAdapter(mAdapterCity);
        mSpTravelCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sigunguCode = position+1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //지역선택 스피너 세팅
        mAdapterState = ArrayAdapter.createFromResource(this,R.array.state,android.R.layout.simple_spinner_item);
        mAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpTravelState.setAdapter(mAdapterState);
        mSpTravelState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaCode = position+1;
                //지역선택에 맞춰서 시군구 스피너 item 변경
                mAdapterCity = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,CityList[position]);
                mSpTravelCity.setAdapter(mAdapterCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //타입선택 스피너 세팅
        mAdapterType = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        mAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpTravelType.setAdapter(mAdapterType);
        mSpTravelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        contentTypeId = 12;
                        break;
                    case 1:
                        contentTypeId = 14;
                        break;
                    case 2:
                        contentTypeId = 15;
                        break;
                    case 3:
                        contentTypeId = 25;
                        break;
                    case 4:
                        contentTypeId = 28;
                        break;
                    case 5:
                        contentTypeId = 32;
                        break;
                    case 6:
                        contentTypeId = 38;
                        break;
                    case 7:
                        contentTypeId = 39;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //검색 버튼
        mBtnSearch = findViewById(R.id.travel_btn_search);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO 검색 API받아와서 리사이클러뷰에 뿌려준다.

                Log.e("contentTypeId","msg: "+contentTypeId);
                Log.e("areaCode","msg: "+areaCode);
                Log.e("sigunguCode","msg: "+sigunguCode);
                if(contentTypeId!=-1 && areaCode!=-1 && sigunguCode!=-1){
                    Log.e("Let's try getting API","msg");
                    try{
                        Log.e("hell","helll");
                        TravelAsyncTask travelAsyncTask= new TravelAsyncTask();
                        travelAsyncTask.execute();
                    } catch (Exception e) {
                        Log.e("TryFail","msg: "+e.getMessage());
                    }
                }

                Log.e("Fail try getting API","msg");

            }
        });



    }

    private void InitializeCity() {
        CityList[0] = new ArrayList<String>(Arrays.asList("강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구",
                "중구","중랑구"));
        CityList[1] = new ArrayList<String>(Arrays.asList("강화구","계양구","미추홀구","남동구"));

    }

    public class TravelAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            Log.e("in  doInBackground","");

            String key = "gG6IBYwr8ShiV%2FSaKXdiCnNi3eRGRu7t5%2Fq%2FfwniInp4vwyaNeJJF4Jrg%2BRpaHMbiF4ir4%2BanO7frXH4%2BAy7kw%3D%3D";

            String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+key+"&contentTypeId="+contentTypeId+"&areaCode="+areaCode+"&sigunguCode="+sigunguCode+"&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1";

            try{
                boolean b_title = false;
                boolean b_addr1 = false;
                boolean b_contenttypeid = false;
                boolean b_contentid = false;
                boolean b_firstimage = false;
                boolean b_areacode = false;
                boolean b_mapx = false;
                boolean b_mapy = false;

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
                            tourDataItems = new ArrayList<TourDataItem>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item") && Item!=null){
                                tourDataItems.add(Item);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("item")){
                                Item = new TourDataItem();
                            }
                            if(parser.getName().equals("title")){
                                b_title=true;
                            }
                            if(parser.getName().equals("addr1")){
                                b_addr1=true;
                            }
                            if(parser.getName().equals("contenttypeid")){
                                b_contenttypeid=true;
                            }
                            if(parser.getName().equals("contentid")){
                                b_contentid=true;
                            }
                            if(parser.getName().equals("firstimage")){
                                b_firstimage=true;
                            }
                            if(parser.getName().equals("areacode")){
                                b_areacode=true;
                            }
                            if(parser.getName().equals("mapx")){
                                b_mapx=true;
                            }
                            if(parser.getName().equals("mapy")){
                                b_mapy=true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(b_title){
                                Item.setTitle(parser.getText());
                                b_title=false;
                            }else if(b_addr1){
                                Item.setAddr1(parser.getText());
                                b_addr1=false;
                            }else if(b_contenttypeid){
                                Item.setContenttypeid(Integer.parseInt(parser.getText()));
                                b_contenttypeid=false;
                            }else if(b_contentid){
                                Item.setContentid(parser.getText());
                                b_contentid=false;
                            }else if(b_firstimage){
                                Item.setFirstimage(parser.getText());
                                b_firstimage=false;
                            }else if(b_areacode){
                                Item.setAreacode(Integer.parseInt(parser.getText()));
                                b_areacode=false;
                            }else if(b_mapx){
                                Item.setMapx(Double.parseDouble(parser.getText()));
                                b_mapx=false;
                            }else if(b_mapy){
                                Item.setMapy(Double.parseDouble(parser.getText()));
                                b_mapy=false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Try Fail on do in back",""+e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //TODO 리사이클러 어답터 연결
            Log.e("onPostExecute","post"+s);
            Log.e("onPostExecute","size: "+tourDataItems.size());
            Log.e("onPostExecute","get(0): "+tourDataItems.get(0).getAddr1());

            TravelRecyclerAdapter adapter = new TravelRecyclerAdapter(tourDataItems,getApplicationContext());
            mRecycler.setAdapter(adapter);
        }
    }
}
