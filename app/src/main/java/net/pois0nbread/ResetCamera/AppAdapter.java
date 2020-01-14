package net.pois0nbread.ResetCamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * <pre>
 *     author : Pois0nBread
 *     e-mail : pois0nbreads@gmail.com
 *     time   : 2019/12/02
 *     desc   : AppAdapter
 *     version: 1.0
 * </pre>
 */

class AppAdapter extends BaseAdapter {

    private List<AppInfo> mData;
    private Context mContext;
    private SharedPreferences preferences;

    AppAdapter(List<AppInfo> data, Context context, SharedPreferences sharedPreferences) {
        this.mData = data;
        this.mContext = context;
        this.preferences = sharedPreferences;
    }

    public void changeData(List<AppInfo> appInfos) {
        this.mData = appInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回带数据当前行的Item视图对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_apps, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.apprl = (RelativeLayout) convertView.findViewById(R.id.apps_layout);
            viewHolder.appicon = (ImageView) convertView.findViewById(R.id.apps_icon);
            viewHolder.appname = (TextView) convertView.findViewById(R.id.apps_name);
            viewHolder.appinfo = (TextView) convertView.findViewById(R.id.apps_info);
            viewHolder.appcbx = (CheckBox) convertView.findViewById(R.id.apps_check);
            convertView.setTag(viewHolder);
            viewHolder.appcbx.setTag(index);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.appicon.setImageDrawable(mData.get(index).getIcon());
        viewHolder.appname.setText(mData.get(index).getAppName());
        viewHolder.appinfo.setText(mData.get(index).getPackageName());

        viewHolder.appcbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isPressed()) return;
                mData.get(index).setCheckStatus(isChecked);
                preferences.edit().putBoolean(mData.get(index).getPackageName(), isChecked).apply();
            }
        });
        viewHolder.appcbx.setChecked(mData.get(position).getCheckStatus());
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout apprl;
        ImageView appicon;
        TextView appname;
        TextView appinfo;
        CheckBox appcbx;
    }
}
