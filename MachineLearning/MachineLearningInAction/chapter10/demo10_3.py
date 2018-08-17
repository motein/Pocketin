'''
Created on Aug 17, 2018

@author: xiongan2
'''
 # -*- coding: utf-8 -*-
from chapter10 import kMeans
import numpy as np
import matplotlib.pyplot as plt

K = 3 # define K
datMat = np.mat(kMeans.loadDataSet('testSet2.txt'))
centList, myNewAssments = kMeans.biKmeans(datMat, K)
print(centList)

scatterMarkers=['s', 'o', '^', '8', 'p', 'd', 'v', 'h', '>', '<']
 
for i in range(K):
    ptsInCurrCluster = datMat[np.nonzero(myNewAssments[:,0].A==i)[0],:]
    markerStyle = scatterMarkers[i % len(scatterMarkers)]
    plt.scatter(ptsInCurrCluster[:,0].flatten().A[0], ptsInCurrCluster[:,1].flatten().A[0], marker=markerStyle, s=90)
         
plt.scatter(centList[:, 0].flatten().A[0],centList[:, 1].flatten().A[0], marker='+', s=300)
plt.show()   