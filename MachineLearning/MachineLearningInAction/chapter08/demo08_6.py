'''
Created on Aug 19, 2018

@author: xiongan2
'''
from numpy import *
from matplotlib import pyplot as plt
from chapter08 import regression

xArr, yArr = regression.loadDataSet('abalone.txt')
result = regression.stageWise(xArr, yArr, 0.001, 5000)
print(result)

xMat = mat(xArr)
yMat = mat(yArr).T
xMat = regression.regularize(xMat)
yM = mean(yMat, 0)
yMat = yMat - yM
weights = regression.standRegres(xMat, yMat.T)
print(weights.T)