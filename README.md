# Pocketin

This project is used to store some useful materials. Then I can link them to some other places.

```math
 cost[i][j]=
 \begin{cases}
    0, &\text{if} \quad i = j \\
    min_{i \leq k < j} \ (cost[i][k]+ cost[k+1][j] + p_{i}*p_{k+1}*p_{j+1}), &\text{if} \quad i < j
 \end{cases}
```
