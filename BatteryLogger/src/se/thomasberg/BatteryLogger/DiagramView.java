package se.thomasberg.BatteryLogger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DiagramView extends View {

	Paint textPaint;
	int xSize;
	private int cursorX;
	private int cursorY;
	private boolean overScrollLeft;
	private boolean overScrollRight;
	private boolean isScrolling;
	private float oldX;
	private int xScale;

	public DiagramView(Context context) {
		super(context);
		initDiagramView();
	}

	public DiagramView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initDiagramView();
	}

	public DiagramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initDiagramView();
	}

	private void initDiagramView() {
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(16);

		xScale = 1;
		xSize = 60*24;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// debug utskrifter
		drawHelpTextOnScreen(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(MyApplication.TAG, "onMeasure");
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			//            // Measure the text
			//            result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
			//                    + getPaddingRight();
			result = xSize;
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by measureSpec
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		//        mAscent = (int) mTextPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			//            // Measure the text (beware: ascent is a negative number)
			//            result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop()
			//                    + getPaddingBottom();
			result = 300;
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}



	private void drawHelpTextOnScreen(Canvas canvas) {
		if (MyApplication.DEBUG_DiagramView) {
			Log.d(MyApplication.TAG, "onDraw");
			// Draw some helping text on screen
			textPaint.setARGB(255, 255, 255, 255);
			int x0Pos = getScreenLeftmostEdgePosInsideHorizontalScrollView();
			//			//			canvas.drawText("screenY: " + mScreenY, 15+x0Pos, 30, paint);
			//			canvas.drawText("visibleScreenWidth: " + getVisibleScreenWidth(canvas), 15+x0Pos, 50, paint);
			//			canvas.drawText("mScreenX: " + fullScreenSizeX, 15+x0Pos, 70, paint);
			//			canvas.drawText("x0Pos: " + x0Pos, 15+x0Pos, 90, paint);
			//			String startString2 = (String) DateFormat.format("EEEE d MMMM yyyy k:mm", startCal);
			//			canvas.drawText("start: " + startString2, 15+x0Pos, 150, paint);
			//			String endString2 = (String) DateFormat.format("EEEE d MMMM yyyy k:mm", nowCal);
			//			canvas.drawText("end: " + endString2, 15+x0Pos, 170, paint);
			canvas.drawText("cursorX: " + cursorX, 15+x0Pos, 190, textPaint);
			canvas.drawText("cursorY: " + cursorY, 15+x0Pos, 230, textPaint);
			//			// Draw some date helping text
			//			//			paint.setARGB(255, 255, 255, 255);
			//			//			String startString = (String) DateFormat.format("EEEE d MMMM yyyy k:mm", start);
			//			//			canvas.drawText("start: " + start + " - " + startString, 10, 110, paint);
			//			//			String endString = (String) DateFormat.format("EEEE d MMMM yyyy k:mm", end);
			//			//			canvas.drawText("end: " + end + " - " + endString, 10, 130, paint);
			Log.d(MyApplication.TAG, "onDrawX:" + canvas.getWidth());
			Log.d(MyApplication.TAG, "onDrawY:" + canvas.getHeight());
			canvas.drawText("Test", 15, 15, textPaint);
			canvas.drawText("Canvas height:" + canvas.getHeight(), 15, 40, textPaint);
			canvas.drawText("Canvas width:" + canvas.getWidth(), 15, 70, textPaint);
		}
	}

	private int getScreenLeftmostEdgePosInsideHorizontalScrollView() {
		int[] s = new int[2];
		this.getLocationOnScreen(s);
		return -s[0];
	}

	//	@Override
	//	public boolean onTouchEvent(MotionEvent event) {
	//		// TODO Auto-generated method stub
	////		return super.onTouchEvent(event);
	//		Log.d(MyApplication.TAG, "onTouchEvent:" + getOverScrollMode());
	//		
	//		xSize = xSize*2;
	//		requestLayout();
	//		invalidate();
	//		
	//		return false;
	//	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(MyApplication.TAG, "onTouchEvent:" + getOverScrollMode());

		// width of the vertical scrollbar
		int scrollBarWidth = getVerticalScrollbarWidth();

		// width of the view depending of you set in the layout
		int viewWidth = computeHorizontalScrollExtent();

		// width of the webpage depending of the zoom
		int innerWidth = computeHorizontalScrollRange();

		// position of the left side of the horizontal scrollbar
		int scrollBarLeftPos = computeHorizontalScrollOffset();

		// position of the right side of the horizontal scrollbar, the width of scroll is the width of view minus the width of vertical scrollbar
		int scrollBarRightPos = scrollBarLeftPos + viewWidth - scrollBarWidth;

		// if left pos of scroll bar is 0 left over scrolling is true
		if(scrollBarLeftPos == 0) {
			overScrollLeft = true;
		} else {
			overScrollLeft = false;
		}

		// if right pos of scroll bar is superior to webpage width: right over scrolling is true
		if(scrollBarRightPos >= innerWidth) {
			overScrollRight = true;
		} else {
			overScrollRight = false;
		}

		Log.d(MyApplication.TAG, "onTouchEvent:" + event.getAction());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // when user touch the screen
			Log.d(MyApplication.TAG, "onTouchEvent:ACTION_DOWN");
			// if scrollbar is the most left or right
			if(overScrollLeft || overScrollRight) {
				isScrolling = false;
			} else {
				isScrolling = true;
			}
			oldX = event.getX();
			break;

		case MotionEvent.ACTION_UP: // when user stop to touch the screen
			Log.d(MyApplication.TAG, "onTouchEvent:ACTION_UP");
			// if scrollbar can't go more to the left OR right 
			// this allow to force the user to do another gesture when he reach a side
			if(!isScrolling) {
				if(event.getX() > oldX && overScrollLeft) {
					// left action
					xSize = xSize*2;
					requestLayout();
					invalidate();
				}

				if(event.getX() < oldX && overScrollRight) {
					// right action
					xSize = xSize*2;
					requestLayout();
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_CANCEL: // when user stop to touch the screen
			Log.d(MyApplication.TAG, "onTouchEvent:ACTION_CANCEL");
			// if scrollbar can't go more to the left OR right 
			// this allow to force the user to do another gesture when he reach a side
			if(!isScrolling) {
				if(event.getX() > oldX && overScrollLeft) {
					// left action
					xSize = xSize*2;
					this.scrollTo(xSize/2, 0);
					requestLayout();
					invalidate();
				}

				if(event.getX() < oldX && overScrollRight) {
					// right action
					xSize = xSize*2;
					requestLayout();
					invalidate();
				}
			}

			break;
		default:
			break;
		}
		//		return super.onTouchEvent(event);
		return true;
	}

	public boolean onTouch(View v, MotionEvent event) {
		//		public boolean onTouch(View arg0, MotionEvent mEvent) {
		Log.d(MyApplication.TAG, "onTouch");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			//	            currentX = (int) mEvent.getRawX();
			//	            currentY = (int) mEvent.getRawY();
			cursorX = (int) event.getRawX();
			cursorY = (int) event.getRawY();
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			//				int x2 = (int) mEvent.getRawX();
			//				int y2 = (int) mEvent.getRawY();
			//	            container.scrollBy(currentX - x2 , currentY - y2);
			//	            currentX = x2;
			//	            currentY = y2;
			break;
		}   
		case MotionEvent.ACTION_UP: {
			break;
		}
		}
		//			return true;
		//		}		// TODO Auto-generated method stub
		return false;
	}

}
