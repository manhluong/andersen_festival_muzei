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
