package me.jessyan.armscomponent.commonres.utils.video;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import java.util.Comparator;
import java.util.List;

public class Utils {

	private Utils() {
	}

/*
	@SuppressLint("NewApi")
	public static void enableStrictMode() {
		if(ARouterUtils.hasGingerbread())
		{
			StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog();

            if (ARouterUtils.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
                vmPolicyBuilder
                        .setClassInstanceLimit(ImageGridActivity.class, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
		}

		
		
		
		
	}
*/

	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;

	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasKitKat() {
		return Build.VERSION.SDK_INT >= 19;
	}

	public static List<Size> getResolutionList(Camera camera)
	{
		Parameters parameters = camera.getParameters();
		return parameters.getSupportedPreviewSizes();
	}

	public static class ResolutionComparator implements Comparator<Size> {

		@Override
		public int compare(Size lhs, Size rhs) {
			if(lhs.height!=rhs.height)
			return lhs.height-rhs.height;
			else
			return lhs.width-rhs.width;
		}
		 
	}
	
	
	
	
}
