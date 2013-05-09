package com.example.seat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seat.SeatView.ZoomChangeListener;

public class MovieSelectSeatActivity extends Activity {

	public static int ROW =15;// 设置最大列数
	public static int COL = 20;// 设置最大行数
 
	private ProgressDialog proDialog;
	private TextView yingmuTextView;
	int p1;
	protected static int width;
	protected static int height;
	StringBuilder seat = new StringBuilder();
	List<Integer> buySeatList = new ArrayList<Integer>();
	SeatView seatView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_movie_select_seat);
		initView();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		width=(displayMetrics.widthPixels);
		height=(displayMetrics.heightPixels);
		new Thread(runnable).start();

	}

	private void initView() {
		yingmuTextView = (TextView) findViewById(R.id.yingmu);
		yingmuTextView.setText("测试影院" + " 测试厅" + " 荧幕");
		proDialog = ProgressDialog.show(MovieSelectSeatActivity.this, "加载",
				"加载数据中，请稍候...", true, true);
		proDialog.setCanceledOnTouchOutside(false);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case 1:
				proDialog.dismiss();
				updateUI();
				break;
			}
		}
	};

	/**
	 * 显示所有座位
	 */
	private void updateUI() {
		LinearLayout myView = (LinearLayout) findViewById(R.id.seatviewcont);
		myView.removeAllViews();
		seatView = new SeatView(this);
		myView.addView(seatView);
		seatView.setZoomChangeListener(new ZoomChangeListener() {
			public void ZoomChange(int box_size) {
				// TODO Auto-generated method stub
				LinearLayout myView2 = (LinearLayout) findViewById(R.id.seatraw);
				myView2.removeAllViews();
				for (int i = 0; i < COL; i++) {
					TextView textView = new TextView(
							MovieSelectSeatActivity.this);
					textView.setText((i + 1) + "");
					textView.setTextSize(9.0f);
					textView.setGravity(Gravity.CENTER_VERTICAL);
					textView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT, box_size));
					myView2.addView(textView);
				}
			}
		});
	}

	Runnable runnable = new Runnable() {
		public void run() {
			Message msg = handler.obtainMessage();
			msg.arg1 = 1;
			handler.sendMessage(msg);
		}
	};

}
