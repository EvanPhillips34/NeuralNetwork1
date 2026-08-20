package util;
import java.util.ArrayList;
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

    public static float convDotP(float[][][] inputs, float[][][] filter, int bRow, int bCol) {
        float total = 0;
        for(int i = 0; i < filter.length; i++) {
            float sum = 0;
            float[][] inL1 = inputs[i];
            float[][] ftL1 = filter[i];

            for(int k = 0; k < ftL1.length; k++) {
                for(int j = 0; j < ftL1[k].length; j++) {
                    sum += (ftL1[k][j] * inL1[k + bRow][j + bCol]);
                }
            }
            total += sum;
        }
        return total;
    }

    public static float backDotP(float[][] inputs, float[][] filter, int bRow, int bCol) {
        float sum = 0;
        for(int k = 0; k < filter.length; k++) {
            for(int j = 0; j < filter[k].length; j++) {
                sum += (filter[k][j] * inputs[k + bRow][j + bCol]);
            }
        }
        return sum;
    }

    public static float convolve(float[][][] inputs, float[][][] filter, int bRow, int bCol) {
        float total = 0;
        for(int i = 0; i < filter.length; i++) {
            float sum = 0;
            float[][] inputCH = inputs[i];
            float[][] filterCH = filter[i];
            int ftH = filter[i].length - 1;
            int ftW = filter[i][0].length - 1;

            for(int r = ftH; r >= 0; r--) {
                for(int c = ftW; c >= 0; c--) {
                    sum += filterCH[r][c] * inputCH[(ftH - r) + bRow][(ftW - c) + bCol]);
                }
            }
            total += sum
        }
        return total;
    }

    public static float[][][] pad(float[][][] gradient, int padding) {
        float[][][] result = new float[gradient.length][gradient[0].length + (2 * padding)][gradient[0][0].length + (2 * padding)];

        for(int i = 0; i < result.length; i++) {
            int originalH = gradient[i].length;
            int originalW = gradient[i][0].length;
            float[][] smallRes = new float[originalH + (padding * 2)][originalW + (padding * 2)];

            for(int r = 0; r < smallRes.length; r++) {
                for(int c = 0; c < smallRes[0].length; c++) {
                    if(r < (padding)) {
                        smallRes[r][c] = 0f;
                    }
                    else if(c < padding) {
                        smallRes[r][c] = 0f;
                    }
                    else if(c >= (originalW + padding)) {
                        smallRes[r][c] = 0f;
                    }
                    else if(r >= (originalH + padding)) {
                        smallRes[r][c] = 0f;
                    }
                    else smallRes[r][c] = originalArr[r - padding][c - padding];

                }
            }
            result[i] = smallRes;
        }
        return result;
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
        else if (val1 == val2) {
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

    public static float[][] subtract(float[][] arr1, float[][] arr2) {
        int l = arr1.length;
        float[][] sum = new float[arr1.length][arr1[0].length];
        for(int i = 0; i < l; i++) {
            float[] arrSub1 = arr1[i];
            float[] arrSub2 = arr2[i];
            int len = arrSub1.length;
            float[] rowSum = new float[len];
            for(int j = 0; j < len; j++) {
                rowSum[j] = (arrSub1[j] - arrSub2[j]);
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

    public static float[] scalarDivide(float[] arr1, float scalar) {
        int l = arr1.length;
        float[] sum = new float[l];
        for(int i = 0; i < l; i++) {
            sum[i] = arr1[i] / scalar;
        }
        return sum;
    }

    public static float[] subtract(float[] arr1, float[] arr2) {
        int l = arr1.length;
        float[] sum = new float[l];
        for(int i = 0; i < l; i++) {
            sum[i] = arr1[i] - arr2[i];
        }
        return sum;
    }

    public static float[][] scalarDivide(float[][] arr, float scalar) {
        float[][] result = new float[arr.length][arr[0].length];

        for(int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                result[i][j] = (arr[i][j] / scalar);
            } 
        }
        return result;
    }

    public static float[][] matrixMult(float[][] arr1, float[][] arr2, int row1, int col1, int row2, int col2) {
        float result[][] = new float[row1][col2];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < row2; k++)
                    result[i][j] += arr1[i][k] * arr2[k][j];
            }
        }

        return result;
    }

    public static float[][] hadamardMult(float[][] arr1, float[][] arr2, int row1, int col1, int row2, int col2) {
        float result[][];
        if(row1 > row2) {
            result = new float[row1][col1];

            for(int i = 0; i < result.length; i++) {
                for(int j = 0; j < result[0].length; j++) {
                    result[i][j] = arr1[i][j] * arr2[row2 - 1][j];
                }
            }

        }
        else if(row2 > row1) {
            result = new float[row2][col1];
            for(int i = 0; i < result.length; i++) {
                for(int j = 0; j < result[0].length; j++) {
                    result[i][j] = arr1[row1 - 1][j] * arr2[i][j];
                }
            }
        }
        else {
            result = new float[row1][col2];

            for(int i = 0; i < result.length; i++) {
                for(int j = 0; j < result[0].length; j++) {
                    result[i][j] = arr1[i][j] * arr2[row2- 1][j];
                }
            }
        }

        return result;
        
    }

    public static float[][] transposeMatrix(float[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
    
        float[][] transposedMatrix = new float[columns][rows];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }
        return transposedMatrix;
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


    public static void printList(ArrayList<float[][]> list) {
        for(float[][] f : list) {
            printArr(f.clone());
        }
    }

    public static void printListSingle(ArrayList<float[]> list) {
        for(float[] f : list) {
            printArr(f.clone());
        }
    }

    




}
