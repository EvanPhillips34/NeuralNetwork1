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

    public static ArrayList<HiddenLayer> layers = new ArrayList<>();
    public static ArrayList<HiddenLayer> originals = new ArrayList<>();

    public HiddenLayer(int neuronCount, int inputCount) {
        nCount = neuronCount;
        iCount = inputCount;
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
        originals.add(this);
        layers.add(new HiddenLayer(this, false));

    }


    public HiddenLayer(HiddenLayer layer, boolean update) {
        this.nCount = layer.nCount;
        this.iCount = layer.iCount;
        this.weights = layer.weights.clone();
        this.biases = layer.biases.clone();
        if(update) {
            this.outputs = layer.outputs.clone();
            this.inputs = layer.inputs.clone();
        }

    }

    

    public static void syncData() {
        ArrayList<HiddenLayer> bufferList = new ArrayList<>();
        for(HiddenLayer layer : originals) {
            bufferList.add(new HiddenLayer(layer, true));
        }
        layers.clear();
        System.out.println("This is the buffer list length: " + bufferList.size());
        layers = bufferList;
        System.out.println("This is the updated and synced list length: " + layers.size());

    }

    public void forward(float[][] inputs) throws Exception {
        this.inputs = inputs;
        outputs = add(dotP(inputs, weights),biases);
        //outputs = dotP(inputs, weights);
    }

    public void train() {
        
    }



}
