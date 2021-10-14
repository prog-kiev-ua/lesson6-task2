package ua.kovalev;

import java.math.BigInteger;

public class SumElementArrayRunnable implements Runnable{
    private int beginIndex;
    private int endIndex;
    private int[] array;


    private BigInteger resultSum;

    public SumElementArrayRunnable() {
        super();
    }

    public SumElementArrayRunnable(int[] array, int beginIndex, int endIndex) {
        super();
        resultSum = BigInteger.ZERO;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.array = array;
    }

    @Override
    public void run() {
        for (int i=beginIndex; i <= endIndex; i++) {
            resultSum = resultSum.add(BigInteger.valueOf(array[i]));
        }
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public BigInteger getResultSum() {
        return resultSum;
    }

    public void setResultSum(BigInteger resultSum) {
        this.resultSum = resultSum;
    }

    @Override
    public String toString() {
        return "SumElementArrayRunnable{" +
                "beginIndex=" + beginIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
