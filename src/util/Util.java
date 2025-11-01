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

    public static float sum(float[] arr) {
        float sum = 0;
        for(float f : arr) {
            sum += f;
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

    public static float max(float[] vals) {
        float temp = -99999999;
        for(float f : vals) {
            if(f > temp) {
                temp = f;
            }
        }
        return temp;
    }

    public static float[] calcLoss(float[][] probs, int[] expectedClassIndex) {
        float[] loss = new float[expectedClassIndex.length];
        int iter = 0;
        for(float[] f : probs) {
            int index = expectedClassIndex[iter];
            loss[iter] = (float) -Math.log(f[index]);
            iter++;
        }

        return loss;
    }

    public static float[] calcLoss(float[][] probs, float[][] expected) {
        float[] loss = new float[probs.length];
        float offset = 0.0000001f;
        int iter = 0;
        for(int i = 0; i < probs.length; i++) {
            float sum = 0;
            float[] probBat = probs[i];
            float[] expBat = expected[i];
            for(int j = 0; j < probBat.length; j++) {
                float infCheck = probBat[j];
                //infCheck = max(offset, infCheck - offset);
                if(infCheck <= 0) {
                    infCheck += offset;
                }
                else if (infCheck >= 1) {
                    infCheck -= offset;
                }
                if(infCheck * expBat[j] == 0) {
                    sum += 0;
                }
                else {
                    sum += Math.log(infCheck * expBat[j]);
                }
            }
            loss[iter] = -sum;
            iter++;
        }

        return loss;
    }

    public static float avg(float[] arr) {
        float sum = 0;
        float size = arr.length;
        for(float f : arr) {
            sum += f;
        }
        return (sum/size);
    }

    public static void print(String msg) {
        char[] x = msg.toCharArray();
        System.out.println(x);
    }

    public static float[][] genRandArr(int neuronCount, int inputCount) {
        float[][] randArr = new float[neuronCount][inputCount];
        for(float[] f : randArr) {
            int l = f.length;
            for(int i = 0; i < l; i++) {
                f[i] = (float) Math.random();
            }
        }
        return randArr;

    }
    public static float[] genRandArr(int neuronCount) {
        float[] randArr = new float[neuronCount];
            for(int i = 0; i < randArr.length ; i++) {
                randArr[i] = (float) Math.random();
            }
        
        return randArr;

    }

    public static float[][] add(float[][] arr1, float[][] arr2) {
        int l = arr1.length;
        float[][] sum = new float[arr1.length][arr1[0].length];
        for(int i = 0; i < l; i++) {
            float[] arrSub1 = arr1[i];
            float[] arrSub2 = arr2[i];
            int len = arrSub1.length;
            float[] rowSum = new float[len];
            for(int j = 0; j < len; j++) {
                rowSum[j] = (arrSub1[j] + arrSub2[j]);
            }

            sum[i] = rowSum;
        }
        return sum;
    }

    public static float[] add(float[] arr1, float[] arr2) {
        int l = arr1.length;
        float[] sum = new float[l];
        for(int i = 0; i < l; i++) {
            sum[i] = arr1[i] + arr2[i];
        }
        return sum;
    }

    public static float[][] mult(float[][] arr1, float mult) {
        int l = arr1.length;
        float[][] sum = new float[arr1.length][arr1[0].length];
        for(int i = 0; i < l; i++) {
            float[] arrSub1 = arr1[i];
            
            int len = arrSub1.length;
            float[] rowSum = new float[len];
            for(int j = 0; j < len; j++) {
                rowSum[j] = (arrSub1[j] * mult);
            }

            sum[i] = rowSum;
        }
        return sum;
    }

    public static float[] mult(float[] arr1, float mult) {
        int l = arr1.length;
        float[] sum = new float[l];
        for(int i = 0; i < l; i++) {
            sum[i] = arr1[i] * mult;
        }
        return sum;
    }

    public static float acc(float[][] predictions) {
        float sum = 0;
        int total = 0;
        for(float[] f : predictions) {
            sum += max(f);
            total++;
        }
        float avg = (sum/total);
        return avg;
    }




}
