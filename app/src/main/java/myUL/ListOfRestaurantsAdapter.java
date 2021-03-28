package myUL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListOfRestaurantsAdapter extends RecyclerView.Adapter<ListOfRestaurantsAdapter.RecyclerViewHolder>{


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflater.inflate(R.layout.recyclerview_row_restuarants, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfRestaurantsAdapter.RecyclerViewHolder holder, final int position) {
        holder.imageView.setImageResource(images.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(actions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    private LayoutInflater myInflater;
    private List<Integer> images;
    private List<Integer> actions;

    public ListOfRestaurantsAdapter(Context context, List<Integer> images, List<Integer> actions) {
        this.myInflater = LayoutInflater.from(context);
        this.images = images;
        this.actions = actions;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public RecyclerViewHolder(@NonNull View itemview) {
            super(itemview);
            imageView = itemview.findViewById(R.id.RestaurantImage);
        }
    }
}