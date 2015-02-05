package com.asyn.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FlashActivator extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TORCH_OFF = Parameters.FLASH_MODE_OFF;
	private static final String TORCH_ON = Parameters.FLASH_MODE_TORCH;
	
	private Camera camera;
	private SurfaceHolder mHolder;
	private SurfaceView preview;	
	private Parameters parameters;
	
	public FlashActivator(Context context, SurfaceView view) {
		super(context);
		preview = view;
		mHolder = preview.getHolder();
		camera = Camera.open();
		parameters = camera.getParameters();
		setFlashAndParametersMode(TORCH_OFF);
		camera.stopPreview();
	}
	
	public void on() {
		setFlashAndParametersMode(TORCH_ON);
		camera.startPreview();
		surfaceCreated(mHolder);
	} // end method torchOn
	
	public void off() {
		setFlashAndParametersMode(TORCH_OFF);
		camera.stopPreview();
	} // end method torchOff

	private void setFlashAndParametersMode(String status) {
		parameters.setFlashMode(status);
		camera.setParameters(parameters);
	}
	
	public void reInstateCamera() {
		if (isNull()) {
			camera = Camera.open();
			parameters = camera.getParameters();
		}
	}
	
	protected boolean isNull() {
		if(camera == null)
			return true;
		return false;
	}
	
	public void destroy() {
		if(camera!=null){
			camera.release();
			camera = null;
		}
			
	} // end method destroy

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mHolder = holder;
		try {
			camera.setPreviewDisplay(mHolder);
		} catch (Exception e) {
			// ERROR
		}
	} // end unimplemented method surfaceCreated

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// empty
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
	} // end unimplemented method surfaceDestroyed
	
	public static void setCameraDisplayOrientation(Activity activity,
	         int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =
	             new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay()
	             .getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     camera.setDisplayOrientation(result);
	 }
	
	public boolean checkCameraHardware(Context context) {
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
			// TODO open camera error
		}		
		return c;
	}

} // end class FlashActivator