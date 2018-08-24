'''
Created on Aug 24, 2018

@author: xiongan2
'''
from numpy import *
from chapter07 import adaboost

datMat, classLabels = adaboost.loadDataSet('horseColicTraining2.txt')
classifierArray, aggClassEst = adaboost.adaBoostTrainDS(datMat, classLabels, 65)
print(classifierArray)

testDatMat, testClassLabels = adaboost.loadDataSet('horseColicTest2.txt')
prediction10 = adaboost.adaClassify(testDatMat, classifierArray)
errArr = mat(ones((67, 1)))
errSum = errArr[prediction10 != mat(testClassLabels).T].sum()
print(errSum)
print(1 - errSum / len(testClassLabels))