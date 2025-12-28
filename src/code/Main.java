package code;
import static util.Util.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    //output 0: knight
    //output 1:giant
    //output 2: archers
    public static float[][] inputs = {{0.2f, 1.0f, 0.5f}, {0.6f, 1.7f, -0.24f}, {0.7f, 3.4f, 8.9f}, {0.0f, 0.6f, 2.4f}};
    //public static float[][] inputs = {{0.2f, 1.0f, 0.5f}};
    public static float[][] layer1weights = {{0.5f,0.4f,0.3f}};
    public static float[][] layer2weights = {{0.2f}, {0.8f}};
    public static float[] layer1bias = {0.7f};
    public static float[] layer2bias = {0.9f, 1.0f};
    //public static float[][] expected = {{1.0f, 0.0f, 0.0f} , {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 1.0f}};
    //public static int[] targets = {0, 1, 2, 2};
    public static int[] targets = {0, 1, 1, 0};
    //public static int[] targets = {0};

    public static void main(String[] args) throws Exception {
        var start = System.currentTimeMillis();
        
        

        HiddenLayer layer1 = new HiddenLayer(1, 3);
        HiddenLayer layer2 = new HiddenLayer(2, 1);
        //print("array for weight");
        //printArr(layer2.weights);
        layer1.weights = layer1weights;
        layer2.weights = layer2weights;
        layer1.biases = layer1bias;
        layer2.biases = layer2bias;
        Softmax softmax = new Softmax();
        ActivationReLU activation1 = new ActivationReLU();

        //print("testing clone method");
        //print(Integer.toString(layer1.iCount));

        float lowestLoss = 9999999;
        float[][] bestLayer1Weights = layer1.weights;
        float[] bestLayer1Biases = layer1.biases;
        float[][] bestLayer2Weights = layer2.weights;
        float[] bestLayer2Biases = layer2.biases;

        // layer1.forward(inputs);
        // activation1.forward(layer1.outputs);
        // printArr(layer1.outputs);
        // printArr(activation1.outputs);
        // layer2.forward(activation1.outputs);
        // printArr(layer2.outputs);
        // softmax.forward(layer2.outputs);
        // printArr(softmax.outputs);
        
        //System.out.println(HiddenLayer.layers);
        //printArr(HiddenLayer.layers.get(0).weights);
        //backpropogate.backpropogate();

        ArrayList<float[][]> weightGrad = new ArrayList<>();
        ArrayList<float[]> biasesGrad = new ArrayList<>();
        ArrayList<HiddenLayer> layers = HiddenLayer.originals;

        int epoch = 0;
        while(lowestLoss > 0.1) {
            epoch++;
            Backpropogate backpropogate = new Backpropogate();
            layer1.forward(inputs);
            //printArr(layer1.outputs);
            activation1.forward(layer1.outputs);
            //printArr(activation1.outputs);
            layer2.forward(activation1.outputs);
            //printArr(layer2.outputs);
            softmax.forward(layer2.outputs);
            
        //     print("predictions");
        //     printArr((softmax.outputs));
        //     printArr(layer1.weights);
        //     //HiddenLayer.syncData();
            backpropogate.backpropogate(softmax.outputs, targets.clone(), 0.1f);
            weightGrad = backpropogate.gradientW;
            biasesGrad = backpropogate.gradientB;

        //     print("indicies");
        //     print("" + weightGrad.size());
        //     print("" + layers.size());
            int indexGrad = weightGrad.size() - 1;
            for(int k = 0; k < layers.size(); k++) {
                layers.get(k).weights = subtract(layers.get(k).weights, weightGrad.get(indexGrad));
                layers.get(k).biases = subtract(layers.get(k).biases, biasesGrad.get(indexGrad));
                indexGrad--;
            }
            //print("hope fully the new weights");
            //printArr(layer1.weights);
            //printArr(layer2.weights);
            float loss = avg(calcLoss(softmax.outputs, targets));
            if(loss < lowestLoss) {
                lowestLoss = loss;
            }
            //weightGrad.clear();
            //print("" + weightGrad.size());

            //System.out.println("Current Loss: " + loss);
            //System.out.println("Lowest Loss: " + lowestLoss);

            


        }

        print("We found the optimal weights not biases though");
        printArr(layer1.weights);
        printArr(layer2.weights);
        System.out.println("Found lowest in " + epoch + " tries");

    var end = System.currentTimeMillis();
    System.out.println("finished in: " + (end-start) + "ms");

    }
}