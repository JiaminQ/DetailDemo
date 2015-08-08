package com.example.jiamin.detaildemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jiamin on 8/8/15.
 */
public class RotaryKnobView extends View {

    private static final String TAG = RotaryKnobView.class.getSimpleName();

    private float currentValue=0.0f;
    Bitmap backgroundBitmap;
    Paint backgroundPaint, handPaint,  handScrewPaint;
    Path handPath;
 //   private String mTitle;
 //   private int mLogoID;

    private OnRotateListener listener = null;


    public RotaryKnobView(Context context) {
        super(context);
        init(null);
    }



    public RotaryKnobView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public RotaryKnobView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // This layer setting is very important, or some drawings may not show up
        // However, this will have performance penalty.
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initDrawingTools();
        initAttrs(attrs);
    }
    public void setCurrentValue(int value){
        Log.d("setCurrentValue",Float.toString(currentValue));
        currentValue = value;
    }

    private void initAttrs(AttributeSet attrs){
        if (attrs != null) {
            String packageName = "http://schemas.android.com/apk/res-auto";
        //    mTitle = attrs.getAttributeValue(packageName, "title");
        //    if (mTitle == null) mTitle = "";
        //    mLogoID = attrs.getAttributeResourceValue(packageName, "image", R.drawable.su3);
        }
    }

    private void initDrawingTools(){
        backgroundPaint = new Paint();
        backgroundPaint.setFilterBitmap(true);

        handPaint = new Paint();
        handPaint.setAntiAlias(true);
        handPaint.setColor(0xff392f2c);
        handPaint.setShadowLayer(0.01f, -0.005f, -0.005f, 0x7f000000);
        handPaint.setStyle(Paint.Style.FILL);

        handPath = new Path();
        handPath.moveTo(0.5f, 0.5f + 0.2f);
        handPath.lineTo(0.5f - 0.010f, 0.5f + 0.2f - 0.01f);
        handPath.lineTo(0.5f - 0.002f, 0.5f - 0.28f);
        handPath.lineTo(0.5f + 0.002f, 0.5f - 0.28f);
        handPath.lineTo(0.5f + 0.010f, 0.5f + 0.2f - 0.01f);
        handPath.lineTo(0.5f, 0.5f + 0.2f);
        handPath.addCircle(0.5f, 0.5f, 0.025f, Path.Direction.CW);

        handScrewPaint = new Paint();
        handScrewPaint.setAntiAlias(true);
        handScrewPaint.setColor(0xff493f3c);
        handScrewPaint.setStyle(Paint.Style.FILL);
    }

    public interface OnRotateListener {
        public void onRotate(int arg);
    }

    public void setOnRotateListener( OnRotateListener l )
    {
        listener = l;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int chosenWidth = chooseDimension(widthMode, widthSize);
        int chosenHeight = chooseDimension(heightMode, heightSize);

        int chosenDimension = Math.min(chosenWidth, chosenHeight);

        setMeasuredDimension(chosenDimension, chosenDimension);
    }

    private int chooseDimension(int mode, int size) {
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY) {
            return size;
        } else { // (mode == MeasureSpec.UNSPECIFIED)
            return getPreferredSize();
        }
    }

    // in case there is no size specified
    private int getPreferredSize() {
        return 300;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        if (backgroundBitmap != null) {
            Matrix matrix = new Matrix();
            // Exercise: need to finish the following code
            matrix.setRotate(currentValue,backgroundBitmap.getWidth()/2,backgroundBitmap.getHeight()/2 );

            canvas.drawBitmap(backgroundBitmap, matrix , backgroundPaint);
            if(currentValue%5==0)
                Log.d("onDraw",Float.toString(currentValue));


        }

        //    drawingExperiment(canvas);

    }

    // Test the drawing APIs when the hardware acceleration is on/off
    void drawingExperiment(Canvas canvas){
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale(getWidth(), getWidth());

        Paint paint = new Paint();
        paint.setColor(0x9f004d0f);
        paint.setTextSize(0.05f);
        paint.setLinearText(true);

        canvas.drawPath(handPath, handPaint);
        canvas.drawCircle(0.5f, 0.5f, 0.05f, handScrewPaint);
        canvas.drawText("Hello", 0.3f, 0.5f, paint);
        canvas.drawRect(0.3f, 0.3f, 0.4f, 0.4f, handPaint);

        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        regenerateBackground();
    }

    private void regenerateBackground() {
        float width = (float) getWidth();
        float height = (float) getHeight();

        backgroundBitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        Canvas backgroundCanvas = new Canvas(backgroundBitmap);

        float scale = (float) getWidth();

        backgroundCanvas.save(Canvas.MATRIX_SAVE_FLAG);
        backgroundCanvas.scale(scale, scale);

        drawShadedCircle(backgroundCanvas);
      //  drawTitle(backgroundCanvas);
      //  drawLogoBitmap(backgroundCanvas);
      //  drawScale(backgroundCanvas);

        backgroundCanvas.restore();
    }


    void drawShadedCircle(Canvas canvas){
        int width = canvas.getWidth();

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new LinearGradient(0.40f, 0.0f, 0.60f, 1.0f,
                Color.rgb(0xf0, 0xf5, 0xf0),
                Color.rgb(0x30, 0x31, 0x30),
                Shader.TileMode.CLAMP));


        canvas.drawCircle(0.5f, 0.5f, 0.35f, paint);


        Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.BLUE);
        paint2.setStrokeWidth(0.03f);
        paint2.setAntiAlias(true);
        paint2.setTextSize(0.025f);
        paint2.setTypeface(Typeface.SANS_SERIF);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setLinearText(true);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(-135,0.5f,0.5f);

        float y1 = 0.15f;
        float y2 = y1 + 0.060f;
        //     canvas.rotate(-90,backgroundBitmap.getWidth()/2,backgroundBitmap.getHeight()/2 );
        canvas.drawLine(0.5f, y1, 0.5f, y2, paint2);
    }


 /*   void drawTitle(Canvas canvas){
        int width = canvas.getWidth();

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.rgb(0xff, 0x66, 0x00));
        titlePaint.setAntiAlias(true);
        titlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(0.05f);
        titlePaint.setTextScaleX(0.8f);

        Path titlePath = new Path();
        titlePath.addArc(new RectF(0.3f, 0.3f, 0.70f, 0.70f), -180.0f, -180.0f);

        canvas.drawTextOnPath(mTitle, titlePath, 0.0f,0.0f, titlePaint);
    }*/



 /*   void drawLogoBitmap(Canvas canvas){
        Paint paint = new Paint();

        Bitmap logo = BitmapFactory.decodeResource(getContext().getResources(), mLogoID);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(0.60f, 0.35f);
        RectF rect = new RectF(0.0f, 0.0f, 0.12f, 0.12f);
        canvas.drawBitmap(logo, null, rect, paint);
        canvas.restore();
    }*/

    void drawScale (Canvas canvas){
        int degreesPerNick = 3;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0x9f004d0f);
        paint.setStrokeWidth(0.003f);
        paint.setAntiAlias(true);
        paint.setTextSize(0.025f);
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setLinearText(true);

        canvas.drawCircle(0.5f, 0.5f, 0.35f, paint);

        int total = 360/degreesPerNick;

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        for (int i = 0; i < total; ++i) {
            float y1 = 0.15f;
            float y2 = y1 + 0.020f;
            int degree = i*degreesPerNick;

            canvas.drawLine(0.5f, y1, 0.5f, y2, paint);

            if (i % 5 == 0) {
                int value = i*degreesPerNick;
                String valueString = Integer.toString(value);
                canvas.drawText(valueString, 0.5f, y2 + 0.025f, paint);
            }

            canvas.rotate(degreesPerNick, 0.5f, 0.5f);
        }

        canvas.restore();
    }


    public void setAngle(float angle) {
        currentValue = angle;
        invalidate();

    }

    private float theta_old=0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float theta = getTheta(x,y);

        switch(event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                theta_old = theta;
                break;
            case MotionEvent.ACTION_MOVE:
                float delta_theta = theta - theta_old;
                theta_old = theta;
                int direction = (delta_theta > 0) ? 1 : -1;
                currentValue += 3*direction;

                if (currentValue < 0) currentValue = currentValue + 360;
                currentValue -= ((int)(currentValue /360)) * 360;

                // Exercise: this part is incomplete
                invalidate();
                if(listener!=null)
                    listener.onRotate((int)currentValue);
                // .......

                break;
        }
        return true;
    }

    private float getTheta(float x, float y)
    {
        float sx = x - (getWidth() / 2.0f);
        float sy = y - (getHeight() / 2.0f);

        float length = (float)Math.sqrt( sx*sx + sy*sy);
        float nx = sx / length;
        float ny = sy / length;
        float theta = (float)Math.atan2( ny, nx );

        final float rad2deg = (float)(180.0/Math.PI);
        float thetaDeg = theta*rad2deg;

        return (thetaDeg < 0) ? thetaDeg + 360.0f : thetaDeg;
    }

}
