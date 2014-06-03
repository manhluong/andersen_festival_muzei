package com.luongbui.andersen_festival_muzei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.MuzeiArtSource;

public class AndersenFestivalSource extends MuzeiArtSource {
	
	private static final String FILE_PROVIDER_AUTHORITIES = "com.luongbui.andersen_festival_muzei.fileprovider";
	
	private static final String PORTRAITS_SUBDIR = "andersen_portraits_2014"; 
	
	private static final String[][] PORTRAITS = {
		{"anais_tonelli.jpg", "Hans Christian Andersen", "Anais Tonelli, 2014", "http://anaistonelli.blogspot.it/"}
		};
	
	private Uri fileUri;

	public AndersenFestivalSource() {
		super("com.luongbui.andersen_festival_muzei.AndersenFestivalSource");
		}
	
	@Override
    protected void onSubscriberAdded(ComponentName subscriber) {
        super.onSubscriberAdded(subscriber);
        android.util.Log.d("onSubscriberAdded()", "onSubscriberAdded()");
        File sharedFile = new File(getApplicationContext().getFilesDir(),
									PORTRAITS_SUBDIR + File.separator + PORTRAITS[0][0]);
        fileUri = FileProvider.getUriForFile(getApplicationContext(),
												FILE_PROVIDER_AUTHORITIES,
												sharedFile);
        getApplicationContext().grantUriPermission(subscriber.getPackageName(),
													fileUri,
													Intent.FLAG_GRANT_READ_URI_PERMISSION);
    	}
	
	@Override
    protected void onSubscriberRemoved(ComponentName subscriber) {
        super.onSubscriberRemoved(subscriber);
        android.util.Log.d("onSubscriberRemoved()", "onSubscriberRemoved()");
    	}

	@Override
	protected void onUpdate(int reason) {
		try {
			android.util.Log.d("onUpdate()", "onUpdate()");
			//For now, empty the external sub dir each time: TODO a more "flexible" cache system.
			deleteExternalSubdir(new File(getApplicationContext().getFilesDir(),
		                                  PORTRAITS_SUBDIR));
			//TODO test if the file exits, should be false.
			File outFile = new File(getApplicationContext().getFilesDir(), PORTRAITS_SUBDIR + File.separator + PORTRAITS[0][0]);
			android.util.Log.d("TEST FILE EXISTS", ""+outFile.exists()+" : " + outFile.getPath());
			//TODO copy a random file.
			copyAsset(PORTRAITS_SUBDIR, PORTRAITS[0][0]);
			//TODO test if the file exits, should be true.
			android.util.Log.d("TEST FILE EXISTS", ""+outFile.exists()+" : " + outFile.getPath());
			
			android.util.Log.d("SHARED FILE URI", ""+fileUri.toString());
			publishArtwork(new Artwork.Builder()
								.imageUri(fileUri)
								.title(PORTRAITS[0][1])
								.byline(PORTRAITS[0][2])
								.viewIntent(new Intent(Intent.ACTION_VIEW,
										Uri.parse(PORTRAITS[0][3])))
								.build());
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
	 * From here: http://stackoverflow.com/questions/4447477/android-how-to-copy-files-in-assets-to-sdcard
	 * 
	 * @param subPath A relative path within the assets. Can be a directory: "andersen_portraits".
	 * @param fileName Name of file to copy, relative to subPath.
	 * @throws IOException
	 */
	protected void copyAsset(String subPath, String fileName) throws IOException {
		android.content.res.AssetManager assetManager = getAssets();
		String[] files = assetManager.list(subPath);
		if (files.length == 0) // It's a file.
		   throw new IOException("subPath of copyAssets() is not a dir.");
		else {
		   // Create the new output directory to store the copied files.
		   File outDir = new File(getApplicationContext().getFilesDir(), subPath);
		   if(!outDir.exists())
		      outDir.mkdir();
		   // Copy the asset.
		   // From Android doc, each element of files[] is relative to "subPath".
		   copyFileToDataDir(assetManager, subPath + File.separator + fileName);
		   }
		}
	
	/**
	 * From here: http://stackoverflow.com/questions/4447477/android-how-to-copy-files-in-assets-to-sdcard
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
	 * From here: http://stackoverflow.com/questions/4447477/android-how-to-copy-files-in-assets-to-sdcard
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	protected void streamCopy(InputStream in, OutputStream out) throws IOException {
	   // Transfer chunks of 1024 bytes.
	   byte[] buffer = new byte[1024];
	   int read;
	   // Transfer between streams.
	   for(;(read = in.read(buffer)) != -1;)
	      out.write(buffer, 0, read);
	   }
	
	}
