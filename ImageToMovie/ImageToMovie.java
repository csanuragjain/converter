package com.cooltrickshome;

import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class ImageToMovie {

	/**
	 * @param args
	 * @throws IOException 
	 */
	 public static void main(String []args)
	    {
		 	Scanner s=new Scanner(System.in);
		 	System.out.println("Enter the directory path of images (for eg c:\\test)");
		 	String imgPath=s.nextLine();
		 	System.out.println("Enter the directory with video file name where resulting video will be saved (for eg c:\\test\\abc.mp4)");
		 	String vidPath=s.nextLine();
		 	
		 	ArrayList<String> links = new ArrayList<>();
		 	File f=new File(imgPath);
		 	File[] f2=f.listFiles();
		 	for(File f3:f2)
		 	{
		 		links.add(f3.getAbsolutePath());
		 	}
		 	convertJPGtoMovie(links, vidPath);
		 	System.out.println("Video has been created at "+vidPath);
		 	s.close();
	    }
	 
	 public static void convertJPGtoMovie(ArrayList<String> links, String vidPath)
	 {
		 OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
		 FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath,640,720);
		 try {
			 recorder.setFrameRate(1);
             recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
             recorder.setVideoBitrate(9000);
             recorder.setFormat("mp4");
             recorder.setVideoQuality(0); // maximum quality
             recorder.start();
		     for (int i=0;i<links.size();i++)
		     {
		        recorder.record(grabberConverter.convert(cvLoadImage(links.get(i))));
		     }
		     recorder.stop();
		    }
		    catch (org.bytedeco.javacv.FrameRecorder.Exception e){
		       e.printStackTrace();
		    }
	 }
}
