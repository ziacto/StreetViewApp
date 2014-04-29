package dk.escteam.streetviewquest;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment {
	
	
	MainActivity mainActivity;
	Location mCurrentLocation;
	TextView textView;
	ImageView imageView;
	
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_picture, container, false);
            textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("This is some Text");
            
            mainActivity = (MainActivity) getActivity();

            imageView = (ImageView)rootView.findViewById(R.id.imageView1);
                       
            return rootView;
        }
        
        public void onLocationConnect(){
        	mCurrentLocation = mainActivity.getLocation();
            if(mCurrentLocation != null)
            {
            	int fov = (int) (Math.random() * 80 + 11);
            	int heading = (int) (Math.random() * 360 + 1);
            	int pitch = (int) (Math.random() * 20 - 9) ;
            	
            	textView.setText("Location: " + mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude() + " fov=" + fov + " heading=" + heading + " pitch=" + pitch);

            
            	String imageURL = "http://maps.googleapis.com/maps/api/streetview?size=600x600&location=" + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude() + "&fov=" + fov + "&heading=" + heading + "&pitch=" + pitch + "&sensor=false&key=AIzaSyCx82Y4r6ybWjlOu20V3lcf1HCy6DGwRyA";
            	if(mainActivity.isOnline()){
            		new DownloadImageTask(imageView).execute(imageURL);
            	}
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
}
