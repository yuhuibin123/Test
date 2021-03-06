package vstore.com.neteaseyx.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

import vstore.netease.com.ugallery.R;
import vstore.com.neteaseyx.ugallery.UGallery;

/**
 * @author yuhuibin
 * @date 2016-05-06
 */
public class ActivityCropImage extends Activity{
    private static Uri mUri;
    /**
     * 剪裁图像
     * @param context
     */
    public static void startActivity(Context context,  Uri uri){
        mUri = uri;
        Intent intent = new Intent(context, ActivityCropImage.class);
        ( (Activity)context).startActivityForResult(intent, UGallery.CROP_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startCropImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //剪裁单张图像后，通过回调返回结果
            final Uri resultUri = UCrop.getOutput(data);
            setResult(RESULT_OK, new Intent()
                .putExtra(UGallery.PATH, resultUri.getPath())
            );
        } else if (resultCode == UCrop.RESULT_ERROR) {
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 开始剪裁图片
     */
    private void startCropImage(){
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
        UCrop.of(mUri, destinationUri)
                .withAspectRatio(1, 1)
                .withOptions(setCropOption())
                .start(ActivityCropImage.this);
    }

    /**
     * 剪裁图片参数配置
     * @return
     */
    private UCrop.Options setCropOption(){
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        options.setAllowedGestures(UCropActivity.SCALE,UCropActivity.SCALE, UCropActivity.SCALE);
        options.setToolbarColor(getResources().getColor(R.color.cropToolBar));
        options.setCropFrameColor(getResources().getColor(R.color.white));
      //  options.setDimmedLayerColor(getResources().getColor(R.color.white));
        return options;
    }
}
