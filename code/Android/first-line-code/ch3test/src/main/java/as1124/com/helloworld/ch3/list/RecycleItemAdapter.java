package as1124.com.helloworld.ch3.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import as1124.com.helloworld.R;
import as1124.com.helloworld.ch3.FruitItem;

public class RecycleItemAdapter extends RecyclerView.Adapter<RecycleItemAdapter.FruitViewHolder> {

    private List<FruitItem> fruits;

    public RecycleItemAdapter(List<FruitItem> items) {
        fruits = items;
    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_fruit, parent, false);
        FruitViewHolder holder = new FruitViewHolder(itemView);
        holder.itemView.setOnClickListener(v -> {
            // 只有执行onBindViewHolder之后才能获取到getAdapterPosition()的值，否则一直为(-1)
            Integer position = (Integer) v.getTag();
            FruitItem itemData = fruits.get(position);
            Toast.makeText(v.getContext(), "U clicked view: " + itemData.getName(), Toast.LENGTH_SHORT).show();
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position) {
        FruitItem itemData = fruits.get(position);
        holder.itemName.setText(itemData.getName());
        holder.itemImg.setImageResource(itemData.getImageRes());
        holder.itemView.setTag(Integer.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    protected class FruitViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImg;
        TextView itemName;

        public FruitViewHolder(View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.fruit_image);
            itemName = itemView.findViewById(R.id.fruit_name);
        }
    }

}
