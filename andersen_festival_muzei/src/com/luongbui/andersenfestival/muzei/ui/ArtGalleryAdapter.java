/*******************************************************************************
 * Copyright 2014 Manh Luong   Bui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.luongbui.andersenfestival.muzei.ui;

import com.luongbui.andersenfestival.muzei.R;
import com.luongbui.andersenfestival.muzei.model.ArtPiece;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtGalleryAdapter extends ArrayAdapter<ArtPiece> {
   
   private static final int[] artistsIcons = {
      R.drawable.giorgia_marras_icon,
      R.drawable.bruno_zocca_icon,
      R.drawable.luca_tagliafico_icon,
      R.drawable.arianna_zuppello_icon,
      R.drawable.daniele_castellano_icon,
      R.drawable.letizia_iannaccone_icon,
      R.drawable.jacopo_oliveri_icon,
      R.drawable.stefano_tirasso_icon,
      R.drawable.matilde_martinelli_icon,
      R.drawable.olga_tranchini_icon,
      R.drawable.anais_tonelli_icon,
      R.drawable.silvia_venturi_icon,
      R.drawable.marco_bassi,
      R.drawable.francesca_consalvo_icon
      };
   
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
      ImageView imageView = (ImageView) rowView.findViewById(R.id.author_icon);
      imageView.setImageResource(artistsIcons[position]);
      return rowView;
      }
   }
