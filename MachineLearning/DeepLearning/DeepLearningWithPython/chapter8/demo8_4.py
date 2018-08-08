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

#Plot the data
pylab.scatter(train_X[:-1000, 0], train_X[:-1000, 1], c=train_y[:-1000], cmap=pylab.cm.get_cmap('Spectral'))
# pylab.show()

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

batch_size = 100

#Our Loss function
z1 = X.dot(W1) + b1
a1 = T.tanh(z1)
z2 = a1.dot(W2) + b2
y_hat = T.nnet.softmax(z2)
loss_reg = 1./batch_size * reg_lambda/2 * (T.sum(T.sqr(W1)) + T.sum(T.sqr(W2)))
loss = T.nnet.categorical_crossentropy(y_hat, y).mean() + loss_reg

prediction = T.argmax(y_hat, axis=1)
predict = theano.function([X], prediction, allow_input_downcast=True)

'''******************************Using SGD variants implemented in Downhill****************************'''
def build_model(algo):
    loss_value = []
      
    W1.set_value(W1_val)
    b1.set_value(b1_val)
    W2.set_value(W2_val)
    b2.set_value(b2_val)
      
    opt = downhill.build(algo, loss=loss)
      
    train = downhill.Dataset([train_X[:-1000], train_y_onehot[:-1000]], batch_size=1, iteration_size=1)
    valid = downhill.Dataset([train_X[-1000:], train_y_onehot[-1000:]])
    iterations = 0
    for tm, vm in opt.iterate(train, valid, patience=1000):
        iterations += 1
        loss_value.append(vm['loss'])
        if iterations > 1000:
            break
    return loss_value

def plot_decision_boundary(pred_func, X, y):
    x_min, x_max = X[:, 0].min() - .5, X[:, 0].max() + .5
    y_min, y_max = X[:, 1].min() - .5, X[:, 1].max() + .5
    h = 0.01
    # Generate a grid of points with distance h between them
    xx, yy = numpy.meshgrid(numpy.arange(x_min, x_max, h), numpy.arange(y_min, y_max, h))
    # Predict the function value for the whole gid
    Z = pred_func(numpy.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)
    # Plot the contour and training examples
    pylab.cla()
    pylab.clf()
    pylab.contourf(xx, yy, Z, cmap=pylab.cm.get_cmap('Spectral'))
    pylab.scatter(X[:, 0], X[:, 1], c=y, cmap=pylab.cm.get_cmap('Spectral'))
    pylab.show()

def plot_loss(algo_name, loss_value):
    pylab.plot(loss_value)
    pylab.legend(algo_name)
    pylab.show()
    
algo_names = ['adadelta', 'adagrad', 'adam', 'nag', 'rmsprop', 'rprop', 'sgd']
losses = []
for algo_name in algo_names:
    print(algo_name)
    vloss = build_model(algo_name)
    losses.append(numpy.array(vloss))
    
#Display results
for l in losses:
    pylab.plot(l)
pylab.legend(algo_names)
pylab.show()