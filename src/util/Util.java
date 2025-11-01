package util;
import java.util.Arrays;

public class Util {
    public static float[][] dotP(float[][] inputs, float[][] weights) throws Exception {
        float sum[][] = new float[inputs.length][weights.length];
        int iteration = 0;
        for(float[] in : inputs) {
        int iterationW = 0;
        for(float[] f : weights) {
            float smallSum = 0;
            if(f.length == in.length) {
                for(int i = 0; i < f.length; i++) {
                    smallSum += (f[i] * in[i]);
                }
            }
            else {
                throw new Exception("Size of weight array and input array does not match");
            }
            sum[iteration][iterationW] = smallSum;
            //System.out.println(smallSum);
            iterationW++;
        }
        iteration++;
    }
        return sum;

        
    }

    public static float[][] add(float[][] inputs, float[] biases) {
        float[][] sum = new float[inputs.length][biases.length];
        int iter = 0;
        for(float[] f : inputs) {
            float[] partsum = new float[f.length];
            for(int i = 0; i < f.length; i++) {
                partsum[i] = (f[i] + biases[i]);
            }
            sum[iter] = partsum;
            iter++;
        }
        return sum;
    }

    public static void printArr(float[][] arr) {
        System.out.println(Arrays.deepToString(arr));
    }
    public static void printArr(float[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static float max(float val1, float val2) {
        if(val1 > val2) {
            return val1;
        }
        else {
            return val2;
        }
    }


}
