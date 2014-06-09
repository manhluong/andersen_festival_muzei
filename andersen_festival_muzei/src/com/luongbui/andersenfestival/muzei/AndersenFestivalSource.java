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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeSet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.MuzeiArtSource;
import com.luongbui.andersenfestival.muzei.model.ArtPiece;

public class AndersenFestivalSource extends MuzeiArtSource {
	
	private static final String FILE_PROVIDER_AUTHORITIES = "com.luongbui.andersenfestival.muzei.fileprovider";
	
	private static final String PORTRAITS_SUBDIR = "andersen_portraits_2014";
	
	private static final String NAME = "luongbui.AndersenFestivalSource";
	
	private static final String SUBS_KEY = NAME + ".Subs";
	
	private static final String ART_INDEX_KEY = NAME + ".Index";
	
	private static final int NO_INDEX_CODE = -1;
	
	/**
	 * public and static to use it as data for the list adapter of the configuration activity.<br>
	 * <br>
	 * A bit brutal, I know, but it's a quick & dirty solution.
	 */
	public static final ArtPiece[] PORTRAITS = {
	   new ArtPiece("giorgia_marras.jpg", "Hans Christian Andersen", "Giorgia Marras", "2014", "http://giorgiamarras.blogspot.it/"),
	   new ArtPiece("bruno_zocca.jpg", "Hans Christian Andersen", "Bruno Zocca", "2014", "http://brunozoccaillustrator.tumblr.com/"),
	   new ArtPiece("luca_tagliafico.jpg", "Hans Christian Andersen", "Luca Tagliafico", "2014", "http://lucatagliaficoillustrator.tumblr.com/"),
	   new ArtPiece("arianna_zuppello.jpg", "Hans Christian Andersen", "Arianna Zuppello", "2014", "http://arianna-zuppello.tumblr.com/"),
	   new ArtPiece("daniele_castellano.jpg", "Hans Christian Andersen", "Daniele Castellano", "2014", "http://altairiv.tumblr.com/"),
	   new ArtPiece("letizia_iannaccone.jpg", "Hans Christian Andersen", "Letizia Iannaccone", "2014", "http://www.letiziaiannaccone.com/"),
	   new ArtPiece("jacopo_oliveri.jpg", "Hans Christian Andersen", "Jacopo Oliveri", "2014", "http://www.fatomale.com/"),
	   new ArtPiece("stefano_tirasso.jpg", "Hans Christian Andersen", "Stefano Tirasso", "2014", "http://stetirasso.tumblr.com/"),
	   new ArtPiece("matilde_martinelli.jpg", "Hans Christian Andersen", "Matilde Martinelli", "2014", "http://matildemartinelli.tumblr.com/"),
	   new ArtPiece("olga_tranchini.jpg", "Hans Christian Andersen", "Olga Tranchini", "2014", "http://olgatranchini.tumblr.com/"),
	   new ArtPiece("anais_tonelli.jpg", "Hans Christian Andersen", "Anais Tonelli", "2014", "http://anaistonelli.blogspot.it/"),
	   new ArtPiece("silvia_venturi.jpg", "Hans Christian Andersen", "Silvia Venturi", "2014", "http://silviaventuri.tumblr.com/"),
	   new ArtPiece("francesca_consalvo.jpg", "Hans Christian Andersen", "Francesca Consalvo", "2014", "about:blank")
      };
	
	private SharedPreferences prefs;
	
	private Uri fileUri;
	
	public AndersenFestivalSource() {
		super(NAME);
		}
	
	@Override
   public void onCreate() {
	   super.onCreate();
	   prefs = getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
      setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
      }
	
	@Override
	protected void onSubscriberAdded(ComponentName subscriber) {
	   super.onSubscriberAdded(subscriber);
	   //android.util.Log.d("onSubscriberAdded()", "onSubscriberAdded()");
	   // Add the subscriber, so we can grant permissions later.
	   Set<String> subs = prefs.getStringSet(SUBS_KEY, new TreeSet<String>());
	   subs.add(subscriber.getPackageName());
      saveSubsPrefs(subs);
    	}
	
	@Override
	protected void onSubscriberRemoved(ComponentName subscriber) {
	   super.onSubscriberRemoved(subscriber);
	   //android.util.Log.d("onSubscriberRemoved()", "onSubscriberRemoved()");
	   // Remove the subscriber.
	   Set<String> subs = prefs.getStringSet(SUBS_KEY, new TreeSet<String>());
	   subs.remove(subscriber.getPackageName());
      saveSubsPrefs(subs);
    	}
	
	private void saveSubsPrefs(Set<String> subs) {
      SharedPreferences.Editor editor = prefs.edit();
      editor.putStringSet(SUBS_KEY, subs);
      editor.commit();
	   }
	
	private int loadArtIndex() {
	   int res = prefs.getInt(ART_INDEX_KEY, NO_INDEX_CODE);
	   if(res==NO_INDEX_CODE) {
	      res = (int)(Math.random() * ((AndersenFestivalSource.PORTRAITS.length-1) + 1));
	      saveArtIndex(res);
	      }
	   return res;
	   }
	
	private void incrArtIndex(int artIndex) {
	   if(artIndex < (AndersenFestivalSource.PORTRAITS.length-1))
         artIndex++;
      else
         artIndex = 0;
	   saveArtIndex(artIndex);
	   }
	
	private void saveArtIndex(int value) {
	   SharedPreferences.Editor editor = prefs.edit();
      editor.putInt(ART_INDEX_KEY, value);
      editor.commit();
	   }

	@Override
	protected void onUpdate(int reason) {
		try {
		   int artIndex = NO_INDEX_CODE;
		   if(reason==UPDATE_REASON_USER_NEXT ||
			      reason==UPDATE_REASON_SCHEDULED) {
		      incrArtIndex(artIndex);
			   //android.util.Log.d("INDEX USER NEXT", ""+artIndex);
			   }
		   else
		      artIndex = loadArtIndex(); // Random if none exists.
			//android.util.Log.d("INDEX", ""+artIndex);
			//For now, empty the external sub dir each time: TODO a more "flexible" cache system.
			deleteExternalSubdir(new File(getApplicationContext().getFilesDir(),
		                                  PORTRAITS_SUBDIR));
			File outFile = new File(getApplicationContext().getFilesDir(),
			                        PORTRAITS_SUBDIR + File.separator + PORTRAITS[artIndex].getFileName());
         //TODO test if the file exits, should be false.
			//android.util.Log.d("TEST FILE EXISTS", ""+outFile.exists()+" : " + outFile.getPath());
			copyAsset(PORTRAITS_SUBDIR, PORTRAITS[artIndex].getFileName());
			//TODO test if the file exits, should be true.
			//android.util.Log.d("TEST FILE EXISTS", ""+outFile.exists()+" : " + outFile.getPath());
			fileUri = FileProvider.getUriForFile(getApplicationContext(),
                                              FILE_PROVIDER_AUTHORITIES,
                                              outFile);
			Set<String> subscribers = prefs.getStringSet(SUBS_KEY, new TreeSet<String>());
			for (String subPackage : subscribers)
			   getApplicationContext().grantUriPermission(subPackage,
                                                       fileUri,
                                                       Intent.FLAG_GRANT_READ_URI_PERMISSION);
			//android.util.Log.d("SHARED FILE URI", ""+fileUri.toString());
			publishArtwork(new Artwork.Builder()
								.imageUri(fileUri)
								.title(PORTRAITS[artIndex].getTitle())
								.byline(PORTRAITS[artIndex].getByLine())
								.viewIntent(new Intent(Intent.ACTION_VIEW,
										Uri.parse(PORTRAITS[artIndex].getAuthorUrl())))
								.build());
			// 12 Hours
			scheduleUpdate(System.currentTimeMillis() + 12 * 60 * 60 * 1000);
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
   protected void deleteExternalSubdir(File subPath) {
      if(subPath.isDirectory()) {
         for(File child : subPath.listFiles())
            deleteExternalSubdir(child);
         }
      subPath.delete();
      }

	/**
	 * Copy a single asset to private app folder.<br>
	 * <br>
	 * It creates the sub folder accordingly.
	 * <br>
	 * @param subPath A relative path within the assets. Can be a directory: "andersen_portraits".
	 * @param fileName Name of file to copy, relative to subPath.
	 * @throws IOException
	 */
	protected void copyAsset(String subPath, String fileName) throws IOException {
		android.content.res.AssetManager assetManager = getAssets();
		File outDir = new File(getApplicationContext().getFilesDir(), subPath);
		if(!outDir.exists())
         outDir.mkdir();
		// Copy the asset.
		copyFileToDataDir(assetManager, subPath + File.separator + fileName);
		}
	
	/**
	 * Copy the file using the asset manager.<br>
	 * 
	 * @param assetManager
	 * @param fileName Relative to assets folder.
	 * @throws IOException
	 */
	protected void copyFileToDataDir(android.content.res.AssetManager assetManager,
	                                    String fileName) throws IOException {
	   // Open the asset file.
	   InputStream in = assetManager.open(fileName);
	   // Open the target file.
	   File outFile = new File(getApplicationContext().getFilesDir(), fileName);
	   OutputStream out = new FileOutputStream(outFile);
	   // Actual copy.
	   streamCopy(in, out);
	   // Close everything.
	   in.close();
	   in = null;
	   out.flush();
	   out.close();
	   out = null;
	   }

	/**
	 * Copy from in stream to out stream.
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	protected void streamCopy(InputStream in, OutputStream out) throws IOException {
	   byte[] buffer = new byte[1024];
	   int read;
	   for(;(read = in.read(buffer)) != -1;)
	      out.write(buffer, 0, read);
	   }
	
	}
