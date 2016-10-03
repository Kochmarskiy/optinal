package hist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Histogram {
    private static final int width=255*3;
    private static final int height = 500;
    private static final int backgroundColor=15_000_000;
    private static final int basicColor = 1;

    private static int[] getRangeOfBrightness(String pathToFile){
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] pixels = new int[256];
        for(int i=0;i<bufferedImage.getWidth();i++){
            for(int j =0;j<bufferedImage.getHeight();j++){
                int color = bufferedImage.getRGB(i,j);
                int red   = (color >>> 16) & 0xFF;
                int green = (color >>>  8) & 0xFF;
                int blue  = (color >>>  0) & 0xFF;
                float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f);
                pixels[Math.round(luminance)]++;
            }
        }
        return pixels;
    }
    private static  void paintBackground(BufferedImage bufferedImage){
        for(int i=0;i<bufferedImage.getWidth();i++){
            for(int j=0;j<bufferedImage.getHeight();j++)
                bufferedImage.setRGB(i,j,backgroundColor);
        }
    }
    private static boolean isFit(String command){
        Pattern p = Pattern.compile("^imhist\\s+-i\\s+\\S+\\s+-o\\s+\\S+");
        Matcher m = p.matcher(command);
        return m.matches();
    }
    public static void main(String[] arhs) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        if(!isFit(command)){
            System.out.println("Wrong command");
            System.exit(0);
        }
        String[] s = command.split("-i")[1].split("-o");
        String inputFile = s[0].trim();
        String outputFile =s[1].trim();
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        paintBackground(bufferedImage);
        int[] pixels = getRangeOfBrightness(inputFile);
        int max = -1;
        int sum=0;
        for(int i=0;i<pixels.length;i++)
        {
            if(pixels[i]>max) max=pixels[i];
            sum+=pixels[i];
        }
        for(int i=0;i<width;i+=3) {
            for (int k = i; k < i + 3 && k < width; k++)
                for (int j = 1; j < pixels[i / 3] * height / max; j++) {
                    bufferedImage.setRGB(k, height - j, 1);
                }
        }
        File file = new File(outputFile);
        ImageIO.write(bufferedImage,"png",file);
    }
}
