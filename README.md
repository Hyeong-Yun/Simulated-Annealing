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

