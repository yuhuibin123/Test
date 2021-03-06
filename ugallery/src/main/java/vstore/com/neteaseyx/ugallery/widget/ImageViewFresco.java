package vstore.com.neteaseyx.ugallery.widget;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import vstore.com.neteaseyx.ugallery.view.ImageProgressHolderDrawable;

/**
 * @author yuhuibin
 * @date 2016-04-27
 */
public class ImageViewFresco extends SimpleDraweeView{
    private Context mContext;

    public ImageViewFresco(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        mContext = context;

    }

    public ImageViewFresco(Context context) {
        super(context);
        mContext = context;

    }

    public ImageViewFresco(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ImageViewFresco(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public ImageViewFresco(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    public void loadImageFilePath(Uri uri, int width, int height){
        ImageProgressHolderDrawable progressDrawable = new ImageProgressHolderDrawable(getContext());
        Resources resources = mContext.getResources();
        //Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_gf_default_photo);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(resources)
                .setFadeDuration(300)
                .setPlaceholderImage(progressDrawable)
                .setFailureImage(progressDrawable)
//                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        setHierarchy(hierarchy);

        mDraweeHolder = DraweeHolder.create(hierarchy, mContext);

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mDraweeHolder.getController())
                .setImageRequest(imageRequest)
                .setAutoPlayAnimations(true)
                .build();

        mDraweeHolder.setController(controller);

    }
    private   DraweeHolder<GenericDraweeHierarchy>  mDraweeHolder;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDraweeHolder.onDetach();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mDraweeHolder.onAttach();
    }
}
