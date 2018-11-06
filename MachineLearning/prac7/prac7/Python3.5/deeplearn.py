from prac7ConvMLPModel import *
from SupportCode.Helpers import openTensorBoardAtIndex
import tensorflow as tf
prac7ConvMLPModel()
#Create an optimisation dictionary for GD
optDicGD = {}optDicGD["optMethod"] = "GradientDescent"
optDicGD["learning_rate"] = 0.001
#Create an optimisation dictionary for Momentum
optDicM = {}optDicM["optMethod"] = "Momentum"
optDicM["learning_rate"] = 0.001optDicM["momentum"] = 0.5
#Adagrad
optDicAGrad = {}optDicAGrad["optMethod"] = "Adagrad"
optDicAGrad["learning_rate"] = 0.001
optDicAGrad["initial_accumulator_value"] = 0.1
#RMSProp
optDicRMS = {}
optDicRMS["optMethod"] = "RMSProp"
optDicRMS["learning_rate"] = 0.001
optDicRMS["momentum"] = 0.0
optDicRMS["decay"] = 0.5
optDicRMS["centered"] = False
#This normalises the weights if True
#Adam
optDicAdam = {}
optDicAdam["optMethod"] = "Adam"
optDicAdam["learning_rate"] = 0.001
optDicAdam["beta1"]=0.7
optDicAdam["beta2"]=0.8

activationFunction = tf.nn.relu
#MLP Parameters ‐ make 2 hidden layers [500,20]
MLPTopology={}
MLPTopology['hiddenDims'] = [500,100]
prac7ConvMLPModel(model='MLP',MLPTop=MLPTopology,optimiser=optDicGD,act=activationFunction, max_steps=100)
