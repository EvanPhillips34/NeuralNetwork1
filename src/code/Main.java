package code;
import static util.Util.*;

import java.util.Arrays;

public class Main {
    //output 0: knight
    //output 1:giant
    //output 2: archers
    public static float[][] inputs = {{0.2f, 1.0f, 0.5f}, {0.6f, 1.7f, -0.24f}, {0.7f, 3.4f, 8.9f}, {0.0f, 0.6f, 2.4f}};
    //public static float[][] expected = {{1.0f, 0.0f, 0.0f} , {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 1.0f}};
    public static int[] targets = {0, 1, 2, 2};

    public static void main(String[] args) throws Exception {
        var start = System.currentTimeMillis();
        
        

        HiddenLayer layer1 = new HiddenLayer(3, 3);
        HiddenLayer layer2 = new HiddenLayer(3, 3);
        Softmax softmax = new Softmax();
        ActivationReLU activation1 = new ActivationReLU();

        float lowestLoss = 9999999;
        float[][] bestLayer1Weights = layer1.weights;
        float[] bestLayer1Biases = layer1.biases;
        float[][] bestLayer2Weights = layer2.weights;
        float[] bestLayer2Biases = layer2.biases;
 

        for(int i = 0; i <= 100000; i++) {
            layer1.weights = add(layer1.weights, mult(genRandArr(layer1.nCount, layer1.iCount), 0.05f)).clone();
            layer2.weights = add(layer2.weights, mult(genRandArr(layer2.nCount, layer2.iCount), 0.05f)).clone();

            layer1.biases = add(layer1.biases, mult(genRandArr(layer1.nCount), 0.05f)).clone();
            layer2.biases = add(layer2.biases, mult(genRandArr(layer2.nCount), 0.05f)).clone();


            layer1.forward(inputs);
            activation1.forward(layer1.outputs);
            layer2.forward(activation1.outputs);
            softmax.forward(layer2.outputs);
            float[][] outputs = softmax.outputs;
            float[] lossArr = calcLoss(outputs, targets);
            float loss = avg(lossArr);

            if(loss < lowestLoss) {
                System.out.println("new lowest loss: " + lowestLoss);
                System.out.println("accuracy: " + acc(outputs));
                

                bestLayer1Biases = layer1.biases.clone();
                bestLayer1Weights = layer1.weights.clone();
                bestLayer2Biases = layer2.biases.clone();
                bestLayer2Weights = layer2.weights.clone();
                lowestLoss = loss;

            }
            else {
                layer1.biases = bestLayer1Biases.clone();
                layer2.biases = bestLayer2Biases.clone();
                layer1.weights = bestLayer1Weights.clone();
                layer2.weights = bestLayer2Weights.clone();
            }
        }


    print("final predictions:");
    printArr(softmax.outputs);

    var end = System.currentTimeMillis();
    System.out.println("finished in: " + (end-start) + "ms");

    }
}