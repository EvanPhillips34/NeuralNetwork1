import java.util.concurrent.ThreadLocalRandom;


public class Tensor {
    //the aura of Letter T - tung tung tensor;;
    private float[] tensorNums;
    private int[] dimensions;
    private int[] strides;
    private int dimension = 0;


    public Tensor(int... dimensions) {
        this.dimension = dimensions.length;
        this.dimensions = dimensions;
        int size = 1;
        for(int i : dimensions) {
            size *= i;
        }
        this.tensorNums = new float[size];
        this.strides = new int[dimensions.length];

        int curStride = 1;
        for(int i = dimensions.length - 1; i >= 0; i--) {
            this.strides[i] = curStride;
            curStride *= dimensions[i];
        }

    }

    public float get4D(int n, int d, int h, int w) {
        if(this.dimension != 4) {
            throw new IllegalArgumentException("Cannot get a number outside the dimensions of the array");
        }
        int index = (w * this.strides[0]) + (h * this.strides[1]) + (d * this.strides[2]) + (n * this.strides[3]);
        return this.tensorNums[index];
    }

    public void set4D(float val, int n, int d, int h, int w) {
        if(this.dimension != 4) {
            throw new IllegalArgumentException("Cannot get a number outside the dimensions of the array");
        }
        int index = (w * this.strides[0]) + (h * this.strides[1]) + (d * this.strides[2]) + (n * this.strides[3]);
        this.tensorNums[index] = val;
    }

    public float get3D(int d, int h, int w) {
        if(this.dimension != 3) {
            throw new IllegalArgumentException("Cannot get a number outside the dimensions of the array");
        }
        int index = (w * this.strides[0]) + (h * this.strides[1]) + (d * this.strides[2]);
        return this.tensorNums[index];
    }

    public void set3D(float val, int d, int h, int w) {
        if(this.dimension != 3) {
            throw new IllegalArgumentException("Cannot get a number outside the dimensions of the array");
        }
        int index = (w * this.strides[0]) + (h * this.strides[1]) + (d * this.strides[2]);
        this.tensorNums[index] = val;
    }

    public float get2D(int h, int w) {
        if(this.dimension != 2) {
            throw new IllegalArgumentException("Cannot get a number outside the dimensions of the array");
        }
        int index = (w * this.strides[0]) + (h * this.strides[1]);
        return this.tensorNums[index];
    }

    public void set2D(float val, int h, int w) {
        if(this.dimension != 2) {
            throw new IllegalArgumentException("Cannot get a number outside the dimensions of the array");
        }
        int index = (w * this.strides[0]) + (h * this.strides[1]);
        this.tensorNums[index] = val;
    }

    public float[] getArr() { return this.tensorNums; }
    public int[] getShape() { return this.dimensions; }
}