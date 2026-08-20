package code;

import static util.Util.*;

import java.util.ArrayList;

public class HiddenLayer {
    public float[][] weights;
    public float[] biases;
    public float[][] outputs;
    public float[][] inputs;
    public int nCount;
    public int iCount;

    public static ArrayList<HiddenLayer> originals = new ArrayList<>();

    public HiddenLayer(int neuronCount, int inputCount) {
        nCount = neuronCount;
        iCount = inputCount;
        weights = new float[neuronCount][inputCount];
        biases = new float[neuronCount];
        

        for(float[] f : weights) {
            int l = f.length;
            for(int i = 0; i < l; i++) {
                f[i] = (float) ThreadLocalRandom.current().nextDouble();
            }
        }
        int l = biases.length;
        for(int i = 0; i < l; i++) {
            biases[i] = (float) ThreadLocalRandom.current().nextDouble();
        }
        originals.add(this);

    }

    public ArrayList<HiddenLayer> createLayers(int neuronCount, int layerCount) {
        ArrayList<HiddenLayer> list = new ArrayList<>();
        for(int i = 0; i < layerCount; i++) {
            list.add(new HiddenLayer(neuronCount, neuronCount));
        }
        return list;
    }



    public void forward(float[][] inputs) throws Exception {
        this.inputs = inputs;
        outputs = add(dotP(inputs, weights),biases);
        //outputs = dotP(inputs, weights);
    }

    public void train() {
        
    }



}
