package com.example.dell.disktopfloatingviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CircleImageView extends android.support.v7.widget.AppCompatImageButton {
    public CircleImageView(Context context) {
        super (context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到自定义控件的drawable对象，也就是定义的背景图片
        Drawable drawable = getDrawable ();
        if (drawable == null) {
            return;
        }
        if (getWidth () == 0 || getHeight () == 0) {
            return;
        }
        //        Log.d ("LIU", "onDraw: " + getHeight () + "  " + getWidth ());
        //我们需要将drawable对象绘制到自定义控件上，所以要转换为bitmap对象
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap b = bd.getBitmap ();
        //复制一个位图
        Bitmap bitmap = b.copy (Bitmap.Config.ARGB_8888, true);
        //将位图铺满整个自定义控件
        Bitmap roundBitmap = getCroppedBitmap (bitmap, getWidth ());
        //绘制bitmap
        canvas.drawBitmap (roundBitmap, 0, 0, null);
    }


    /*
     * 对Bitmap裁剪，使其变成圆形，这步最关键
     */
    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        //如果设置的图片不能铺满整个自定义控件，我们就从当前存在的位图，按一定的比例创建一个新的位图。
        if (bmp.getWidth () != radius || bmp.getHeight () != radius) {
            sbmp = Bitmap.createScaledBitmap (bmp, radius, radius, false);
        } else {
            sbmp = bmp;
        }
        //创建指定格式，大小的位图
        Bitmap output = Bitmap.createBitmap (sbmp.getWidth (),
                sbmp.getHeight (), Bitmap.Config.ARGB_8888);
        //将画布与位图相联系
        Canvas canvas = new Canvas (output);
        //初始化画笔以及设置矩形大小
        final Paint paint = new Paint ();
        final Rect rect = new Rect (0, 0, sbmp.getWidth (), sbmp.getHeight ());

        paint.setAntiAlias (true);
        paint.setFilterBitmap (true);
        paint.setDither (true);
        //相当于清屏
        canvas.drawARGB (0, 0, 0, 0);
        //为画笔设置颜色
        paint.setColor (Color.parseColor ("#ffffff"));
        //画圆角
        //四个参数 ：圆心X坐标，圆心Y坐标，圆的半径，画笔
        canvas.drawCircle (sbmp.getWidth () / 2, sbmp.getHeight () / 2,
                sbmp.getWidth () / 2, paint);
        //取两层绘制，显示上层
        paint.setXfermode (new PorterDuffXfermode (PorterDuff.Mode.SRC_IN));
        //把原生的图片放到这个画布上，使之带有画布的效果
        //根据指定区域绘制bitmap
        //bitmap：需要绘制的bitmap
        //src：bitmap需要绘制的区域，若src的面积小于bitmap时会对bitmap进行裁剪，一般来说需要绘制整个bitmap时可以为null
        //dst：在画布中指定绘制bitmap的区域，当这个区域的面积与bitmap要显示的面积不匹配时，会进行拉伸，不可为null
        //paint：画笔，可为null
        canvas.drawBitmap (sbmp, rect, rect, paint);

        return output;
    }
}
