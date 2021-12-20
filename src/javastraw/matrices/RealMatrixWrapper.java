/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2011-2021 Broad Institute, Aiden Lab, Rice University, Baylor College of Medicine
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package javastraw.matrices;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Wrpas a apache commons RealMatrix.  We don't expose the apache class so we can use other implementations.
 *
 * @author jrobinso 7/13/12
 */
public class RealMatrixWrapper implements BasicMatrix {

    private final RealMatrix matrix;
    private float lowerValue = -1;
    private float upperValue = 1;

    public RealMatrixWrapper(RealMatrix matrix) {
        this.matrix = matrix;
        computePercentiles();
    }

    @Override
    public float getEntry(int row, int col) {
        return (float) matrix.getEntry(row, col);
    }

    @Override
    public int getRowDimension() {
        return matrix.getRowDimension();
    }

    @Override
    public int getColumnDimension() {
        return matrix.getColumnDimension();
    }

    @Override
    public float getLowerValue() {
        return lowerValue;
    }

    @Override
    public float getUpperValue() {
        return upperValue;
    }

    @Override
    public void setEntry(int i, int j, float corr) {

    }

    private void computePercentiles() {

        // Statistics, other attributes
        DescriptiveStatistics flattenedDataStats = new DescriptiveStatistics();
        double min = 1;
        double max = -1;
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                double value = matrix.getEntry(i, j);
                if (!Double.isNaN(value) && value != 1) {
                    min = Math.min(value, min);
                    max = Math.max(value, max);
                    flattenedDataStats.addValue(value);
                }
            }
        }

        // Stats
        lowerValue = (float) flattenedDataStats.getPercentile(5);
        upperValue = (float) flattenedDataStats.getPercentile(95);
    }


    public RealMatrix getMatrix() {
        return matrix;
    }
}
