package dk.escteam.streetviewquest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainFragment extends Fragment {
	
	
	MainActivity mainActivity;
	Location mCurrentLocation;
	TextView[] textViews;
	ImageView[] imageViews;
	boolean[] imageSet;
	
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MainFragment newInstance() {
        	MainFragment fragment = new MainFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 1);
            fragment.setArguments(args);
            return fragment;
        }
        
        public void onCreate(Bundle savedInstanceState){
        	mainActivity = (MainActivity) getActivity();
        	imageSet = new boolean[10];
        	super.onCreate(savedInstanceState);
        }
        
        public void onActivityCreated (Bundle savedInstanceState){
        	for(int i = 0; i < 10; i++){
        		if(mainActivity.fileExists("tempfile_" + i + ".jpeg"))
    			{
                	Bitmap tempfile = mainActivity.loadImage("tempfile_" + i);
                	if(tempfile != null)
                	{
                		imageViews[i].setImageBitmap(tempfile);
                		imageSet[i] = true;
                	}
    			}
            	
        	}
        	
        	super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	View rootView = inflater.inflate(R.layout.fragment_picture, container, false);
        	
        	LinearLayout lL = (LinearLayout) rootView.findViewById(R.id.fragment_picture_root);
        	
        	imageViews = new ImageView[10];
        	textViews = new TextView[10];
            for(int i = 0; i < 10; i++){
            	imageViews[i] = new ImageView(mainActivity);
            	lL.addView(imageViews[i]);
            	textViews[i] = new TextView(mainActivity);
            	lL.addView(textViews[i]);
            }
            return rootView;
        }
        
        public void onLocationConnect(){
        	mCurrentLocation = mainActivity.getLocation();
            if(mCurrentLocation != null)
            {
            	for(int i = 0; i < 10; i++){
            		if(!imageSet[i])
                   	{
            			int fov = (int) (Math.random() * 80 + 11);
           				int heading = (int) (Math.random() * 360 + 1);
            			int pitch = (int) (Math.random() * 20 - 9) ;
            							
            			textViews[i].setText("Location: " + mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude() + " fov=" + fov + " heading=" + heading + " pitch=" + pitch);
            			String imageURL = "http://maps.googleapis.com/maps/api/streetview?size=600x600&location=" + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude() + "&fov=" + fov + "&heading=" + heading + "&pitch=" + pitch + "&sensor=false&key=AIzaSyCx82Y4r6ybWjlOu20V3lcf1HCy6DGwRyA";
            			if(mainActivity.isOnline()){
            				new DownloadImageTask(this, i).execute(imageURL);
                        	imageSet[i] = true;
                        }
            		}
            	}
            }
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

		public void imageDownloaded(Bitmap result, int tag) {
			imageViews[tag].setImageBitmap(result);
			mainActivity.saveImage(result, "tempfile_" + tag);
		}
}
