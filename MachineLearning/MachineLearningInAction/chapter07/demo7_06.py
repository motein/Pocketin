'''
Created on Aug 24, 2018

@author: xiongan2
'''
from numpy import *
from chapter07 import adaboost

datMat, classLabels = adaboost.loadDataSet('horseColicTraining2.txt')
classifierArray, aggClassEst = adaboost.adaBoostTrainDS(datMat, classLabels, 10)
print(classifierArray)

adaboost.plotROC(aggClassEst.T, classLabels)