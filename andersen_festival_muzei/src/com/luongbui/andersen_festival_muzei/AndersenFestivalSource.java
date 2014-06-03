package com.luongbui.andersen_festival_muzei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.net.Uri;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.MuzeiArtSource;

public class AndersenFestivalSource extends MuzeiArtSource {

	public AndersenFestivalSource() {
		super("com.luongbui.andersen_festival_muzei.AndersenFestivalSource");
		}

	@Override
	protected void onUpdate(int reason) {
		//assets/pure_data/filename.jpg
		//loadAssets("pure_data", "filename.jpg");
			//File dir = context.getFilesDir();
	    	//dir = new File(dir.getAbsoluteFile() + File.separator + assetsSubDir);
	    	//dir.mkdir();
			//File patchFile = new File(dir, fileName);
		//TODO better call copyFileToDataDir() to copy single file.
		copyAssets("assetsSubDir");
		/*
		publishArtwork(new Artwork.Builder()
        					.imageUri(Uri.parse("file:///android_asset/anais_tonelli.jpg"))
        					.title("Hans Christian Andersen")
        					.byline("Anais Tonelli, 2014")
        					.viewIntent(new Intent(Intent.ACTION_VIEW,
        									Uri.parse("http://anaistonelli.blogspot.it/")))
        					.build());*/
		}

	/**
	 * From here: http://stackoverflow.com/questions/4447477/android-how-to-copy-files-in-assets-to-sdcard
	 * 
	 * @param subPath A relative path within the assets. Can be a directory: "andersen_portraits".
	 * @throws IOException
	 */
	protected void copyAssets(String subPath) throws IOException {
		android.content.res.AssetManager assetManager = getAssets();
		String[] files = assetManager.list(subPath);
		if (files.length == 0) // It's a file.
		   copyFileToDataDir(assetManager, subPath);
		else {
		   // Create the new output directory to store the copied files.
		   File outDir = new File(getApplicationContext().getFilesDir(), subPath);
		   if(!outDir.exists())
		      outDir.mkdir();
		   // Copy each asset.
		   // From Android doc, each element of files[] is relative to "subPath".
		   for(String fileName : files) {
		      copyAssets(subPath + File.separator + fileName);
			   }
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
