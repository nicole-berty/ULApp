package ie.ul.restuarantmenus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    public void onBindViewHolder(@NonNull ListOfRestaurantsAdapter.RecyclerViewHolder holder, int position) {
        holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    private LayoutInflater myInflater;
    private List<Integer> images;

    public ListOfRestaurantsAdapter(Context context, List<Integer> images) {
        this.myInflater = LayoutInflater.from(context);
        this.images = images;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public RecyclerViewHolder(@NonNull View itemview) {
            super(itemview);
            imageView = itemview.findViewById(R.id.RestaurantImage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_listOfRestuarants_to_stablesClubMenu);
                }
            });
        }
    }
}