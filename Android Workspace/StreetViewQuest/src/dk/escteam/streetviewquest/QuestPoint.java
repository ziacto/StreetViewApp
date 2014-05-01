package dk.escteam.streetviewquest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class QuestPoint {
	Bitmap sourceImage;
	double sourceLatitude;
	double sourceLongitude;
	int sourceFov;
	int sourceHeading;
	int sourcePitch;
	
	public static QuestPoint newInstance(MainActivity activity, double latitude, double longitude){
		QuestPoint questPoint = new QuestPoint();
		questPoint.sourceLatitude = latitude;
		questPoint.sourceLongitude = longitude;
		
		questPoint.sourceFov = (int) (Math.random() * 80 + 11);
		questPoint.sourceHeading = (int) (Math.random() * 360);
		questPoint.sourcePitch = (int) (Math.random() * 20 - 9) ;
		
		String imageURL = "http://maps.googleapis.com/maps/api/streetview?size=600x600&location=" + latitude + "," + longitude + "&fov=" + questPoint.sourceFov + "&heading=" + questPoint.sourceHeading + "&pitch=" + questPoint.sourcePitch + "&sensor=false&key=AIzaSyCx82Y4r6ybWjlOu20V3lcf1HCy6DGwRyA";
		if(activity.isOnline()){
			new DownloadImageTask(questPoint).execute(imageURL);
        }
		
		return questPoint;
	}

	public void imageDownloaded(Bitmap result) {
		sourceImage = result;
	}
	
	public boolean saveAsFile(Activity activity, String filename){
		ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
		File directory = cw.getFilesDir();
		File mypath = new File(directory, filename + ".escqp");

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(mypath);
			
			fos.write(sourceFov);
			fos.write(sourceHeading);
			fos.write(sourcePitch);
			
			byte[] output = new byte[8];
			ByteBuffer.wrap(output).putDouble(sourceLatitude);
			fos.write(output);
			ByteBuffer.wrap(output).putDouble(sourceLongitude);
			fos.write(output);
			
			sourceImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static QuestPoint loadFromFile(Activity activity, String filename) {
		QuestPoint questPoint = new QuestPoint();
		
		ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
		File directory = cw.getFilesDir();
		File mypath = new File(directory, filename + ".escqp");
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(mypath);
			
			questPoint.sourceFov = fis.read();
			questPoint.sourceHeading = fis.read();
			questPoint.sourcePitch = fis.read();
			
			byte[] input = new byte[8];
			fis.read(input);
			questPoint.sourceLatitude = ByteBuffer.wrap(input).getDouble();
			fis.read(input);
			questPoint.sourceLongitude = ByteBuffer.wrap(input).getDouble();
			
			questPoint.sourceImage = BitmapFactory.decodeStream(fis);
			fis.close();
	        return questPoint;
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        return null;
	    }
	}
}
