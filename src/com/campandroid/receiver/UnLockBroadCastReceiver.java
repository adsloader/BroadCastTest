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
		
		// ���⼭ 5�ʰ� �ɸ���... �׽��ϴ�(ANR).
		// �׷��� ��κ� startservice()�� ���񽺸� ȣ���մϴ�.
		// ȣ��� ���񽺿��� ����� �����ϴ� ��������..
		
		
		boolean bCheck = MyDataManager.GetState(ctx);
		
		//check�� �����Ǿ� ���������� ī��Ʈ����.
		if (bCheck == false) return;
		
		// unlock�� ���õ� ī��Ʈ�� ó���մϴ�.
		addUnlockCount(ctx);
		
	}

	// unlock�� ���õ� ī��Ʈ�� ó���մϴ�.
	private void addUnlockCount(Context ctx) {
		
		// - ����� ������ �迭�� �����ɴϴ�.
		// - ù��° ������ �����ɴϴ�.
		// - ���ó�¥ ���ڿ��� ����ϴ�.
		// - ù��° ������ ���ó�¥ ���ڿ��� ������ �����մϴ�.
		// - �ٸ���, ���Ӱ� �׸��� ����� �迭�� �߰��մϴ�.
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
		
		Toast.makeText(ctx, "Unlock�� " +d.nCount+" �ϼ̽��ϴ�." , Toast.LENGTH_LONG).show();
		//ShowCustomToast(ctx, "Unlock�� " +d.nCount+" �ϼ̽��ϴ�.", d.nCount);
	}

	// ���ó�¥�� ���ڿ��� ������~
	private String getToday() {
		// ���� �ð��� msec���� ���Ѵ�.
		long now = System.currentTimeMillis();

		// ���� �ð��� ���� �Ѵ�.
		Date date = new Date(now);
		
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		return df.format(date);
	}
	
	// �̹����� �Բ� �����ִ� Ŀ���� Toast
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
