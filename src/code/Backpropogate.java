package code;

import static util.Util.*;

import java.util.ArrayList;
import java.util.Arrays;


public class Backpropogate {
    static ArrayList<HiddenLayer> layers = HiddenLayer.originals;
    
   
    
    float[][] arr1 = {{1,2,3}, {1,2,3}, {1,2,3}};
    float[][] arr2 = {{2,2,2}};

    public ArrayList<float[][]> gradientW = new ArrayList<>();
    public ArrayList<float[][]> gradientDelErr = new ArrayList<>();
    public ArrayList<float[]> gradientB = new ArrayList<>();


    public void backpropogate(float[][] predictions, int[] expectedOutputs, float learningRate) {
        int index = layers.size();
        int size = layers.size();
        //print("maaking sure we actually running the backpropogation");
        //HiddenLayer.syncData();
        layers = HiddenLayer.originals;
        //System.out.//println(HiddenLayer.layers.size());
        int deltaIter = 0;
        
        while(index > 0) {
            index -= 1;
        //print("makig sure the array list is working properly");
        //printArr(layers.get(index).weights);
        float[][] origninalInputs;
        
       
        float[][] costFuncDeriv = new float[predictions.length][predictions[0].length];
        float[] avgCostFunc = new float[predictions[0].length];
        HiddenLayer currlayer = layers.get(index);
        HiddenLayer prevLayer = null;
        if(index != 0) {
            prevLayer = layers.get(index - 1);
            origninalInputs = prevLayer.outputs;
        }
        else {
            origninalInputs = currlayer.inputs;
        }

        for (int i = 0; i < predictions.length; i++) {
            for(int j = 0; j < predictions[0].length; j++) {
                if(expectedOutputs[i] == j) {
                    costFuncDeriv[i][j] = predictions[i][j] - 1;
                }
                else {
                    costFuncDeriv[i][j] = predictions[i][j];
                }
            }
        }
        //print("cost func deriv");
        //printArr(costFuncDeriv);

        for(float[] f : costFuncDeriv) {
            avgCostFunc = add(f, avgCostFunc);
        }


        //print("STARTING PROPOGATION////////////////////////////////////////////////////////////////////////////////////////////00///");
        if(index == (layers.size()-1)) {
            //print(" this was the out put layor difrverited");
            //printArr(currlayer.weights.clone());
            //gradientDelErr.add(calcLayerError(currlayer, currlayer.nCount, Arrays.copyOf(origninalInputs, origninalInputs.length), costFuncDeriv));
            gradientDelErr.add(costFuncDeriv);
            //print("STARTING PROPOGATION////////////////////////////////////////////////////////////////////////////////////////////00///");
            gradientDelErr.add(calcLayerError(currlayer, currlayer.nCount, Arrays.copyOf(origninalInputs, origninalInputs.length), gradientDelErr.get(deltaIter)));
            gradientW.add(calcWeightGradient(currlayer, Arrays.copyOf(origninalInputs, origninalInputs.length), gradientDelErr.get(deltaIter), learningRate));
            gradientB.add(calcBiasGradient(currlayer, gradientDelErr.get(deltaIter), learningRate));
            deltaIter++; 
        }

        else if(index > 0 && prevLayer != null) {
            
            //gradientW.add(calcLayerDerivWeights(currlayer, prevLayer, origninalInputs, costFuncDeriv).clone());
            gradientDelErr.add(calcLayerError(currlayer, currlayer.nCount, Arrays.copyOf(origninalInputs, origninalInputs.length), gradientDelErr.get(deltaIter - 1))); 
            gradientW.add(calcWeightGradient(currlayer, Arrays.copyOf(origninalInputs, origninalInputs.length), gradientDelErr.get(deltaIter), learningRate));
            gradientB.add(calcBiasGradient(currlayer, gradientDelErr.get(deltaIter), learningRate));
            deltaIter++; 
        } 
        else if(index == 0) {
            //gradientW.add(calcLayerDerivWeights(currlayer, origninalInputs, costFuncDeriv).clone());
            //print("this is WHERE we INput the ORIGNAL INPUTS...");
            //printArr(origninalInputs);
            //print("graadient size " + gradientDelErr.size());

            //gradientDelErr.add(calcLayerError(currlayer, origninalInputs.length, Arrays.copyOf(origninalInputs, origninalInputs.length), gradientDelErr.get(deltaIter - 1)));
            //print("graadient size " + gradientDelErr.size());
            gradientW.add(calcWeightGradient(currlayer, Arrays.copyOf(origninalInputs, origninalInputs.length), gradientDelErr.get(deltaIter), learningRate));
            gradientB.add(calcBiasGradient(currlayer, gradientDelErr.get(deltaIter), learningRate));
            deltaIter++;
            //System.out.println("We finished the propogation..");
            //print(Integer.toString(gradientW.size()));
            //printList(gradientW);
            //printList(gradientDelErr);
            //print("and THIS is the gradneint for THE WEIGHTS");
            //printList(gradientW);
            //print("and This is the gradient for the BIASES");
            //printListSingle(gradientB);
        }

    }

    }


    public float[] calcBiasGradient(HiddenLayer curlayer, float[][] errorDelta, float learningRate) {
        ArrayList<float[]> totalModifiedBiases = new ArrayList<>();
    
        for(float[] error1d : errorDelta) {

            float[] modifiedBiases = new float[curlayer.biases.length];
            for(int i = 0; i < modifiedBiases.length; i++) {
                modifiedBiases[i] = error1d[i] * learningRate;
            }
            totalModifiedBiases.add(modifiedBiases);
        }

        float[] averageBiases = new float[curlayer.biases.length];
        float batchSize = totalModifiedBiases.size();
        for(int i = 0; i < batchSize; i++) {
            averageBiases = add(averageBiases, totalModifiedBiases.get(i));
        }

        averageBiases = scalarDivide(averageBiases, batchSize);
        

        return averageBiases;
    }
    

    public float[][] calcWeightGradient(HiddenLayer curlayer, float[][] prevLayerOutputs, float[][] errorDeltaList, float learningRate) {
        //print("make sure we are actually calculating some sort of weight gradient i hope i hope i hopeeeeee");
        float[][] modifiedWeights = Arrays.copyOf(curlayer.weights, curlayer.weights.length);
        ArrayList<float[][]> totalBatchGradient = new ArrayList<>();

        for(int k = 0; k < errorDeltaList.length; k++) {
            float[][] weightDeriv = new float[modifiedWeights.length][modifiedWeights[0].length];
            float[] neuronInput = prevLayerOutputs[k].clone();
            float[] error1d = errorDeltaList[k].clone();
            

                for(int i = 0; i < modifiedWeights.length; i++) {
                    float errorTerm = error1d[i];
                    for(int j = 0; j < neuronInput.length; j++) {
                        weightDeriv[i][j] = (neuronInput[j] * errorTerm) * learningRate;
                }
            }
            totalBatchGradient.add(weightDeriv);
        }
        float[][] averageWeightGradient = new float[totalBatchGradient.get(0).length][totalBatchGradient.get(0)[0].length];
        float batchSize = totalBatchGradient.size();
        for(int i = 0; i < batchSize; i++) {
            averageWeightGradient = add(averageWeightGradient, totalBatchGradient.get(i));
            
        }

        averageWeightGradient = scalarDivide(averageWeightGradient, batchSize);
        return averageWeightGradient;
    }

    public float[][] calcLayerError(HiddenLayer curlayer, int prevLayerNC, float[][] prevlayerOutputs, float[][] costFunc)  {
        //print("are we really calculating error? pretty please?");
        //printArr(prevlayerOutputs);
        float[][] result = new float[prevlayerOutputs.length][curlayer.nCount];
        float[] totalAvgLayerError = new float[prevLayerNC];
        float[] bat = new float[costFunc[0].length];

        float[][] totalBatchesError = new float[costFunc.length][curlayer.weights.length];
        //print(" ut di da realll weights n foenm grave buh");
        //printArr(curlayer.weights);

        //float[][] transposedWeights = transposeMatrix(Arrays.copyOf(curlayer.weights, curlayer.weights.length));
        float[][] transposedWeights = (Arrays.copyOf(curlayer.weights, curlayer.weights.length));
        float[][] derviLayerOutputs = Arrays.copyOf(curlayer.outputs, curlayer.outputs.length);
        //printArr(derviLayerOutputs);
        //print("outputs jown jown jown");
        ArrayList<float[][]> totalBatchError = new ArrayList<>();
        //printArr(derviLayerOutputs);
        float[] outputBatch = new float[prevlayerOutputs[0].length];

        for(int batchIndex = 0; batchIndex < prevlayerOutputs.length; batchIndex++) {
            float[] layerError = new float[curlayer.weights.length];
            outputBatch = prevlayerOutputs[batchIndex].clone();            
            //System.out.//println(batchIndex);
            bat = costFunc[batchIndex].clone();
            float[][] batch2d = new float[outputBatch.length][1];
            float[][] bat2d = new float[1][bat.length];

            for(int i = 0; i < bat.length; i++) {
                bat2d[0][i] = bat[i];
            }

            for(int i = 0; i < outputBatch.length; i++) {
                if(outputBatch[i] > 0) {
                    batch2d[i][0] = 1;
                }
                else {
                    batch2d[i][0] = 0.001f;
                }
            }

            //print("batch 2 dimesnional trans form a tion");
            //printArr(batch2d);
            //printArr(output2d);

        

            
        
        //for(int i = 0; i < curlayer.weights.length; i++) {
            //float[] weightArr = curlayer.weights[i];
            //float[][] inputArr = {curlayer.weights[i].clone()};
            float neuronSum = 0f;

            //float[][] batMult = matrixMult(transposedOutputs, batch2d, transposedOutputs.length, transposedOutputs[0].length, batch2d.length, batch2d[0].length);
            //print("IMPROTANT//////////////////////////////////////");
            //printArr(transposedWeights);
            //printArr(curlayer.weights);
            //printArr(bat2d);
            float[][] batMult = matrixMult(bat2d, transposedWeights, bat2d.length, bat2d[0].length, transposedWeights.length, transposedWeights[0].length);
            //print("Now it is time for us to reveal the proper arrays used in the Hardmard ");
            //printArr(batMult);
            //printArr(batch2d);
            batch2d = transposeMatrix(batch2d);
            //float[][] hardmard  = hadamardMult(batMult, output2d, batMult.length, batMult[0].length, output2d.length, output2d[0].length);
            float[][] hardmard  = hadamardMult(batMult, (batch2d), batMult.length, batMult[0].length, batch2d.length, batch2d[0].length);
            totalBatchError.add(hardmard);
            //print("Look here");
            float[] error1d = new float[hardmard[0].length];
            for(int i = 0; i < hardmard[0].length; i++) {
                error1d[i] = hardmard[0][i];
            }

            result[batchIndex] = error1d;
            //printArr(hardmard);
            //printArr(error1d);

        //}

        //totalBatchesError[batchIndex] = layerError;
        //print("total layer error");
        //printArr(layerError);

       


    }
    //print("shit ass average tha shoul be ceros");
    //printArr(totalAvgLayerError);
    // float[][] averageError = new float[totalBatchError.get(0).length][totalBatchError.get(0)[0].length];
    // float batchSize = totalBatchError.size();
    // for(int i = 0; i < batchSize; i++) {
    //         averageError = add(averageError, totalBatchError.get(i));
            
    // }

    // averageError = scalarDivide(averageError, batchSize);
    
    //print("this the avergage type shiii");
    //printArr(totalBatchError);




    return result;
}

}
