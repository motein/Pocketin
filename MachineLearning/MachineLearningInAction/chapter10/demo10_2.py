'''
Created on Aug 17, 2018

@author: xiongan2
'''
from chapter10 import kMeans
import numpy as np
import matplotlib.pyplot as plt

K = 4 # define K
datMat = np.mat(kMeans.loadDataSet('testSet.txt'))
# centroid0 = np.mean(datMat, axis=0).tolist()[0]
# centList = [centroid0]
# print(type(centList[0]))

# print(np.sum(datMat[:, 0]) / len(datMat[:, 0]))
# print(centroid0)
myCentroids, clusterAssing = kMeans.kMeans(datMat, K)
# print(myCentroids)
# print(np.size(myCentroids[:, 1].flatten().A))

scatterMarkers=['s', 'o', '^', '8', 'p', 'd', 'v', 'h', '>', '<']

for i in range(K):
    ptsInCurrCluster = datMat[np.nonzero(clusterAssing[:,0].A==i)[0],:]
    markerStyle = scatterMarkers[i % len(scatterMarkers)]
    plt.scatter(ptsInCurrCluster[:,0].flatten().A[0], ptsInCurrCluster[:,1].flatten().A[0], marker=markerStyle, s=90)
        
plt.scatter(myCentroids[:, 0].flatten().A[0],myCentroids[:, 1].flatten().A[0], marker='+', s=300)
plt.show()    