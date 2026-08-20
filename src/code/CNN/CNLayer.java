public abstract class CNLayer {
    public abstract void forward(float[][][] inputs, int stride);

    public abstract void backpropogate(CNLayer prevLayer);
}