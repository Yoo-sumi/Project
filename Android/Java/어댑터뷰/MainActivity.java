package com.example.appinfo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.appinfo.databinding.ActivityMainBinding;
import com.example.appinfo.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;


class Info{
    Drawable mIcon;
    CharSequence mLabel;
    String mPackageName;
    Info(Drawable icon, CharSequence label, String packageName){
        mIcon=icon;
        mLabel=label;
        mPackageName=packageName;
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ItemBinding itemBinding;
    MyViewHolder(ItemBinding binding){
        super(binding.getRoot());
        itemBinding=binding;
    }
}
class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private List<Info> mInfo;

    MyAdapter(List<Info> info){
        mInfo=info;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        ItemBinding itemBinding=ItemBinding.inflate(inflater,parent,false);
        return new MyViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Info info=mInfo.get(position);
        holder.itemBinding.imageView.setImageDrawable(info.mIcon);
        holder.itemBinding.textView1.setText(info.mLabel);
        holder.itemBinding.textView2.setText(info.mPackageName);
    }

    @Override
    public int getItemCount() {
        return mInfo.size();
    }
}
public class MainActivity extends AppCompatActivity {
    ArrayList mInfo=new ArrayList<Info>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final PackageManager pm = getPackageManager();//context의메소드,Activity에서호출가능
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for(int i=0;i<packages.size();i++){
            ApplicationInfo appInfo = packages.get(i);
            CharSequence label= appInfo.loadLabel(pm);
            Drawable icon = appInfo.loadIcon(pm);
            String packageName = appInfo.packageName;
            mInfo.add(new Info(icon, label,packageName));
        }
        binding.recyclerview.setAdapter(new MyAdapter(mInfo));
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}

