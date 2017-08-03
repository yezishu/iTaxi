package yzs.commonlibrary.view.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import yzs.commonlibrary.R;
import yzs.commonlibrary.base.constant.SType;
import yzs.commonlibrary.util.SPUtils;


/**
 * 多账号适配器
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {

    private Map<?, ?> showData;
    private Map<Object, Object> AllData;
    private ArrayFilter mFilter;
    private Context context;

    public AutoCompleteAdapter(Context context, Map<?, ?> map) {
        this.showData = map;
        this.context = context;
    }

    public void setShowData(Map<?, ?> showData) {
        this.showData = showData;
        AllData = null;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (showData != null) {
            return showData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (showData != null) {
            int a = 0;
            for (Map.Entry<?, ?> entry : showData.entrySet()) {
                if (a == position) {
                    return entry;
                } else if (a >= position) {
                    break;
                }
                a += 1;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.dropdown_item, null);
            holder.imgDel = (ImageView) convertView.findViewById(R.id.delete);
            holder.tv = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgDel.setOnClickListener(null);
        if(position==0){
            convertView.findViewById(R.id.v_line_top).setVisibility(View.VISIBLE);
        }
        int a = 0;
        for (Map.Entry<?, ?> entry : showData.entrySet()) {
            if (a == position) {
                final String key = (String) entry.getKey();
                holder.tv.setText(entry.getKey() + "");
                holder.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPUtils.remove(context, SType.LOGIN_USERNAME, key);
                        Map map = SPUtils.getAll(context, SType.LOGIN_USERNAME);
                        setShowData(map);
                    }
                });
                break;
            } else if (a >= position) {
                break;
            }
            a += 1;
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (AllData == null) {
                AllData = new HashMap<>();
                for (Map.Entry<?, ?> entry : showData.entrySet()) {
                    AllData.put(entry.getKey(), entry.getValue());
                }
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = AllData;
                results.count = AllData.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                Map<Object, Object> filter = new HashMap<>();
                for (Map.Entry<?, ?> entry : AllData.entrySet()) {
                    String key = (String) entry.getKey();
                    if (key.contains(prefixString)) {
                        filter.put(entry.getKey(), entry.getValue());
                    }
                }
                results.values = filter;
                results.count = filter.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            showData = (Map<?, ?>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    class ViewHolder {
        private TextView tv;
        private ImageView imgDel;
    }
}
