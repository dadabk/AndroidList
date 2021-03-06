package pl.androidlist.androidlist.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.androidlist.androidlist.Fragments.TripActivity;
import pl.androidlist.androidlist.MainActivity;
import pl.androidlist.androidlist.Model.Wyjazd;
import pl.androidlist.androidlist.R;
import pl.androidlist.androidlist.TripsListActivity;

/**
 * Created by Makarion on 2018-11-22.
 */

public class TripsListAdapter extends RecyclerView.Adapter<TripsListAdapter.ViewHolder>{

    private Context context;
    Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM TRIPS");
    private List<Wyjazd> tripsList;
    View.OnClickListener onClickListener;

    public TripsListAdapter(Context context,List<Wyjazd> tripsList) {
        this.context = context;
        this.tripsList = tripsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        Wyjazd wyjazd = tripsList.get(position);

        viewHolder.txtDepartureDate.setText(wyjazd.getDataWyjazdu());
        viewHolder.txtReturnDate.setText(wyjazd.getDataPowrotu());
        viewHolder.txtPrice.setText(wyjazd.getCena());
        viewHolder.txtLocation.setText(wyjazd.getLokalizacja());

        byte [] recordImage = wyjazd.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
        viewHolder.img.setImageBitmap(bitmap);

        if(!cursor.moveToPosition(position)){
            return;
        }

        int id = cursor.getInt(0);

        viewHolder.itemView.setTag(id);

        viewHolder.trip_item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TripActivity.class);
                intent.putExtra("departure_date", tripsList.get(position).getDataWyjazdu());
                intent.putExtra("return_date", tripsList.get(position).getDataPowrotu());
                intent.putExtra("price", tripsList.get(position).getCena());
                intent.putExtra("location", tripsList.get(position).getLokalizacja());
                intent.putExtra("image", tripsList.get(position).getImage());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView trip_item;
        ImageView img;
        TextView txtDepartureDate, txtReturnDate, txtPrice, txtLocation;

        public ViewHolder(View itemView) {
            super(itemView);

            trip_item=itemView.findViewById(R.id.rowTripElement);

            txtDepartureDate = itemView.findViewById(R.id.rowDepartureDate);
            txtReturnDate = itemView.findViewById(R.id.rowReturnDate);
            txtPrice = itemView.findViewById(R.id.rowPrice);
            txtLocation = itemView.findViewById(R.id.rowLocation);
            img = itemView.findViewById(R.id.rowImageView);
        }
    }

    public void setClickListener(View.OnClickListener callback) {
        onClickListener = callback;
    }
}
