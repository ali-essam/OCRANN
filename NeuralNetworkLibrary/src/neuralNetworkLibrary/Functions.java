package neuralNetworkLibrary;

abstract class Functions {
	double sigmoid(double x){
		
	}
	double sigmoidDerivative(double x){
		
	}
	
	double tanh(double x){
		return Math.tanh(x);
	}
	
	double tanhDerivative(double x){
		return 1.0-Math.pow(tanh(x),2);
	}
}
