public class ConvBP {

    public float[] dBias(float[][][] incGrad, float learningRate) {
        float[] rsBiases = new float[incGrad.length];
        for(int i = 0; i < incGrad.length; i++) {
            float db = 0;
            for(int r = 0; r < errOut.length; r++) {
                for(int c = 0; c < errOut[0].length; c++) {
                    db += errOut[r][c];
                }
            }
            rsBiases[i] = db * learningRate;
        }
        return rsBiases;
    }

    public float[][][][] wGradient(ConvLayer curlayer, float[][][] incomingGrad, int stride, float leraningRate) {
        float[][][][] result = new float[incomingGrad.length][curlayer.inputs.length][curlayer.h][curlayer.w];
        int ind = 0;
        for(float[][] filter : incomingGrad) {
            float[][][] out = new float[curlayer.inputs.length][curlayer.h][curlayer.w];
            for(int i = 0; i < curlayer.inputs.length; i++) {
                for(int row = 0; row <= curlayer.inputs[0].length - this.h; row += stride) {
                    for(int col = 0; col < curlayer.inputs[0][0].length - this.w; col += stride) {
                        out[i][row][col] = backDotP(curlayer.inputs[i], filter, row, col) * learningRate;
                    }
                }
            }
            result[ind] = out;
            ind++;
        }
        return result;
    }

    public float[][][] layerGradient(ConvLayer curlayer, float[][][] incGrad, float learningRate) {
        int fW = curlayer.w;
        int fH = curlayer.h;
        int fD = curlayer.d;
        int padding = w - 1;
        float[][][] output = new float[curlayer.inputs.length][][];
        float[][][] padded = pad(incGrad, padding);
        int ind = 0;
        for(float[][][] filter : curlayer.filters) {
            float[][] chOut = new float[curlayer.inputs[0].length][curlayer.inputs[0][0].length];
            for(int row = 0; row <= padded[0].length; row++) {
                for(int col = 0; col < padded[0][0].length; col++) {
                    chOut[row][col] = convolve(padded, filter, row, col); * leraningRate
                }
            }
            output[ind] = chOut;
            ind++;
        }
        return output;
    }
}