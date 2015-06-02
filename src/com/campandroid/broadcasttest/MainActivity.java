package com.campandroid.broadcasttest;

import java.util.ArrayList;

import com.campandroid.My.MyDataManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView  txtState  = null;
	TextView  txtCount  = null;
	ImageView imgBanner = null; 
	Button    btnCheck  = null;
	
	// 채크가 설정되어 있는가?
	boolean bCheck     = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		txtState = (TextView)findViewById(R.id.txtState);
		txtCount = (TextView)findViewById(R.id.txtCount);
		imgBanner= (ImageView)findViewById(R.id.imgBanner);
		btnCheck = (Button)findViewById(R.id.btnSet);
		
		bCheck = MyDataManager.GetState(getApplicationContext());
		btnCheck.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				bCheck = !bCheck;
				MyDataManager.SaveState(getApplicationContext(), bCheck);
				ChangeButtonText(bCheck);
			}
			
		});
		
        ChangeButtonText(bCheck);
		
		DisplayCountInfo();
	}

	private void ChangeButtonText(boolean b) {
		if(b == true){
		    btnCheck.setText("실행멈춤하기(현재실행중)");	
		} else {
			btnCheck.setText("실행하기(현재멈춤)");
		}
	}

	// 카운트 정보를 가져와서 보여준다.
	private void DisplayCountInfo() {
		ArrayList<ListData> listMain = new ArrayList<ListData>();
		try {
			listMain   = MyDataManager.LoadData(getApplicationContext());
			ListData d = null;
			
			int    nCount = 0;
			String sState = ""; 
			
			if(listMain.size() != 0){
				d = listMain.get(0);
				nCount = d.nCount;
			}
			 
			txtCount.setText(String.format("%d unlock",nCount ) );
			
			if(nCount < 10){
				sState = "정상이십니다.";
				imgBanner.setImageResource(R.drawable.good);
			}
			
			if(nCount >= 10){
				sState = "자주 열어보고 계십니다.";
				imgBanner.setImageResource(R.drawable.warning);
			}
            
			if(nCount >= 50){
				sState = "중증이시네요.";
				imgBanner.setImageResource(R.drawable.bad);
			}
			
			txtCount.setText(String.format("%d unlock",nCount ) );
			txtState.setText( sState );
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
