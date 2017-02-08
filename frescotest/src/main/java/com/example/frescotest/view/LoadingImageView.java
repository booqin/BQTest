
package com.example.frescotest.view;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequest.RequestLevel;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

/**
 * 集成了网络图片加载功能的ImageView。<br>
 * 使用时，该View的width和height不能设为wrap_content，否则无法加载图片。需要match_parent或者具体尺寸。
 * 请求图片时，可以不用带上长宽参数，该View内部会自己计算，直接调用{@link #setLoadingImage(String)}即可。
 * 也可以调用{@link #setLoadingImage(String, int, int)}，传入图片的大小，这个方法效率会比不传尺寸的高一点点。
 *
 * @author Byron(hzchenlk&corp.netease.com)
 */
public class LoadingImageView extends SimpleDraweeView {
    private int mWidth = 0, mHeight = 0;
    private String mUrl;//记录请求url,用于个别界面需要取出比较
    private boolean mIsCircle = false;


    public LoadingImageView(Context context) {
        super(context);
        init();
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 只能被调用一次
     */
    private void init() {
//        ViewImageProgressHolderDrawable progressDrawable = new ViewImageProgressHolderDrawable(getContext());
//        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
//        GenericDraweeHierarchy h = builder
//                .setPlaceholderImage(progressDrawable)
//                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
//                .setFadeDuration(400)
//                .build();
//        this.setHierarchy(h);
    }

    /** 取消进度表示占位，占位切换为纯色背景 */
//    public void setPureHolderDrawable() {
//        GenericDraweeHierarchy hierarchy = getHierarchy();
//        if (hierarchy != null) {
//            hierarchy.setPlaceholderImage(new ViewImagePureHolderDrawable());
//            setHierarchy(hierarchy);
//        }
//    }
    /** 取消进度表示占位，占位切换为选择攻略的默认图 */
    public void setDefaultHolderDrawable(int drawableId) {
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(ContextCompat.getDrawable(getContext(), drawableId));
            setHierarchy(hierarchy);
        }
    }


    /**
     * 获取当前加载图片的url
     *
     * @return mUrl
     */
    public String getUrl() {
        return mUrl;
    }

    /** 设置占位（默认）图片的res id。如果不想要默认图片，将resId设为0 */
    public void setPlaceholderImage(int resId) {
        if (resId > 0) {
            GenericDraweeHierarchy h = this.getHierarchy();
            h.setPlaceholderImage(resId);
            this.setHierarchy(h);
        } else {
            setPlaceholderImage(null);
        }
    }

    /** 设置占位（默认）图片。如果不想要默认图片，传null */
    public void setPlaceholderImage(Drawable drawable) {
        GenericDraweeHierarchy h = this.getHierarchy();
        h.setPlaceholderImage(drawable);
        this.setHierarchy(h);
    }

    public void setRoundingParams(RoundingParams rp) {
        GenericDraweeHierarchy h = this.getHierarchy();
        h.setRoundingParams(rp);
        this.setHierarchy(h);
    }

    public RoundingParams getRoundingParams() {
        GenericDraweeHierarchy h = this.getHierarchy();

        if (h != null) {
            return h.getRoundingParams();
        }

        return null;
    }

    /**
     * 设置图片带有圆角
     */
    public void setIsCircle(final boolean isCircle) {
        getHierarchy().setRoundingParams(new RoundingParams() {
            @Override
            public boolean getRoundAsCircle() {
                mIsCircle = isCircle;
                return mIsCircle;
            }
        });
        GenericDraweeHierarchy hierarchy = getHierarchy();
    }

    /**
     *
     */
    public void setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
        getHierarchy().setActualImageScaleType(scaleType);
    }

    /**
     * 加载网络图片。
     * 根据该LoadingImageView的长宽去取（Urs上的）图片。所以该View必须是尺寸确定的。
     * 否则需要调用{@link #setLoadingImage(String, int, int)}
     *
     * @param url
     */
    public void setLoadingImage(final String url) {
        mUrl = url;
        if (TextUtils.isEmpty(url)) {
            setURI(null);
            return;
        }

        ViewTreeObserver vo = this.getViewTreeObserver();
        vo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);

                mWidth = getWidth();
                mHeight = getHeight();
                load(url, mWidth, mHeight);

                return true;
            }
        });
    }

    /**
     * 清除url对应的磁盘缓存以及内存缓存，重新加载图片
     *
     * @param url
     */
    public void loadImageAndEvictCache(String url) {
        Uri uri = Uri.parse(url);

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

        setImageURI(uri);
    }

    /**
     * 清除url对应的磁盘缓存以及内存缓存，重新加载图片
     *
     * @param uri
     */
    public void loadImageAndEvictCache(Uri uri) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

        setImageURI(uri);
    }

    /**
     * 加载已知尺寸的网络图片。
     *
     * @param url
     */
    public void setLoadingImage(final String url, int width, int height) {
        mUrl = url;
        if (TextUtils.isEmpty(url)) {
            setURI(null);
            return;
        }
        load(url, width, height);
    }

    private void load(String url, int w, int h) {
        url = makeServerClipUrl(url.trim(), w, h);
        Uri uri = Uri.parse(url);
        setURI(uri);
    }

    private void setURI(Uri uri) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri).build();
        this.setController(controller);
    }

    /**
     * 优先从Cache（Memory、Disk)中加载下载过的图片
     *
     * @param url
     */
    public void setLoadingImageFromCache(String url, int width, int height) {
        mUrl = url;
        Uri uri = Uri.parse(makeServerClipUrl(url.trim(), width, height));

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(imageRequest)
                .build();
        this.setController(controller);
    }

    /**
     * 优先从Cache（Memory、Disk)中加载下载过的图片
     *
     * @param url
     */
    public void setLoadingImageFromCache(String url, int width, int height, final ImageDownloadListener listener) {
        mUrl = url;
        Uri uri = Uri.parse(makeServerClipUrl(url.trim(), width, height));

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(imageRequest)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        if (listener != null) {
                            listener.onImageGet();
                        }
                    }
                })
                .build();
        this.setController(controller);
    }

    /**
     * 把图片下载到磁盘缓存，但是不显示出来
     */
    public void downloadToDisCache(String url, int width, int height) {
        Uri uri = Uri.parse(makeServerClipUrl(url.trim(), width, height));
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();

      //  Fresco.getImagePipeline().prefetchToDiskCache(request, VstoreApp.getAppInstance());

      //  Fresco.getImagePipeline().prefetchToDiskCache(request, VstoreApp.getAppInstance());

        Fresco.getImagePipeline().prefetchToDiskCache(request, getContext().getApplicationContext());

    }

    /**
     * 添加图片服务器裁剪参数
     *
     * @param url
     * @return
     */
    public static String makeServerClipUrl(String url, int clipWidth, int clipHeight) {
        // 存储在改服务器的图片才能进行裁剪
        if (url.startsWith("http://paopao.nosdn.127.net")) {
            if (clipWidth >= 0 || clipHeight >= 0) { // 不要都是0的
                // 添加裁剪参数?resize=100x100
                StringBuilder sb = new StringBuilder(url);
                sb.append("?resize=").append(clipWidth).append("x").append(clipHeight).append("&type=webp");

                return sb.toString();
            } else {
                return url + "?type=webp";
            }
        }

        // 返回原地址
        return url;
    }

    /**
     * 显示图库相片时使用的方法
     *
     * @param url
     * @param width
     * @param height
     */
    public void setGalleryImage(final String url, int width, int height) {
        Uri uri = Uri.parse(url);
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))//图片目标大小
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(getController())
                .setImageRequest(imageRequest)
                .build();
        this.setController(controller);

    }

    public static interface ImageDownloadListener {
        public void onImageGet();
    }
}
