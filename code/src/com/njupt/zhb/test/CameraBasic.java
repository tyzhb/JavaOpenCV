package com.njupt.zhb.test;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
/**
 * 动态人脸检测并裁剪
 * @author hyj
 *
 */
public class CameraBasic {
    static {
    	System.out.println(System.getProperty("java.library.path"));  
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private JFrame frame;
    private static JLabel label;
    private static int flag = 0;
    private static int checkFlag = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    CameraBasic window = new CameraBasic();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        VideoCapture camera = new VideoCapture();//创建Opencv中的视频捕捉对象
        camera.open(0);//open函数中的0代表当前计算机中索引为0的摄像头，如果你的计算机有多个摄像头，那么一次1,2,3……
        CascadeClassifier faceDetector = new CascadeClassifier("src/com/njupt/zhb/test/haarcascade_frontalface_alt.xml");
        MatOfRect faceDetections = null;
        Rect [] rectArray = null;
        if (!camera.isOpened()) {//isOpened函数用来判断摄像头调用是否成功
            System.out.println("Camera Error");//如果摄像头调用失败，输出错误信息
        } else {
            Mat frame = new Mat();//创建一个输出帧
            while (flag == 0) {
                camera.read(frame);//read方法读取摄像头的当前帧
//                CascadeClassifier faceDetector = new CascadeClassifier("src/com/njupt/zhb/test/lbpcascade_frontalface.xml");
	                faceDetections = new MatOfRect();
	                faceDetector.detectMultiScale(frame, faceDetections);
	                rectArray = faceDetections.toArray();
	                if (rectArray.length > 0) {
	                    
	                    for (int i=0;i<rectArray.length;i++) {
	                    	Rect rect = rectArray[i];
	                        Rect  rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
	                        if (rect.width + rect.height > rectCrop.height + rectCrop.width) {
	                            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
	                        }
	                        	System.out.println(String.format("检测到 %s 个人脸！ ", rectArray.length));
	                        	//Mat imageRoi = new Mat(frame, rectCrop);
	                        	//String name = System.currentTimeMillis()+".png";
	                        	//Highgui.imwrite(name, imageRoi);
	                        	
	                        
	                        Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 2);
	                    }
	                }

                //转换图像格式并输出
                label.setIcon(new ImageIcon(mat2BufferedImage.matToBufferedImage(frame)));

//                try {
//                    //Thread.sleep(10);//线程暂停500ms
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }

                
                
                
//                if (faceCount > 0) {
//                    faceSerialCount++;
//                    System.out.println(faceSerialCount);
//                } else {
//                    faceSerialCount = 0;
//                }
//
//                if (faceSerialCount > 6) {
//                    Mat imageRoi = new Mat(frame, rectCrop);
//                    Highgui.imwrite("haha.png", imageRoi);
//                    faceSerialCount = 0;
//                }
            }
        }
    }

    private CameraBasic() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        label = new JLabel("");
        label.setBounds(0, 0, 1000, 500);
        frame.getContentPane().add(label);
    }
    
    
}
