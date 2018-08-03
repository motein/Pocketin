'''
Created on Jul 30, 2018

@author: xiongan2
'''
# Generate Toy Dataset
from matplotlib import pyplot
import numpy

x = numpy.linspace(-1, 1, 100)

signal = 2 + x + 2 * x * x
noise = numpy.random.normal(0, 0.1, 100)
y = signal + noise
pyplot.plot(signal, 'b')
pyplot.plot(y, 'g')
pyplot.plot(noise, 'r')
pyplot.xlabel("x")
pyplot.ylabel("y")
pyplot.legend(["Without Noise", "With Noise", "Noise"], loc = 2)
# pyplot.show()
x_train = x[0:80]
y_train = y[0:80]
# print(x_train)
# print(y_train)

# Model with degree 1
pyplot.figure()
degree = 2
X_train = numpy.column_stack([numpy.power(x_train, i) for i in range(0, degree)])
# print(X_train)
model = numpy.dot(numpy.dot(numpy.linalg.inv(numpy.dot(X_train.transpose(), X_train)), X_train.transpose()), y_train)
pyplot.plot(x, y, 'g')
pyplot.xlabel("x")
pyplot.ylabel("y")
predicted = numpy.dot(model, [numpy.power(x, i) for i in range(0, degree)])
pyplot.plot(x, predicted, 'r')
pyplot.legend(["Actual1", "Predicted1"], loc = 2)
# pyplot.show()
train_rmse1 = numpy.sqrt(numpy.sum(numpy.dot(y[0:80]-predicted[0:80], y_train - predicted[0:80])) / 80.0)
test_rmse1 = numpy.sqrt(numpy.sum(numpy.dot(y[80:]-predicted[80:], y[80:]-predicted[80:])) / 20.0)
print("Train RMSE (Degree = 1)", train_rmse1)
print("Test RMSE (Degree =1)", test_rmse1)

# Model with degree 2
pyplot.figure()
degree = 3
X_train = numpy.column_stack([numpy.power(x_train, i) for i in range(0, degree)])
# print(X_train)
model = numpy.dot(numpy.dot(numpy.linalg.inv(numpy.dot(X_train.transpose(), X_train)), X_train.transpose()), y_train)
pyplot.plot(x, y, 'g')
pyplot.xlabel("x")
pyplot.ylabel("y")
predicted = numpy.dot(model, [numpy.power(x, i) for i in range(0, degree)])
pyplot.plot(x, predicted, 'r')
pyplot.legend(["Actual2", "Predicted2"], loc = 2)
# pyplot.show()
train_rmse1 = numpy.sqrt(numpy.sum(numpy.dot(y[0:80]-predicted[0:80], y_train - predicted[0:80])) / 80.0)
test_rmse1 = numpy.sqrt(numpy.sum(numpy.dot(y[80:]-predicted[80:], y[80:]-predicted[80:])) / 20.0)
print("Train RMSE (Degree = 2)", train_rmse1)
print("Test RMSE (Degree =2)", test_rmse1)

# Model with degree 8
pyplot.figure()
degree = 9
X_train = numpy.column_stack([numpy.power(x_train, i) for i in range(0, degree)])
# print(X_train)
model = numpy.dot(numpy.dot(numpy.linalg.inv(numpy.dot(X_train.transpose(), X_train)), X_train.transpose()), y_train)
pyplot.plot(x, y, 'g')
pyplot.xlabel("x")
pyplot.ylabel("y")
predicted = numpy.dot(model, [numpy.power(x, i) for i in range(0, degree)])
pyplot.plot(x, predicted, 'r')
pyplot.legend(["Actual8", "Predicted8"], loc = 2)
# pyplot.show()
train_rmse1 = numpy.sqrt(numpy.sum(numpy.dot(y[0:80]-predicted[0:80], y_train - predicted[0:80])) / 80.0)
test_rmse1 = numpy.sqrt(numpy.sum(numpy.dot(y[80:]-predicted[80:], y[80:]-predicted[80:])) / 20.0)
print("Train RMSE (Degree = 8)", train_rmse1)
print("Test RMSE (Degree =8)", test_rmse1)

pyplot.show()