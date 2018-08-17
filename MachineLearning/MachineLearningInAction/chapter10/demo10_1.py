'''
Created on Aug 17, 2018

@author: xiongan2
'''
from chapter10 import kMeans
import numpy as np

datMat = np.mat(kMeans.loadDataSet('testSet.txt'))
# print(datMat)
print(min(datMat[:, 0]))
print(kMeans.randCent(datMat, 2))
print(kMeans.distEclud(datMat[0], datMat[1]))