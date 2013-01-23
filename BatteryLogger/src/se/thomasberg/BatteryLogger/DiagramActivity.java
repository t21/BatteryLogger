package se.thomasberg.BatteryLogger;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public class DiagramActivity extends Activity {

	private DiagramView diagramView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (MyApplication.DEBUG_DiagramView) Log.d(MyApplication.TAG, "+++ ON CREATE +++");

		diagramView = new DiagramView(this);
//		diagramView.setLayoutParams(new ViewGroup.LayoutParams(2880, ViewGroup.LayoutParams.MATCH_PARENT));
		diagramView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.graphview);
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll1);
//		ll.addView(diagramView);
		
//		Button button = (Button)findViewById(R.id.widget32);
//		button.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
//			}
//		});
//
//		startService(new Intent(this, BattLogService.class));
//
//		c = getData();

	}
}
