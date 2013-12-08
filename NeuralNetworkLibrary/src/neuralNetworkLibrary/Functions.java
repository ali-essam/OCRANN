package neuralNetworkLibrary;

abstract class Functions {
	double sigmoid(double x){
		double y=1.0/(1.0+Math.exp(-x));
		return y;
	}
	double sigmoidDerivative(double x){
		
	}
}
