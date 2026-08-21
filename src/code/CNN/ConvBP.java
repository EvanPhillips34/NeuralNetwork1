public class ConvBP {

    public static float[] dBias(float[][][] incGrad, float learningRate) {
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

    public static float[] tDBias(Tensor incGrad, float learningRate) {
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

    public static float[][][][] wGradient(ConvLayer curlayer, float[][][] incomingGrad, int stride, float leraningRate) {
        float[][][][] result = new float[incomingGrad.length][curlayer.inputs.length][curlayer.h][curlayer.w];
        int ind = 0;
        for(float[][] filter : incomingGrad) {
            float[][][] out = new float[curlayer.inputs.length][curlayer.h][curlayer.w];
            for(int i = 0; i < curlayer.inputs.length; i++) {
                for(int row = 0; row <= curlayer.inputs[0].length - curlayer.h; row += stride) {
                    for(int col = 0; col < curlayer.inputs[0][0].length - curlayer.w; col += stride) {
                        out[i][row][col] = backDotP(curlayer.inputs[i], filter, row, col) * learningRate;
                    }
                }
            }
            result[ind] = out;
            ind++;
        }
        return result;
    }

    public static Tensor tWGradient(ConvLayer curlayer, Tensor incGrad, int stride, float learningRate) {
        int incD = incGrad.getShape()[0];
        int inputD = curlayer.tInputs.getShape()[0];

        int inputH = curlayer.tInputs.getShape()[1];
        int inputW = curlayer.tInputs.getShape()[2];

        Tensor result = new Tensor(incD, inputD, curlayer.h, curlayer.w);

        for(int i = 0; i < incD; i++) {
            for(int d = 0; d < inputD; d++) {
                for(int row = 0; row <= inputH - curlayer.h; row += stride) {
                    for(int col = 0; col <= inputW - curlayer.w; col += stride) {
                        float num = tBackDotP(curlayer.tInputs, incGrad, i, d, row, col) * learningRate;
                        result.set4D()
                    }
                }
            }
        }
        return result;
    }

    public static float[][][] layerGradient(ConvLayer curlayer, float[][][] incGrad, float learningRate) {
        int fW = curlayer.w;
        int fH = curlayer.h;
        int fD = curlayer.d;
        int padding = fW - 1;
        float[][][] output = new float[curlayer.inputs.length][][];
        float[][][] padded = pad(incGrad, padding);
        int ind = 0;
        for(float[][][] filter : curlayer.filters) {
            float[][] chOut = new float[curlayer.inputs[0].length][curlayer.inputs[0][0].length];
            for(int row = 0; row < padded[0].length; row++) {
                for(int col = 0; col < padded[0][0].length; col++) {
                    chOut[row][col] = convolve(padded, filter, row, col) * leraningRate;
                }
            }
            output[ind] = chOut;
            ind++;
        }
        return output;
    }

    public static Tensor tLGradient(ConvLayer curlayer, Tensor incGrad, float learningRate) {
        int fW = curlayer.w;
        int fH = curlayer.h;
        int fD = curlayer.d;
        int padding = fW - 1;
        Tensor padded = tPad(incGrad, padding);

        Tensor output = new Tensor(curlayer.tInputs.getShape()[0], curlayer.tInputs.getShape()[1], curlayer.tInputs.getShape()[2]);

        for(int f = 0; f < curlayer.tFilters.getShape()[0]; f++) {
            for(int row = 0; row < padded.getShape()[1]; row++) {
                for(int col = 0; col < padded.getShape()[2]; col++) {
                    float num = tConvolve(padded, filter, row, col) * leraningRate;
                    output.set3D(num, f, row, col);
                }
            }
        }
        return output;
    }
}