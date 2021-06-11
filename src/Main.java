package com.company;
import SimulatedAnnealing;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(10);
        Problem p = new Problem() {

            @Override
            public double fit(double x) {
                return 5.38*x +0.2;
                // x=19 , f(x)=441
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 < f1;
            }
        };

        double x = sa.solve(p, 100, 0.99, 0, 10, 60);
        System.out.println("x = "+x);
        System.out.println("f(x) = "+p.fit(x));
    }
}
