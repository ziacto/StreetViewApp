package dk.escteam.streetviewquest;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	MainFragment returnFragment;
	QuestPoint returnQuestPoint;
	int returnTag;

	  public DownloadImageTask(MainFragment Fragment, int tag) {
	      this.returnFragment = Fragment;
	      this.returnTag = tag;
	  }

	  public DownloadImageTask(QuestPoint questPoint) {
		  this.returnQuestPoint = questPoint;
	}

	protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          Log.e("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
		  if(returnQuestPoint != null){
			  returnQuestPoint.imageDownloaded(result);
		  }else if(returnFragment != null){
			  returnFragment.imageDownloaded(result, returnTag);
		  }
	      //bmImage.setImageBitmap(result);
	  }
	}
