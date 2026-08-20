import java.util.concurrent.ThreadLocalRandom;

public class ConvLayer extends CnLayerInterface{

    public static List<ConvLayer> allConvLayers = new ArrayList<>();
    public int numFilters;
    public int h;
    public int w;
    public int d;


    public float[][][] inputs;
    public float[][][] outputs; 

    public float[] deltaBias;
    public float[][][][] weightGradient;
    public float[][][] layerGradient;

    public float[][][][] filters;
    public float[] biases;

    public float leraningRate;

    public int stride;
    
    public ConvLayer(int height, int width, int depth, int filterCt, float learningRate, int stride) {
        //init empty array to hold all filters
        this.filters = new float[filterCt][depth][height][width];
        this.biases = new float[filterCt];
        this.outputs = new float[filterCt][][];
        this.learningRate = learningRate;
        this.stride = stride;

        //randomize weights for all filters

        for(float[][][] filter : this.filters) {
            for(int d = 0; d < filter.length; d++) {
                for(int r = 0; r < filter[0].length; r++) {
                    for(int c = 0; c < filter[0][0].length; c++) {
                        filter[d][r][c] = (float) ThreadLocalRandom.current().nextDouble();
                    }
                }
            }
        }
        //randomize all the biases for all filters
        for(float f : this.biases) {
            f = (float) ThreadLocalRandom.current().nextDouble();
        }
        
    }
    
    @Override
    public void forward(float[][][] inputs, int stride) {
        this.inputs = inputs;
        int biasInd = 0;
        for(float[][][] filter : this.filters) {
            //Output array for each filter to put in the total outputs (eachfilter buidls out the "new depth")
            float[][] output = new float[this.h][this.w];
            float[][][] input = new float[this.d][this.h][this.w];
            //this is stride = 1, going across one pixel at a time, it should probably be a variable like (row += stride)
            for(int row = 0; row <= inputs[0].length - this.h; row += stride) {
                for(int col = 0; col < inputs[0][0].length - this.w; col += stride) {
                    //divide by stride so when row = 6 and stride = 2, it isnt output[6] but rather output[3], add 1 because of floor rounding down
                    output[(row / stride) + 1][(col / stride) + 1] = (convDotP(inputs, filter, row, col) +  this.biases[biasInd]);
                }
            }
            //store the output of this filter to the output array
            this.outputs[biasInd] = output;
            biasInd++;
        }
    }

    @Override
    public void backpropogate(CNLayer prevLayer) {
        CNLayer curlayer = this;
        incGrad = prevLayer.layerGradient;
        //Chain rule ts2 
        this.deltaBias = dBias(incGrad, this.learningRate);
        this.weightGradient = wGradient(curlayer, incGrad, this.stride, this.learningRate);
        this.layerGradient = layerGradient(curlayer, incGrad, this.learningRate);

        // Send values back in a recursive manner, once it gets to the top then update everything maybe ? or update then send back,,, need to dwell on dat...

    }
}