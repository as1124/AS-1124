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
        final FruitViewHolder holder = new FruitViewHolder(itemView);
        holder.fruitView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
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
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    class FruitViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImg;
        TextView itemName;
        View fruitView;

        public FruitViewHolder(View itemView) {
            super(itemView);
            fruitView = itemView;
            itemImg = itemView.findViewById(R.id.fruit_image);
            itemName = itemView.findViewById(R.id.fruit_name);
        }
    }

}
