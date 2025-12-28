package code;

import static util.Util.*;

public class ActivationReLU {
    public float[][] outputs;
    
    public void forward(float[][] inputs) {
        outputs = new float[inputs.length][inputs[0].length];
        int iter = 0;
        for(float[] f : inputs) {
            int l = f.length;
            float[] newVals = new float[l];
            for(int i = 0; i < l; i++) {
                newVals[i] = max(0.001f, f[i]);
            }

            outputs[iter] = newVals;
            iter++;
        }
    }
}
