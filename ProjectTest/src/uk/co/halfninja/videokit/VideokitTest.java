package uk.co.halfninja.videokit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Environment;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class VideokitTest extends InstrumentationTestCase {

	Videokit vk = new Videokit();
	
    public VideokitTest() {
    }

    
    public void testEncode() throws Exception {  		
		File images = new File(Environment.getExternalStorageDirectory(), "fun");
		images.mkdirs();
		
		for (int i=0; i<10; i++) {
			String filename = String.format("snap%04d.jpg", i);
			File dest = new File(images, filename);
			Log.i("Test", "Adding image at " + dest.getAbsolutePath());
			InputStream is = getInstrumentation().getContext().getAssets().open("image.jpg");
			BufferedOutputStream o = null;
			try {
				byte[] buff = new byte[10000];
				int read = -1;
				o = new BufferedOutputStream(new FileOutputStream(dest), 10000);
				while ((read = is.read(buff)) > -1) { 
					o.write(buff, 0, read);
				}
			} finally {
				is.close();
				if (o != null) o.close();  
				
			}
		}
		
		
		String filename = "explode.mp4";
		File dest = new File(images, filename);
		Log.i("Test", "Adding image at " + dest.getAbsolutePath());
		InputStream is = getInstrumentation().getContext().getAssets().open("orig.mp4");
		BufferedOutputStream o = null;
		try {
			byte[] buff = new byte[10000];
			int read = -1;
			o = new BufferedOutputStream(new FileOutputStream(dest), 10000);
			while ((read = is.read(buff)) > -1) { 
				o.write(buff, 0, read);
			}
		} finally {
			is.close();
			if (o != null) o.close();  
			
		}
		
		//videokit.initialise();
		
		
		
		
		File file = new File(images.getAbsolutePath(), "explode.mp4");
		assertTrue("File exist", file.exists());
		
		String input = file.getAbsolutePath(); 
		Log.i("Test", "Let's set input to " + input);
//		videokit.setInputFile(input); 
		String output = new File(Environment.getExternalStorageDirectory(), "video.3gp").getAbsolutePath();
		Log.i("Test", "Let's set output to " + output);
//		videokit.setOutputFile(output); 
		
		vk.run(new String[]{
				"ffmpeg",
				"-i", input, 
				//"-s" ,"480x320" 
				//"-vcodec" ,"mpeg4"  ,"-ac" ,"1" ,"-ar", "16000" ,"-r" ,"13" ,"-ab", "32000", "-y"
				
				"-ss", "00:00:05.00"
				,"-t" , "00:00:06.00"
				,"-vcodec", "copy" 
				,"-acodec" ,"copy"
				, "-y",  output
		});
		
//		videokit.setSize(640,480); 
//		videokit.setFrameRate(5);
//		
//		videokit.encode();
	}


}
