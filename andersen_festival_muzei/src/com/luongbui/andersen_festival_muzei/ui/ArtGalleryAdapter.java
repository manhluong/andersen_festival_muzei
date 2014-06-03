package com.luongbui.andersen_festival_muzei.ui;

import com.luongbui.andersen_festival_muzei.R;
import com.luongbui.andersen_festival_muzei.model.ArtPiece;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArtGalleryAdapter extends ArrayAdapter<ArtPiece> {
   
   private final Context ctx;
   private final ArtPiece[] vals;

   public ArtGalleryAdapter(Context context,
                              int resource,
                              ArtPiece[] objects) {
      super(context, resource, objects);
      ctx = context;
      vals = objects;
      }

   @Override
   public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.settings_list_item, parent, false);
      TextView textView = (TextView) rowView.findViewById(R.id.author_name);
      textView.setText(vals[position].getAuthor());
      return rowView;
      }
   }
