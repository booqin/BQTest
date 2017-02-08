package com.example.frescotest.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.frescotest.R;
import com.example.frescotest.view.LoadingImageView;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO
 * Created by Boqin on 2017/2/8.
 * Modified by Boqin
 *
 * @Version
 */
public class RVAdapter extends RecyclerView.Adapter{

    private List<String> mDateSet;

    public RVAdapter(){
        mDateSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IVVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_imageload, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IVVHolder) holder).setImage(mDateSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDateSet.size();
    }

    public void reduceDateset(){
        mDateSet.remove(0);
    }

    public void setDateSet(String[] strings){
        mDateSet.clear();
        for (int i = 0; i < strings.length; i++) {
            mDateSet.add(strings[i]);
        }
    }

    private class IVVHolder extends RecyclerView.ViewHolder{

        private LoadingImageView mLoadingImageView;

        public IVVHolder(View itemView) {
            super(itemView);
            mLoadingImageView = (LoadingImageView) itemView.findViewById(R.id.iv_load);
        }

        public void setImage(String path){
            mLoadingImageView.setLoadingImage(path);
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            boolean inMemoryCache = imagePipeline.isInBitmapMemoryCache(Uri.parse(path));
            boolean isCacheInDisk = Fresco.getImagePipelineFactory().getMainDiskStorageCache().hasKey(new SimpleCacheKey(path));
            Log.d("bq_fresco", "inMemoryCache:"+inMemoryCache);
            Log.d("bq_fresco", "isCacheInDisk:"+isCacheInDisk);
        }
    }
}
