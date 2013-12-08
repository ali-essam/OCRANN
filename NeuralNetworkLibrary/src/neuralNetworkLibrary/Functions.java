package neuralNetworkLibrary;

abstract class Functions {
	double sigmoid(double x){
		return 1.0/(1.0+Math.exp(-x));
	}
	double sigmoidDerivative(double x){
		double sigmoid = sigmoid(x);
		return sigmoid*(1-sigmoid);
	}
	
	double tanh(double x){
		return Math.tanh(x);
	}
	
	double tanhDerivative(double x){
		return 1.0-Math.pow(tanh(x),2);
	}
}
