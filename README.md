# Simulated Annealing
높은 온도에서 액체 상태인 물질이 온도가 점차 낮아지면서 결정체로 변하는 과정을 모방한 해 탐색 알고리즘이다. 응용 상태에서는 물질의 분자가 자유로이 움직이는데 이를 모방하여, 해를 탐색하는 과정도 특정한 패턴없이 이루어진다. 하지만 온도가 점점 낮아지면 분자의 움직임이 점점 줄어들어 결정체가 되는데, 해 탐색 과정도 이와 유사하게 점점 더 규칙적인 방식으로 이루어진다.

## 탐색
### 후보해에 대해 이웃하는 해를 정의하여야 한다.
<img width="431" alt="스크린샷 2021-06-11 오후 3 07 29" src="https://user-images.githubusercontent.com/81741589/121639047-e89ef900-cac6-11eb-948d-48aabd258028.png">
<br/>

- 높은 T에서의 초기 탐색은 최솟값을 찾는데도 불구하고, 확률 개념을 도입하여 현재 해의 이웃해 중에서 현재 해보다 나쁜 해로 (위 방향)으로 이동하는 모습을 보인다. 
- 하지만, T가 낮아질수록 위 방향으로 이동하는 확률이 점점 작아진다. 
- 앞의 그림에서 처음 도착한 골짜기(지역 최적해)에서 더 이상 아래로 탐색할 수 없는 상태에 이르렀을 때 운 좋게 위 방향으로 탐색하다가 전역 최적해를 찾은 것을 보여준다.
- But, 모의 담금질 기법 역시 항상 전역 최적해를 찾아준다는 보장이 없다.


## Problem 코드
```java
package com.company;

public interface Problem {
    double fit(double x);
    boolean isNeighborBetter(double f0, double f1);
}

```
## Simulated Annealing 코드
```java
package com.company;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    private int niter;
    public ArrayList<Double> hist;

    public SimulatedAnnealing(int niter) {
        this.niter = niter;
        hist = new ArrayList<>();
    }

    public double solve(Problem p, double t, double a, double lower, double upper) {
        Random r = new Random();
        double x0 = r.nextDouble() * (upper - lower) + lower;
        return solve(p, t, a, x0, lower, upper);
    }

    public double solve(Problem p, double t, double a, double x0, double lower, double upper) {
        Random r = new Random();
        double f0 = p.fit(x0);
        hist.add(f0);

        for (int i=0; i<niter; i++) {
            int kt = (int) t;
            for(int j=0; j< kt; j++) {
                double x1 = r.nextDouble() * (upper - lower) + lower;
                double f1 = p.fit(x1);
                if(p.isNeighborBetter(f0, f1)) {
                    x0 = x1;
                    f0 = f1;
                    hist.add(f0);
                } else {
                    double d = Math.sqrt(Math.abs(f1 - f0));
                    double p0 = Math.exp(-d/t);
                    if(r.nextDouble() < 0.0001) {
                        x0 = x1;
                        f0 = f1;
                        hist.add(f0);
                    }
                }
            }
            t *= a;
        }
        return x0;
    }
}

```

## 3차 함수 전역 최적점
```java
package com.company;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(10);
        Problem p = new Problem() {
            @Override
            public double fit(double x) {
                return  x*x*x -4*x*x + 3*x + 2;
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 > f1;
            }
        };

        double x = sa.solve(p, 100, 0.99, 0, 0, 5);
        System.out.println("x = "+x);
        System.out.println("f(x) = "+p.fit(x));
        System.out.println(sa.hist);
    }
}

```
### 결과
<img width="198" alt="스크린샷 2021-06-11 오후 6 13 34" src="https://user-images.githubusercontent.com/81741589/121662940-dcc03080-cae0-11eb-94e9-cb722ae81b6e.png">
<br/>
<img width="248" alt="스크린샷 2021-06-11 오후 6 18 07" src="https://user-images.githubusercontent.com/81741589/121663440-6243e080-cae1-11eb-90a6-5784b677cf59.png">


## 4차 함수 전역 최적점
```java
package com.company;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(10);
        Problem p = new Problem() {
            @Override
            public double fit(double x) {
                return  -x*x*x*x -2*x*x*x + 3*x*x +x+ 2;
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 < f1;
            }
        };

        double x = sa.solve(p, 100, 0.99, 0, -5, 5);
        System.out.println("x = "+x);
        System.out.println("f(x) = "+p.fit(x));

    }
}
```
### 결과
<img width="251" alt="스크린샷 2021-06-11 오후 6 21 59" src="https://user-images.githubusercontent.com/81741589/121664026-03329b80-cae2-11eb-8303-412c5af3d8fd.png">
<br/>
<img width="287" alt="스크린샷 2021-06-11 오후 6 23 03" src="https://user-images.githubusercontent.com/81741589/121664112-19405c00-cae2-11eb-9445-0e0f9c88893a.png">


## 선형 모델 예시
- 독립변수 : 걷는 시간
- 종속변수 : 칼로리
 <br/>
 <br/>
|     | 10분 | 20분 | 30분 |40분 |50분 | 60분 |
|----|----|----|----|----|----|----|
|kcal | 54 |108 | 162 | 216 |269 |323|
<br/>

<img width="501" alt="스크린샷 2021-06-11 오후 7 33 53" src="https://user-images.githubusercontent.com/81741589/121673608-06cb2000-caec-11eb-9e0a-4c2e4e914005.png">
<br/>
- y = 5.38x + 0.02의 직선 모양이 된다.

## 성능 분석 및 결과
```java
package com.company;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(10);
        Problem p = new Problem() {
            @Override
            public double fit(double x) {
                return  5.38*x +0.2;
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
```
<img width="222" alt="스크린샷 2021-06-11 오후 7 46 34" src="https://user-images.githubusercontent.com/81741589/121675018-c8366500-caed-11eb-9a1c-6f59138f4d4b.png">

- fit 함수 안에서 직선 5.38x + 0.02을 리턴하도록 하면 가장 적합한 파라미터 값을 추정할 수 있다.
- 결과적으로 걷는 시간이 길어질 수록 칼로리 소모가 커진다.