package com.popova.service.page;

import com.popova.entity.Page;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Service
public class PageRankCalculatorService {

    public Map<Page, Double> calculatePageRanks(Set<Page> pages, double dCoefficient) {
        Entry<double[][], double[]> matrixAndAnswers = calculateMatrix(pages, dCoefficient);
        double[] solution = solveSystemEquations(matrixAndAnswers.getKey(), matrixAndAnswers.getValue());

        Map<Page, Double> map = new HashMap<>();
        for (Page page : pages) {
            map.put(page, solution[page.getId()]);
        }

        return map;
    }

    private Entry<double[][], double[]> calculateMatrix(Set<Page> pages, double dCoefficient) {
        double[][] matrix = new double[pages.size()][];
        double[] answers = new double[pages.size()];

        for (Page page : pages) {
            Entry<double[], Double> equationCoefficients
                    = calculateEquationCoefficients(page, pages.size(), dCoefficient);
            matrix[page.getId()] = equationCoefficients.getKey();
            answers[page.getId()] = equationCoefficients.getValue();
        }

        return new AbstractMap.SimpleEntry<>(matrix, answers);
    }

    private Entry<double[], Double> calculateEquationCoefficients(Page page, int lineLength, double dCoefficient) {
        double[] matrixLine = new double[lineLength];
        double answer = 1 - dCoefficient;
        matrixLine[page.getId()] = 1;

        for (Page referredBy : page.getReferredBy()) {
            matrixLine[referredBy.getId()] = (-1 * dCoefficient) / referredBy.getRefersTo().size();
        }

        return new AbstractMap.SimpleEntry<>(matrixLine, answer);
    }

    private double[] solveSystemEquations(double[][] matrix, double[] answers) {
        RealMatrix coefficients = new Array2DRowRealMatrix(matrix, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(answers, false);
        RealVector solution = solver.solve(constants);

        return solution.toArray();
    }
}
