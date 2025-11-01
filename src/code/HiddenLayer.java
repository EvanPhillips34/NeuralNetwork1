package code;

import static util.Util.*;

public class HiddenLayer {
    private float[][] weights;
    private float[] biases;
    public float[][] outputs;
    public HiddenLayer(int neuronCount, int inputCount) {
        weights = new float[neuronCount][inputCount];
        biases = new float[neuronCount];

        for(float[] f : weights) {
            int l = f.length;
            for(int i = 0; i < l; i++) {
                f[i] = (float) Math.random();
            }
        }
        int l = biases.length;
        for(int i = 0; i < l; i++) {
            biases[i] = (float) Math.random();
        }
    }

    public void forward(float[][] inputs) throws Exception {
        outputs = add(dotP(inputs, weights),biases);
    }


}
