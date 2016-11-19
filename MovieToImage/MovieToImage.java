package com.cooltrickshome;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;


public class MovieToImage {

	/**
	 * @param args
	 * @throws IOException 
	 */
	 public static void main(String []args) throws Exception, IOException
	    {
		 	Scanner s=new Scanner(System.in);
		 	System.out.println("Enter the path of mp4 (for eg c:\\test.mp4)");
		 	String mp4Path=s.nextLine();
		 	System.out.println("Enter the folder path where the images will be saved (eg c:\\convertedImages)");
		 	String imagePath=s.nextLine();
		 	convertMovietoJPG(mp4Path, imagePath,"jpg",0);
		 	System.out.println("Conversion complete. Please find the images at "+imagePath);
	    }
	 
	 public static void convertMovietoJPG(String mp4Path, String imagePath, String imgType, int frameJump) throws Exception, IOException
	 {
		 	Java2DFrameConverter converter = new Java2DFrameConverter();
	        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(mp4Path);
	        frameGrabber.start();
	        Frame frame;
        	double frameRate=frameGrabber.getFrameRate();
        	int imgNum=0;
        	System.out.println("Video has "+frameGrabber.getLengthInFrames()+" frames and has frame rate of "+frameRate);
	        
	        try {       	
	            for(int ii=1;ii<=frameGrabber.getLengthInFrames();ii++){
	            imgNum++;	
	            frameGrabber.setFrameNumber(ii);
	            frame = frameGrabber.grab();
	            BufferedImage  bi = converter.convert(frame);
	            String path = imagePath+File.separator+imgNum+".jpg";
	            ImageIO.write(bi,imgType, new File(path));
	            ii+=frameJump;
	            }
	            frameGrabber.stop();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
