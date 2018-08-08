'''
Created on Aug 8, 2018

@author: xiongan2
'''
import sklearn.datasets
import numpy
import pylab
import theano.tensor as T
import theano
import downhill

'''************************Generating data for our excercise************************'''
#Specifiy the number of examples we need (5000) and the noise level
train_X, train_y = sklearn.datasets.make_moons(5000, noise=0.1)
train_X = numpy.float32(train_X)
train_y = numpy.int32(train_y)

#One hot encode the target values
train_y_onehot = numpy.float32(numpy.eye(2)[train_y])

'''************************Defining the loss function************************'''
#Set Seed
numpy.random.seed(0)

num_examples = len(train_X)

#Our Neural Network
nn_input_dim = 2
nn_hdim = 1000
nn_output_dim = 2

#Regularization
reg_lambda = numpy.float32(0.01)

#Weights and bias terms
W1_val = numpy.random.randn(nn_input_dim, nn_hdim)
b1_val = numpy.zeros(nn_hdim)
W2_val = numpy.random.randn(nn_hdim, nn_output_dim)
b2_val = numpy.zeros(nn_output_dim)

X = T.matrix('X', dtype="float32")
y = T.matrix('y', dtype="float32")
W1 = theano.shared(W1_val, name='W1')
b1 = theano.shared(b1_val, name='b1')
W2 = theano.shared(W2_val, name='W2')
b2 = theano.shared(b2_val, name='b2')

batch_size = 1

#Our Loss function
z1 = X.dot(W1) + b1
a1 = T.tanh(z1)
z2 = a1.dot(W2) + b2
y_hat = T.nnet.softmax(z2)
loss_reg = 1./batch_size * reg_lambda/2 * (T.sum(T.sqr(W1)) + T.sum(T.sqr(W2)))
loss = T.nnet.categorical_crossentropy(y_hat, y).mean() + loss_reg

prediction = T.argmax(y_hat, axis=1)
predict = theano.function([X], prediction, allow_input_downcast=True)

'''************************************SGD**********************************'''
#Store the training and vlidation loss
train_loss = []
validation_loss = []

opt = downhill.build('sgd', loss=loss)

#Set up training and validation dataset splits, use only one example in a batch
#and use only one batch per step/epoc

#Use everything except last 1000 examples for training
train = downhill.Dataset([train_X[:-1000], train_y_onehot[:-1000]], batch_size=batch_size, iteration_size=1)

#Use last 1000 examples for valudation
valid = downhill.Dataset([train_X[-1000:], train_y_onehot[-1000:]])

#SGD
iterations = 0
for tm, vm in opt.iterate(train, valid=valid, patience=10000):
    iterations += 1
      
    # Record the training and validation loss
    print(tm['loss'])
    print(vm['loss'])
    train_loss.append(tm['loss'])
    validation_loss.append(vm['loss'])
      
    if iterations > 10000:
        break

x_min, x_max = train_X[:, 0].min() - 0.5, train_X[:, 0].max() + 0.5
y_min, y_max = train_X[:, 1].min() - 0.5, train_X[:, 1].max() + 0.5

x_mesh, y_mesh = numpy.meshgrid(numpy.arange(x_min, x_max, 0.01), numpy.arange(y_min, y_max, 0.01))

Z = predict(numpy.c_[x_mesh.ravel(), y_mesh.ravel()])
Z = Z.reshape(x_mesh.shape)

pylab.contourf(x_mesh, y_mesh, Z, cmap=pylab.cm.get_cmap('Spectral'))
pylab.scatter(train_X[:-1000, 0], train_X[:-1000, 1], c=train_y[:-1000], cmap=pylab.cm.get_cmap('Spectral'))
pylab.show()