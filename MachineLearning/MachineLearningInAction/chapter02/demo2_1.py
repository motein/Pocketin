'''
Created on Aug 13, 2018

@author: xiongan2
'''
import numpy as np
import matplotlib.pyplot as plt
from chapter02 import kNN

def createDataSet():
    group = np.array([[1.0, 1.1],[1.0, 1.0],[0, 0],[0, 0.1]])
    labels = ['A', 'A', 'B', 'B']
    return group, labels

def createScatter():
    fig = plt.figure()
    ax = fig.add_subplot(111)
    
    datingDataMat, datingLabels = kNN.file2matrix('datingTestSet2.txt')
    
    #ax.scatter(datingDataMat[:,1], datingDataMat[:,2])
    ax.scatter(datingDataMat[:,1], datingDataMat[:,2], 15.0*np.array(datingLabels), 15.0*np.array(datingLabels))
    ax.axis([-2,25,-0.2,2.0])
    plt.xlabel('Percentage of Time Spent Playing Video Games')
    plt.ylabel('Liters of Ice Cream Consumed Per Week')
    plt.show()

if __name__ == "__main__":
    group, labels = createDataSet()
    print(kNN.classify([0,0], group, labels, 3))
     
    createScatter()