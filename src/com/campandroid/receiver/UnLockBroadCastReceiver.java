package com.campandroid.receiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.campandroid.My.MyDataManager;
import com.campandroid.broadcasttest.ListData;
import com.campandroid.broadcasttest.R;

import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UnLockBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent arg1) {
		
		// 여기서 5초가 걸리면... 죽습니다(ANR).
		// 그래서 대부분 startservice()로 서비스를 호출합니다.
		// 호출된 서비스에서 명령을 수행하는 것이지요..
		
		
		boolean bCheck = MyDataManager.GetState(ctx);
		
		//check이 설정되어 있지않으면 카운트안함.
		if (bCheck == false) return;
		
		// unlock에 관련된 카운트를 처리합니다.
		addUnlockCount(ctx);
		
	}

	// unlock에 관련된 카운트를 처리합니다.
	private void addUnlockCount(Context ctx) {
		
		// - 저장된 정보를 배열로 가져옵니다.
		// - 첫번째 내용을 가져옵니다.
		// - 오늘날짜 문자열을 만듭니다.
		// - 첫번째 내용이 오늘날짜 문자열과 같으면 저장합니다.
		// - 다르면, 새롭게 항목을 만들고 배열에 추가합니다.
		ArrayList<ListData> listMain = new ArrayList<ListData>();
		try {
			listMain   = MyDataManager.LoadData(ctx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ListData d = null;
		if(listMain.size() == 0){
		    d = new ListData();
		    d.nCount = 0;
		    d.sDate  = "";
		    
		} else {
			d = listMain.get(0);
		}
		
		String sToday = getToday();
		if(d.sDate.equals(sToday)){
		    d.nCount++;
		    
		    try {
				MyDataManager.SaveData(ctx, listMain);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		} else {
			d = new ListData();
		    d.sDate  = sToday;
			d.nCount = 1;
			MyDataManager.AddData(ctx, d);
		}
		
		Toast.makeText(ctx, "Unlock을 " +d.nCount+" 하셨습니다." , Toast.LENGTH_LONG).show();
		//ShowCustomToast(ctx, "Unlock을 " +d.nCount+" 하셨습니다.", d.nCount);
	}

	// 오늘날짜를 문자열로 만들어요~
	private String getToday() {
		// 현재 시간을 msec으로 구한다.
		long now = System.currentTimeMillis();

		// 현재 시간을 저장 한다.
		Date date = new Date(now);
		
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		return df.format(date);
	}
	
	// 이미지와 함께 보여주는 커스텀 Toast
	private void ShowCustomToast(Context ctx, String str, int nCount){
		
		int nIndx = 0;
		if(nCount >= 10){
			nIndx = 1;
		}
        
		if(nCount >= 50){
			nIndx = 2;
		}
		
		int RES_ID[]={R.drawable.good, R.drawable.warning, R.drawable.bad };
		
		LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService("layout_inflater");
	    View layout = layoutInflater.inflate(R.layout.toast_layout, null);
	    
	    TextView  txtState  = (TextView)layout.findViewById(R.id.txtState);
	    ImageView imgBanner = (ImageView)layout.findViewById(R.id.imgBanner);
	    
	    txtState.setText(str);
	    imgBanner.setImageResource(RES_ID[nIndx]);
	    
	    Toast toast = new Toast(ctx);
	    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
	    toast.setDuration(Toast.LENGTH_LONG);
	    toast.setView(layout);
	    toast.show();
	}

}
