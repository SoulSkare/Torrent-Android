/*
 * Simple example of a torrent client on android
 */
package com.example.hellolibtorrent;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.VideoView;

public class HelloLibtorrent extends Activity {

	public static String resUrl;
	public static String sdcardPath = Environment.getExternalStorageDirectory().toString() + "/Torrents/";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.mainlayout);
        
        setTitle("Torrent Client - SoulSkare");
        
        //getUrl2();
        
        final WebView wb1 = (WebView) findViewById(R.id.wb1);
        
        wb1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            	final EditText txtUrl = (EditText) findViewById(R.id.txtUrl);
            	final TextView tvDebug = (TextView) findViewById(R.id.tvDebug);
            	
                String urlb = wb1.getUrl();
                txtUrl.setText(urlb);
            	
                if(!urlb.isEmpty() && isURLMatching(url)) {
                	resUrl = url;
                	
                	starter();
                	//mainFunc();
                	updater();
                	
                    return true;
                }
                else {
                tvDebug.setText("Error. urlb does not contain magnet");
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        
        //wb1.loadUrl("https://oldpiratebay.org/");
        //wb1.loadUrl("https://oldpiratebay.org/search.php?iht=5&age=0&Torrent_sort=seeders.desc");
        
        wb1.loadUrl("https://kickass.so/south-park-s18e08-720p-hdtv-x264-dimension-rartv-t9869028.html");
        
        
        getWindow().getDecorView().clearFocus();
        
        //getActionBar().setIcon(R.drawable.my_icon);
        
        MainScreen m = new MainScreen();
        
        m.runLayout();
        
    }
    
    protected boolean isURLMatching(String urlB) 
    {
        // some logic to match the URL would be safe to have here
    	final WebView wb1 = (WebView) findViewById(R.id.wb1);
    	if (!urlB.isEmpty() && urlB.contains("magnet:"))
    	{
    		final TextView tvDebug = (TextView) findViewById(R.id.tvDebug);
    		
    		tvDebug.setText("Starting torrent.");
    		
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    	
    	
    }
    
    public void alert(String title, String message)
    {
    	new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // Yes clicked
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // No/Cancel clicked
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
    }
    
    public void btnDownload_Clicked(View v)

    {
    	//mainFunc();
    	final WebView wb1 = (WebView) findViewById(R.id.wb1);
    	
    	wb1.loadUrl("https://oldpiratebay.org/search.php?iht=5&age=0&Torrent_sort=seeders.desc");
    	//getUrl2();
    	
    	//hideKeyboard();
    }

    public void btnPauseTor_Clicked(View v){
    	final TextView tvDebug = (TextView) findViewById(R.id.tvDebug);
    	
    	pauseTorrents();
    	tvDebug.setText(pauseTorrents());
    }

    public void btnResumeTor_Clicked(View v) {
    	final TextView tvDebug = (TextView) findViewById(R.id.tvDebug);
    	
    	resumeTorrents();
    	tvDebug.setText(resumeTorrents());
    }
    
    public void btnGo_Clicked(View v)
    {
    	//final WebView wb1 = (WebView) findViewById(R.id.wb1);
    	//final EditText txtUrl = (EditText) findViewById(R.id.txtUrl);
    	//wb1.loadUrl(txtUrl.toString());
    	
//    	setContentView(R.layout.videolayout);
//    	final VideoView vv = (VideoView) findViewById(R.id.videoView1);
//    	1
//    	vv.setVideoPath(sdcardPath + "test.mp4");
//   	
//    	MediaController mediaController = new 
//       			MediaController(this);
//       		mediaController.setAnchorView(vv);
//       		vv.setMediaController(mediaController);
//    	
//    	vv.start();
    }
    
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    
    public void getUrl2()
    {
    	new AsyncTask<Void, Void, Void>() {
    	    @Override
    	    // this runs on another thread
    	    protected Void doInBackground(Void... params) {
    	        // assuming x, y, z, h are visible here
    	        
    	    	new Timer().scheduleAtFixedRate(new TimerTask() {
    		        @Override
    		        public void run() {
    		            runOnUiThread(new Runnable() {
    		                @Override
    		                public void run() {
    		                	final EditText txtUrl = (EditText) findViewById(R.id.txtUrl);
    		                	final WebView wb1 = (WebView) findViewById(R.id.wb1);
    		                	
    		                    String urlb = wb1.getUrl();
    		                    txtUrl.setText(urlb);
    		                    //System.out.println(url);
    		                }
    		            });
    		        }
    		    }, 0, 10);
    	    	
    	    	
    	        return null;
    	    }

    	    @Override
    	    // this runs on main thread
    	    protected void onPostExecute(Void result) {
    	        //TextView txt = (TextView) findViewById(R.id.output);
    	        //txt.setText("Executed");
    	    }

    	}.execute(); // call execute, NOT doInBackGround
    }
    
    public void getUrl()
    {
    	new Timer().scheduleAtFixedRate(new TimerTask() {
	        @Override
	        public void run() {
	            runOnUiThread(new Runnable() {
	                @Override
	                public void run() {
	                	final EditText txtUrl = (EditText) findViewById(R.id.txtUrl);
	                	final WebView wb1 = (WebView) findViewById(R.id.wb1);
	                	
	                    String url = wb1.getUrl();
	                    txtUrl.setText(url);
	                    //System.out.println(url);
	                }
	            });
	        }
	    }, 0, 10);
    }
    
    public void starter() //-------------------------------STARTER--------------------------------------
    {
    	final TextView tvMain = (TextView) findViewById(R.id.tvMain);
    	final WebView wb1 = (WebView) findViewById(R.id.wb1);
    	final TextView tvDebug = (TextView) findViewById(R.id.tvDebug);
    	
    	// Start torrent and output torrent name to screen
    	if(!(wb1.getUrl() == "")) {
		String torrent_name = startTorrent(resUrl, sdcardPath);
	    tvMain.setText(torrent_name);
    	}
    	else {
    		tvDebug.setText("Error. Url empty!");
    	}
    }
    
    public void updater()
    {
    	new AsyncTask<Void, Void, Void>() {
    	    @Override
    	    // this runs on another thread
    	    public Void doInBackground(Void... params) {
    	        // assuming x, y, z, h are visible here

    	    	final TextView tvMain = (TextView) findViewById(R.id.tvMain);
    	    	
    		 // Get updates at a regular interval and print them to the screen
    		    new Timer().scheduleAtFixedRate(new TimerTask() {
    		        @Override
    		        public void run() {
    		            runOnUiThread(new Runnable() {
    		                @Override
    		                public void run() {

    		                    String status = getStatusA(sdcardPath);
    		                    tvMain.setText(status);
    		                    //System.out.println(status);
    		                }
    		            });
    		        }
    		    }, 0, 500);
    	    	
    	    	
    	        return null;
    	    }

    	    @Override
    	    // this runs on main thread
    	    protected void onPostExecute(Void result) {
    	        //TextView txt = (TextView) findViewById(R.id.output);
    	        //txt.setText("Executed");
    	    }

    	}.execute(); // call execute, NOT doInBackGround
    }
    
public void mainFunc()
{
//	runOnUiThread(new Runnable() {
//	  @Override
//	  public void run() {

		    // Create text area with scroll
			final TextView tvMain = (TextView) findViewById(R.id.tvMain);
		    //final TextView textView = new TextView(this);
			//final ScrollView scMain = (ScrollView) findViewById(R.id.svMain);
		    //ScrollView scrollView = new ScrollView(this);
		    //scMain.addView(tvMain);
		    //this.setContentView(scMain);


		    // Get updates at a regular interval and print them to the screen
		    new Timer().scheduleAtFixedRate(new TimerTask() {
		        @Override
		        public void run() {
		            runOnUiThread(new Runnable() {
		                @Override
		                public void run() {
		                    String status = getStatusA(sdcardPath);
		                    tvMain.setText(status);
		                    System.out.println(status);
		                }
		            });
		        }
		    }, 0, 5);

	  }
//	});
//}

    /* A native method that tells us which torrent file we are going to download
     */
    public native String startTorrent(String torHash, String sdcardPathA);
    
    //native method pause torrent
    public native String pauseTorrents();
    
    //native method resume torrent
    public native String resumeTorrents();


    /* A native method that starts the download
     */
    public native String getStatusA(String sdcardPathA);

    /* Which libraries to include and their dependencies
     * Note that the order is very important. Place libraries that are not dependent
     * on anything first, and only place dependent libraries after the dependencies have
     * already been included. 
     */
    static {
	    System.loadLibrary("torrent-rasterbar");
        System.loadLibrary("hello");
    }
}
