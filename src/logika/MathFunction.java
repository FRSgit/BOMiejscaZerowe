package logika;

import net.objecthunter.exp4j.Expression;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
/**
 * @author FRS
 * @version 1.00, 08/16/2015
 */
public class MathFunction {
    public LinkedList<String> namesOfArguments;
    private final Expression functionExpression;
    
    MathFunction(Expression _functionExp){
        functionExpression = _functionExp;
    }
    
    MathFunction(Expression _functionExp, LinkedList<String> _namesOfArguments){
        functionExpression = _functionExp;
        namesOfArguments = _namesOfArguments;
    }
    
    double calculateValue(LinkedList<Double> listOfValues){
        Map<String, Double> arguments = new HashMap<String, Double>();
        int numberOfArguments = getNumberOfArguments();
        for(int i = 0; i < numberOfArguments; i++)
            arguments.put(namesOfArguments.get(i), listOfValues.get(i));
        return functionExpression.setVariables(arguments).evaluate();
    }
    
    int getNumberOfArguments(){
        return namesOfArguments.size();
    }
    
    Expression getFunctionExpression(){
        return functionExpression;
    }
}
