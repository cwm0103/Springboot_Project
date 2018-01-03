//package com.bom.utils.imagehelper;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import sun.awt.image.ImageFormatException;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//
///**
// * 图片压缩类
// * @Autor   chenwangming
// * Created by chenwangming on 2017/8/3.
// */
//public class ImageHelper {
//    private String srcFile;//源图片地址
//    private String destFile;//目标图片地址
//    private int width;//图片宽度
//    private int height;//图片高度
//    private Image img;//图片
//    public String getSrcFile() {
//        return srcFile;
//    }
//    public void setSrcFile(String srcFile) {
//        this.srcFile = srcFile;
//    }
//    public String getDestFile() {
//        return destFile;
//    }
//    public void setDestFile(String destFile) {
//        this.destFile = destFile;
//    }
//    public int getWidth() {
//        return width;
//    }
//    public void setWidth(int width) {
//        this.width = width;
//    }
//    public int getHeight() {
//        return height;
//    }
//    public void setHeight(int height) {
//        this.height = height;
//    }
//    public Image getImg() {
//        return img;
//    }
//    public void setImg(Image img) {
//        this.img = img;
//    }
//
//    /**
//     * 构造函数
//     * @param srcFile
//     * @param destFile
//     * @throws IOException
//     */
//    public ImageHelper(String srcFile,String destFile) throws IOException
//    {
//        this.srcFile=srcFile;
//        this.destFile=destFile;
//        img = ImageIO.read(new File(srcFile));
//        width=img.getWidth(null);
//        height=img.getHeight(null);
//    }
//    /**
//     * 压缩/放大图片到固定的大小
//     * @param newWidth
//     * @param newHeight
//     * @throws ImageFormatException
//     * @throws IOException
//     */
//    public void resize(int newWidth,int newHeight) throws  IOException
//    {
//        Image img=Toolkit.getDefaultToolkit().getImage(srcFile);
//        BufferedImage bfi=toBufferedImage(img,newWidth,newHeight);
//        //第一种通过文件流和JPEGImageEncoder近JPEg编码输出
//        FileOutputStream newImageOPS = new FileOutputStream(destFile);//输出文件流
//          /*
//          * JPEGImageEncoder 将图像缓冲数据编码为 JPEG 数据流。该接口的用户应在 Raster
//          * 或 BufferedImage 中提供图像数据，在 JPEGEncodeParams 对象中设置必要的参数，
//          * 并成功地打开 OutputStream（编码 JPEG 流的目的流）。JPEGImageEncoder 接口可
//          * 将图像数据编码为互换的缩略 JPEG 数据流，该数据流将写入提供给编码器的 OutputStream 中。
//                               注意：com.sun.image.codec.jpeg 包中的类并不属于核心 Java API。它们属于 Sun 发布的
//          JDK 和 JRE 产品的组成部分。虽然其它获得许可方可能选择发布这些类，但开发人员不能寄
//                               希望于从非 Sun 实现的软件中得到它们。我们期望相同的功能最终可以在核心 API 或标准扩
//                               展中得到。
//          */
//        com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(newImageOPS);
//        encoder.encode(bfi);//近JPEG编码
//        newImageOPS.close();
//
//        //第二种通过图片流写入
//        //ImageIO.write(bfi, "JPEG", new File(destFile));
//    }
//
//    /**
//     * 生成图片缓存区
//     * @param image
//     * @param width
//     * @param height
//     * @return
//     */
//    public static BufferedImage toBufferedImage(Image image,int width,int height) {
//
//        if (image instanceof BufferedImage) {
//            return (BufferedImage)image;
//        }
//
//        // This code ensures that all the pixels in the image are loaded
//        image = new ImageIcon(image).getImage();
//
//        // Determine if the image has transparent pixels; for this method's
//        // implementation, see e661 Determining If an Image Has Transparent Pixels
//        //boolean hasAlpha = hasAlpha(image);
//
//        // Create a buffered image with a format that's compatible with the screen
//        BufferedImage bimage = null;
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//
//        try {
//            // Determine the type of transparency of the new buffered image
//            int transparency = Transparency.OPAQUE;
//           /* if (hasAlpha) {
//                transparency = Transparency.BITMASK;
//            }*/
//
//            // Create the buffered image
//            GraphicsDevice gs = ge.getDefaultScreenDevice();
//            GraphicsConfiguration gc = gs.getDefaultConfiguration();
//            bimage = gc.createCompatibleImage(width, height, transparency);
//        } catch (HeadlessException e) {
//            // The system does not have a screen
//        }
//
//        if (bimage == null) {
//            // Create a buffered image using the default color model
//            int type = BufferedImage.TYPE_INT_RGB;
//            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
//            /*if (hasAlpha) {
//                type = BufferedImage.TYPE_INT_ARGB;
//            }*/
//
//            bimage = new BufferedImage(width, height, type);
//        }
//
//        // Copy image to buffered image
//        Graphics g = bimage.createGraphics();
//        // Paint the image onto the buffered image
//        g.drawImage(image, 0, 0,width,height, null);////绘制缩小的图
//        g.dispose();
//        return bimage;
//    }
//
//    /**
//     * 按照固定的比例缩放图片
//     * @param t
//     * @throws IOException
//     */
//    public void resize(double t) throws IOException{
//        int w = (int)(width*t);
//        int h = (int)(height*t);
//        resize(w,h);
//    }
//
//    /**
//     * 已宽度为基准，等比例缩放图片
//     * @param newWidth
//     * @throws IOException
//     */
//    public void resizeByWidth(int newWidth) throws IOException{
//        int h = (int)(height*(new Double(newWidth)/width));
//        resize(newWidth,h);
//    }
//
//    /**
//     * 以高度为基准，等比例缩放图片
//     * @param newHeight
//     * @throws IOException
//     */
//    public void resizeByHeight(int newHeight) throws IOException{
//
//        int w = (int)(width*(new Double(newHeight)/height));
//        resize(w,newHeight);
//    }
//
//    /**
//     * 生成规格
//     * @throws IOException
//     */
//    public void resizeFix(int newWidth,int newHeight) throws IOException{
//
//        if(width>height || (width/height>newWidth/newHeight)){
//            resizeByWidth(newWidth);
//        }else{
//            resizeByHeight(newHeight);
//        }
//    }
//
//    /**
//     * 添加图片水印
//     * @param targetImg 目标图片路径，如：D:/eclipsetest/old/1.jpg
//     * @param waterImg  水印图片路径，如：D:/eclipsetest/old/水印图片.png
//     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
//     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
//     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
//     * @throws Exception
//     */
//    public static void pressImage(String targetImg,String waterImg,int x,int y,float alpha) throws Exception{
//        try {
//            File file = new File(targetImg);
//            Image image = ImageIO.read(file);
//            int width = image.getWidth(null);
//            int height = image.getHeight(null);
//            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = bufferedImage.createGraphics();
//            g.drawImage(image, 0, 0, null);
//
//            Image waterImage = ImageIO.read(new File(waterImg));//水印文件
//            int width_wi=waterImage.getWidth(null);
//            int height_wi=waterImage.getHeight(null);
//
//            if(width<=width_wi || height<=height_wi){
//                throw new Exception("原图的宽、高必须大于水印图的宽、高");
//            }
//
//            AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
//
//            int widthDiff = width-width_wi;
//            int heightDiff = height-height_wi;
//
//            if(x<0){
//                x = widthDiff/2;
//            }else if(x>widthDiff){
//                x = widthDiff;
//            }
//
//            if(y<0){
//                y = heightDiff/2;
//            }else if(y>heightDiff){
//                y = heightDiff;
//            }
//            g.drawImage(waterImage, x, y, width_wi,height_wi,null);//水印文件结束
//            g.dispose();
//            ImageIO.write(bufferedImage, "JPEG", file);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 添加文字水印
//     * @param targetImg 目标图片路径，如：D:/eclipsetest/old/1.jpg
//     * @param pressText 水印文字， 如：中国证券网
//     * @param fontName 字体名称，    如：宋体
//     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
//     * @param fontSize 字体大小，单位为像素
//     * @param color 字体颜色
//     * @param x 水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
//     * @param y 水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
//     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
//     */
//    public static void pressText(String targetImg,String pressText,String fontName,int fontStyle,int fontSize,Color color,int x,int y,float alpha){
//        try {
//            File file = new File(targetImg);
//            Image image = ImageIO.read(file);
//            int width = image.getWidth(null);
//            int height = image.getHeight(null);
//
//            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = bufferedImage.createGraphics();
//            g.drawImage(image,0,0, width, height, null);
//            g.setFont(new Font(fontName, fontStyle, fontSize));
//            g.setColor(color);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
//
//            int width_wi = fontSize*getTextLength(pressText);
//            int height_wi = fontSize;
//
//            int widthDiff = width-width_wi;
//            int heightDiff = height-height_wi;
//            if(x<0){
//                x = widthDiff/2;
//            }else if(x>widthDiff){
//                x=widthDiff;
//            }
//
//            if(y<0){
//                y = heightDiff/2;
//            }else if(y>heightDiff){
//                y = heightDiff;
//            }
//            g.drawString(pressText, x, y+height_wi);//水印文件
//            g.dispose();
//            ImageIO.write(bufferedImage, "JPEG", file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 计算文字像素长度
//     * @param text
//     * @return
//     */
//    private static int getTextLength(String text){
//        int textLength = text.length();
//        int length = textLength;
//        for (int i = 0; i < textLength; i++) {
//            int wordLength = String.valueOf(text.charAt(i)).getBytes().length;
//            if(wordLength > 1){
//                length+=(wordLength-1);
//            }
//        }
//        return length%2==0 ? length/2:length/2+1;
//    }
//
//
//    /**
//     * 旋转任意度数的方法
//     * @param targetImg
//     * @param degree
//     * @param bgcolor
//     * @throws IOException
//     */
//    public static void rotateImage(String targetImg, int degree, Color bgcolor) throws IOException {
//
//        File file = new File(targetImg);
//        BufferedImage sourceImage = ImageIO.read(file);
//        int iw = sourceImage.getWidth();//原始图象的宽度
//        int ih = sourceImage.getHeight();//原始图象的高度
//        int w = 0;
//        int h = 0;
//        int x = 0;
//        int y = 0;
//        degree = degree % 360;
//        if (degree < 0)
//            degree = 360 + degree;//将角度转换到0-360度之间
//        double ang = Math.toRadians(degree);//将角度转为弧度
//
//        /**
//         *确定旋转后的图象的高度和宽度
//         */
//
//        if (degree == 180 || degree == 0 || degree == 360) {
//            w = iw;
//            h = ih;
//        } else if (degree == 90 || degree == 270) {
//            w = ih;
//            h = iw;
//        } else {
//            int d = iw + ih;
//            w = (int) (d * Math.abs(Math.cos(ang)));
//            h = (int) (d * Math.abs(Math.sin(ang)));
//        }
//
//        x = (w / 2) - (iw / 2);//确定原点坐标
//        y = (h / 2) - (ih / 2);
//        BufferedImage rotatedImage = new BufferedImage(w, h, sourceImage.getType());
//        Graphics2D gs = (Graphics2D)rotatedImage.getGraphics();
//        if(bgcolor==null){
//            rotatedImage  = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
//        }else{
//            gs.setColor(bgcolor);
//            gs.fillRect(0, 0, w, h);//以给定颜色绘制旋转后图片的背景
//        }
//        //有两种旋转使用方式，第一使用AffineTransformOp，第二使用Graphics2D
//        /*
//        * AffineTransform at = new AffineTransform();
//        at.rotate(ang, w / 2, h / 2);//旋转图象
//        at.translate(x, y);
//        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
//        op.filter(sourceImage, rotatedImage);
//        sourceImage = rotatedImage;
//        ImageIO.write(sourceImage, "PNG", file);//这里的格式化请使用PNG格式，否则旋转后会出现红眼效果
//        */
//        BufferedImage bufferedImage = new BufferedImage(w, h, sourceImage.getType());
//        Graphics2D g = bufferedImage.createGraphics();
//        if(bgcolor==null){
//            g.setColor(Color.WHITE);
//        }else{
//            g.setColor(bgcolor);
//        }
//        g.fillRect(0, 0, w, h);//以给定颜色绘制旋转后图片的背景
//        g.rotate(Math.toRadians(degree), w/2, h/2);
//        g.translate(x, y);
//        g.drawImage(sourceImage, 0, 0, null);
//        g.dispose();
//        ImageIO.write(bufferedImage, "JPEG", file);//这里的JPEG也可以是PNG
//    }
//
//
//    /**
//     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
//     * @param imgsrc 源图片地址
//     * @param imgdist 目标图片地址
//     * @param widthdist 压缩后图片宽度（当rate==null时，必传）
//     * @param heightdist 压缩后图片高度（当rate==null时，必传）
//     * @param rate 压缩比例
//     */
//    public static void reduceImg(String imgsrc, String imgdist, int widthdist,
//                                 int heightdist, Float rate) {
//        try {
//            File srcfile = new File(imgsrc);
//            // 检查文件是否存在
//            if (!srcfile.exists()) {
//                return;
//            }
//            // 如果rate不为空说明是按比例压缩
//            if (rate != null && rate > 0) {
//                // 获取文件高度和宽度
//                int[] results = getImgWidth(srcfile);
//                if (results == null || results[0] == 0 || results[1] == 0) {
//                    return;
//                } else {
//                    widthdist = (int) (results[0] * rate);
//                    heightdist = (int) (results[1] * rate);
//                }
//            }
//            // 开始读取文件并进行压缩
//            Image src = javax.imageio.ImageIO.read(srcfile);
//            BufferedImage tag = new BufferedImage((int) widthdist,
//                    (int) heightdist, BufferedImage.TYPE_INT_RGB);
//
//            tag.getGraphics().drawImage(
//                    src.getScaledInstance(widthdist, heightdist,
//                            Image.SCALE_SMOOTH), 0, 0, null);
//
//            FileOutputStream out = new FileOutputStream(imgdist);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(tag);
//            out.close();
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /** 获取图片宽度
//     *
//     * @param file
//     *            图片文件
//     * @return 宽度
//     */
//    public static int[] getImgWidth(File file) {
//        InputStream is = null;
//        BufferedImage src = null;
//        int result[] = { 0, 0 };
//        try {
//            is = new FileInputStream(file);
//            src = javax.imageio.ImageIO.read(is);
//            result[0] = src.getWidth(null); // 得到源图宽
//            result[1] = src.getHeight(null); // 得到源图高
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//}
