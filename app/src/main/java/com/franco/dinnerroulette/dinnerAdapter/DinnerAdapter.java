package com.franco.dinnerroulette.dinnerAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.franco.dinnerroulette.MainActivity;
import com.franco.dinnerroulette.R;
import com.franco.dinnerroulette.model.Restaurant;

import java.util.List;

/**
 * Created by franc on 3/9/2018.
 */

public class DinnerAdapter extends ArrayAdapter<Restaurant> implements AdapterView.OnItemClickListener{

public DinnerAdapter(Context context, List<Restaurant>restaurants){
    super(context, 0, restaurants );
}


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        Restaurant restaurant = getItem( position );
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate( R.layout.list_item, parent, false);
        }

        ImageButton btnDelete = convertView.findViewById( R.id.btnDelete );
        ImageButton btnEdit = convertView.findViewById( R.id.btnEdit );

        btnDelete.setOnClickListener( v->{
            Restaurant item = this.getItem(position);

            this.remove(item);
            this.notifyDataSetChanged();

        } );

        btnEdit.setOnClickListener( (View v) ->{
            Restaurant item = this.getItem(position);

            final EditText input = new EditText(getContext());
            input.setInputType( InputType.TYPE_CLASS_TEXT);
            input.setText( item.name );
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                    .setTitle("Update Restaurant")
                    .setMessage("Enter the restaurant's name")
                    .setCancelable(false);
            alert.setView(input);

            alert.setPositiveButton("Update", (dialog, which) -> item.name = input.getText().toString() );
            alert.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel() );

            alert.show();

            this.notifyDataSetChanged();

        } );


        TextView restaurantName = convertView.findViewById( R.id.listItemText );
        assert restaurant != null;
        restaurantName.setText( restaurant.name );



        return  convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


}
