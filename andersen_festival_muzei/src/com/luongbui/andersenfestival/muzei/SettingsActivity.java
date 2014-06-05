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

package com.luongbui.andersenfestival.muzei;

import com.luongbui.andersenfestival.muzei.model.ArtPiece;
import com.luongbui.andersenfestival.muzei.ui.ArtGalleryAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_settings);
      final ListView listview = (ListView) findViewById(android.R.id.list);
      ArtGalleryAdapter adapter = new ArtGalleryAdapter(this,
                                                          R.layout.settings_list_item,
                                                          AndersenFestivalSource.PORTRAITS);
      listview.setAdapter(adapter);
      listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> parent,
                                    final View view,
                                    int position,
                                    long id) {
            final ArtPiece item = (ArtPiece) parent.getItemAtPosition(position);
            //android.util.Log.d("URL", item.getAuthorUrl());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getAuthorUrl()));
            startActivity(browserIntent);
            }

         });
      
      Toast toast = Toast.makeText(this, "Tap on an element to open artist's home page.", Toast.LENGTH_LONG);
      toast.show();
      }
   }
