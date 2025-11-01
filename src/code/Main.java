package code;
import static util.Util.*;

import java.util.Arrays;

public class Main {
    public static float[][] inputs = {{0.2f, 1.0f, 0.5f}, {0.6f, 1.7f, -0.24f}, {0.7f, 3.4f, 8.9f}};
    // public static float[][] weights = {{0.3f, 0.5f, -0.6f}, {1.4f, 2.5f, 0.34f}};
    // public static float[] biases = {1.2f, 0.65f};
    public static void main(String[] args) throws Exception {
        HiddenLayer layer1 = new HiddenLayer(2, 3);
        ActivationReLU activation1 = new ActivationReLU();
        HiddenLayer layer2 = new HiddenLayer(2, 2);
        Softmax softmax = new Softmax();
        // layer1.forward(inputs);
        // printArr(layer1.outputs);
        // activation1.forward(layer1.outputs);
        // printArr(activation1.outputs);

        // layer2.forward(activation1.outputs);
        // printArr(layer2.outputs);
        // softmax.forward(layer2.outputs);
        // printArr(softmax.outputs);
        layer1.forward(inputs);
        softmax.forward(layer1.outputs);
        printArr(softmax.outputs);
        

        
    }
}