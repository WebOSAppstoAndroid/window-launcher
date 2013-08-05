package com.djqballer.windowlauncher;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
    }
    
    public void submitSearch(View v){
    	EditText search = (EditText)findViewById(R.id.searchBar);
		String toSearchFor = search.getText().toString();
				
				if(toSearchFor.equals("")){
					Toast.makeText(this, "Enter in something to search for.", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "Still Implementing Searching Process", Toast.LENGTH_SHORT).show();
				}
			Log.i("Error Finder", "Successfully completed function submitSearch()");
	}
    
    public void gotoSettings(View v){
    	Intent settingsIntent = new Intent(this, Settings.class);
    	startActivity(settingsIntent);
    }
    
    public void runApp(View v) {
    	
    	//Gets Installed Apps
    	PackageManager pacman = getPackageManager();
    	final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    	mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    	final List<ResolveInfo> apps = pacman.queryIntentActivities(mainIntent, 0);
    	
    	//Starts App
    	int iconId = v.getId();
    	Intent appToStart = pacman.getLaunchIntentForPackage(apps.get(iconId-1).activityInfo.packageName);
    	this.startActivity(appToStart);
    	
    	//Clears App List (onPause won't work)
    	GridLayout appList = (GridLayout)findViewById(R.id.applist);
    	appList.removeAllViewsInLayout();
    	
    	Log.i("Error Finder", "Successfully started app");
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	PackageManager pacman = getPackageManager();
    	final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    	mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    	final List<ResolveInfo> apps = pacman.queryIntentActivities(mainIntent, 0);
    	
    	LinearLayout runningAppLayout = (LinearLayout)findViewById(R.id.runningAppLayout);
    	GridLayout appList = (GridLayout)findViewById(R.id.applist);
    	
    	Intent intent = getIntent();
    	int numOfColumns = intent.getIntExtra("numOfColumns", 3);
    	appList.setColumnOrderPreserved(false);
    	appList.setColumnCount(numOfColumns);
    	appList.setColumnOrderPreserved(true);
    	Log.d("aValue", String.valueOf(numOfColumns));
    	
    	for(int i = 1; i != apps.size(); i++){
    		
    		//Creates Layouts and sets the Parameters
    		LinearLayout mainLayout = new LinearLayout(this);
    		LinearLayout tempLayout = new LinearLayout(this);
    		tempLayout.setOrientation(1);
    		LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(144, LinearLayout.LayoutParams.WRAP_CONTENT);
    		LinearLayout.LayoutParams tempLayoutParams = new LinearLayout.LayoutParams(108, LinearLayout.LayoutParams.WRAP_CONTENT);
    		mainLayout.setLayoutParams(mainLayoutParams);
    		tempLayout.setLayoutParams(tempLayoutParams);
    		
    		//Sets View Parameters and Parents
    		appList.addView(mainLayout);
    		mainLayout.setGravity(Gravity.CENTER);
    		mainLayout.addView(tempLayout);
    		ImageView icon = new ImageView(this);
    		icon.setAdjustViewBounds(true);
    		icon.setMaxHeight(72);
    		icon.setMaxWidth(72);
    		TextView appName = new TextView(this);
    		appName.setGravity(Gravity.CENTER);
    		
    		//Gets Visual
    		icon.setImageDrawable(apps.get(i-1).loadIcon(pacman));
    		appName.setText(apps.get(i-1).loadLabel(pacman));
    		
    		//Sets Id and Creates onClick method
    		icon.setId(i);
    		icon.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					runApp(v);
				}});
    		
    		//Adds Parents
    		tempLayout.addView(icon);
    		tempLayout.addView(appName);
    	}
    	
    	Log.i("Error Finder", "Successfully resumed program");
    	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	LinearLayout runningAppLayout = (LinearLayout)findViewById(R.id.runningAppLayout);
    	runningAppLayout.removeAllViewsInLayout();
    	Log.i("Error Finder", "Succcessfully paused program");
    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}
