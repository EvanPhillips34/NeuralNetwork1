import java.util.concurrent.ThreadLocalRandom;

public class ConvLayer extends CnLayerInterface{

    public static List<ConvLayer> allConvLayers = new ArrayList<>();
    public int numFilters;
    public int h;
    public int w;
    public int d;


    public float[][][] inputs;
    public float[][][] outputs; 

    public Tensor tInputs;


    public float[] deltaBias;
    public float[][][][] weightGradient;
    public float[][][] layerGradient;

    public Tensor tWeightGradient;
    public Tensor tLayerGradient;

    public float[][][][] filters;
    public float[] biases;

    public Tensor tFilters;
    public Tensor tOutputs;

    public float leraningRate;

    public int stride;
    
    public ConvLayer(int height, int width, int depth, int filterCt, float learningRate, int stride) {
        //init empty array to hold all filters
        this.filters = new float[filterCt][depth][height][width];
        this.tFilters = new Tensor(filterCt, depth, height, width);
        this.tOutputs = new Tensor(filterCt, this.h, this.w);

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

        //1D array typeshizz / tnsor methoddddddddddlllll
        for(int i = 0; i < filterCt; i++) {
            for(int d = 0; d < depth; d++) {
                for(int r = 0; r < height; r++) {
                    for(int c = 0; c < width; c++) {
                        float ran = (float) ThreadLocalRandom.current().nextDouble();
                        this.tFilters.set4D(ran, i, d, r, c);
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
    public void forward(Tensor tInputs, float[][][] inputs, int stride) {
        this.inputs = inputs;
        int biasInd = 0;
        for(float[][][] filter : this.filters) {
            //Output array for each filter to put in the total outputs (eachfilter buidls out the "new depth")
            float[][] output = new float[this.h][this.w];
            float[][][] input = new float[this.d][this.h][this.w];
            //this is stride = 1, going across one pixel at a time, it should probably be a variable like (row += stride)
            //intput height = inputs[0].length, input width = inputs[0][0].length
            for(int row = 0; row <= inputs[0].length - this.h; row += stride) {
                for(int col = 0; col < inputs[0][0].length - this.w; col += stride) {
                    //divide by stride so when row = 6 and stride = 2, it isnt output[6] but rather output[3], add 1 because of floor rounding down
                    output[(row / stride) + 1][(col / stride) + 1] = (convDotP(inputs, filter, row, col) +  this.biases[biasInd]);
                }
            }
            //store the output of this filter to the output array
            this.outputs[biasInd] = output;
            //bro doesnt know the for loop technique
            biasInd++;
        }


        //Time for the tung tung tensor method...
        int inputH = tInputs.getShape()[1];
        int inputW = tInputs.getShape()[2];
        for(int i = 0; i < filterCt; i++) {
            for(int row = 0; row <= inputH - this.h; row += stride) {
                for(int col = 0; col < inputW - this.w; col += stride) {
                    float dotP = t3DDotP(tInputs, this.tFilters, row, col) + this.biases[i];
                    this.tOutputs.set3D(dotP, i, row, col);
                }
            }
        }
        
    }
    //tung tung method
    @Override
    public void backpropogate(CNLayer prevLayer) {
        CNLayer curlayer = this;
        incGrad = prevLayer.layerGradient;
        //Chain rule ts2 
        this.deltaBias = dBias(incGrad, this.learningRate);
        this.weightGradient = wGradient(curlayer, incGrad, this.stride, this.learningRate);
        this.layerGradient = layerGradient(curlayer, incGrad, this.learningRate);

        // Send values back in a recursive manner, once it gets to the top then update everything maybe ? this >> or update then send back,,, need to dwell on that...
        //
        // but first tung tung tung tung tung Tensahur method
        Tensor incomGrad = prevLayer.tLayerGradient;
        this.deltaBias = tDBias(incomGrad, this.learningRate);
        this.tWeightGradient = tWGradient(curlayer, incomGrad, this.stride, this.learningRate);
        this.tLayerGradient = tLGradient(curlayer, incomGrad, this.learningRate);
    }   
}