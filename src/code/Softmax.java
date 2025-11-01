package code;

import static util.Util.*;

public class Softmax {
    public float[][] outputs;
    public void forward(float[][] inputs) {
        outputs = new float[inputs.length][inputs[0].length];
        int iter = 0;
        for(float[] f : inputs) {
            int l = f.length;
            float[] expNums = new float[l];
            float[] normNums = new float[l];
            float max = max(f);
            for(int i = 0; i < l; i++) {
                expNums[i] = (float) Math.exp(f[i] - max);
            }
            float sum = sum(expNums);
            for(int i = 0; i < l; i++) {
                normNums[i] = (expNums[i] / sum);
            }
            outputs[iter] = normNums;
            iter++;
        }
    }
}
