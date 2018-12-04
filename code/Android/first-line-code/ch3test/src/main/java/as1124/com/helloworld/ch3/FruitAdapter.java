package as1124.com.helloworld.ch3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import as1124.com.helloworld.R;

/**
 * ListView中Item的适配器，ListView处理中最大的问题在于显示优化（重复渲染等问题）
 * <ol>
 * <li>通过判断convertView来避免Item重复渲染</li>
 * <li>通过{@link ViewHolder} 来管理Item子项，提升控件查找效率</li>
 * </ol>
 *
 * @author as-1124(as1124huang@gmail.com)
 */
public class FruitAdapter extends ArrayAdapter<FruitItem> {

    /**
     * 需要填充的布局item的值
     */
    private int resourceID;

    private View.OnClickListener listener;

    public FruitAdapter(Context context, int resource, List<FruitItem> objects, View.OnClickListener viewListener) {
        super(context, resource, objects);
        this.resourceID = resource;
        this.listener = viewListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FruitItem itemData = getItem(position);
        View itemView;
        ViewHolder holder = null;
        if (convertView == null) {
            // 通过判断convertView来防止重复渲染item，提升
            itemView = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
            holder = new ViewHolder();
            holder.img = itemView.findViewById(R.id.fruit_image);
            holder.img.setTag(position);
            holder.img.setOnClickListener(listener);
            holder.fruitName = itemView.findViewById(R.id.fruit_name);
            holder.fruitName.setTag(position);
            holder.fruitName.setOnClickListener(listener);
            itemView.setTag(holder);
        } else {
            itemView = convertView;
            holder = (ViewHolder) itemView.getTag();
        }
//        ImageView fruitImg = itemView.findViewById(R.id.fruit_image);
//        TextView fruitName = itemView.findViewById(R.id.fruit_name);
//        if (itemData != null) {
//            fruitImg.setImageResource(itemData.getImageRes());
//            fruitName.setText(itemData.getName());
//        }
        if (itemData != null) {
            holder.img.setImageResource(itemData.getImageRes());
            holder.fruitName.setText(itemData.getName());
        }
        return itemView;
    }

    class ViewHolder {
        public ImageView img;
        public TextView fruitName;
    }
}
