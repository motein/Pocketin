'''
Created on Aug 8, 2018

@author: xiongan2
'''
import sklearn.datasets
import numpy
import pylab

'''************************Generating data for our excercise************************'''
#Specifiy the number of examples we need (5000) and the noise level
train_X, train_y = sklearn.datasets.make_moons(5000, noise=0.1)
train_X = numpy.float32(train_X)
train_y = numpy.int32(train_y)

#One hot encode the target values
train_y_onehot = numpy.float32(numpy.eye(2)[train_y])

#Plot the data
pylab.scatter(train_X[:-1000, 0], train_X[:-1000, 1], c=train_y[:-1000], cmap=pylab.cm.get_cmap('Spectral'))
pylab.show()