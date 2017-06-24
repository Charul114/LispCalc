package PJ2;
import java.util.*;

public class LispExpressionEvaluator
{
    // Current input Lisp expression
    private String inputExpr;
    private Stack<Object> inputExprStack;
    private Stack<Double> evaluationStack;


    // default constructor
    // set inputExpr to "" 
    // create stack objects
    public LispExpressionEvaluator()
    {
    	inputExpr = "";
    	inputExprStack = new Stack<Object>();
    	evaluationStack = new Stack<Double>();
    }

    // constructor with an input expression 
    // set inputExpr to inputExpression 
    // create stack objects
    public LispExpressionEvaluator(String inputExpression) 
    {
    	if (inputExpression == null)
    	 throw new LispExpressionEvaluatorException(" No expression inputted ");
    		inputExpr = inputExpression;
    	    inputExprStack = new Stack<Object>();
    	    evaluationStack = new Stack<Double>();
    			
    }

    // set inputExpr to inputExpression 
    // clear stack objects
    public void reset(String inputExpression) 
    {
    	inputExpr = inputExpression; 
    	inputExprStack.clear();
    	evaluationStack.clear();
    }


    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    // 		Pop operands from inputExprStack and push them onto 
    // 			evaluationStack until you find an operator
    //  	Apply the operator to the operands on evaluationStack
    //          Push the result into inputExprStack
    //
    private void evaluateCurrentOperation()
    {
    	try{
        boolean isOperator = false;
        double result = 0;
        String operator = null;
        while (!isOperator){
    	 operator =(String.valueOf(inputExprStack.peek())) ;
    	 if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("*") )
    	 		isOperator = true;
    	 else {
    		 String temp = String.valueOf(inputExprStack.pop());
    		// System.out.println(temp);
    		 Double number = Double.parseDouble(temp);
    		 evaluationStack.push(number);}
    	
        }
    	//{if (inputExprStack.peek() == "+" || inputExprStack.peek() =="-" || inputExprStack.peek() == "/" || inputExprStack.peek() =="*")
    	
    	//else evaluationStack.push((Double)inputExprStack.pop());
    //	}
       
    	if (evaluationStack.isEmpty())
    		throw new LispExpressionEvaluatorException("LispExpressionException");
    	else {
   
	switch (operator)
  {
    case "+":
    	result = evaluationStack.pop();
		while(!evaluationStack.isEmpty())
    	{
    		result = result + evaluationStack.pop();
    	}
		//System.out.println(inputExprStack.pop());
		//System.out.println(inputExprStack.pop());
    	inputExprStack.pop();
    	inputExprStack.pop();
		inputExprStack.push(result);
		//System.out.println(result);
    	break;
    case "-":
    	if (evaluationStack.size() == 1)
    		result = result - evaluationStack.pop();
    	else {
    	result = evaluationStack.pop();
    	while(!evaluationStack.isEmpty())
    	{
    		result = result - evaluationStack.pop();
    	}
    	}
    	//System.out.println(inputExprStack.pop());
    	//System.out.println(inputExprStack.pop());
    	inputExprStack.pop();
    	inputExprStack.pop(); 
    	inputExprStack.push(result);
    	//System.out.println(result);
    	break;
    case "*":
    	result = evaluationStack.pop();
    	while(!evaluationStack.isEmpty())
    	{
    		result = result * evaluationStack.pop();
    	}
    	//System.out.println(inputExprStack.pop());
    	//System.out.println(inputExprStack.pop());
    	inputExprStack.pop();
    	inputExprStack.pop();
    	inputExprStack.push(result);
    	//System.out.println(result);
    	break;
    case "/":
    	if (evaluationStack.size() == 1)
    		result = 1 / evaluationStack.pop();
    	else{
    	result = evaluationStack.pop();
    	while(!evaluationStack.isEmpty())
    	{
    		result = result / evaluationStack.pop();
    	}
    	}
    	//System.out.println(inputExprStack.pop());
    	//System.out.println(inputExprStack.pop());
    	inputExprStack.pop();
    	inputExprStack.pop();
    	inputExprStack.push(result);
    	//System.out.println(result);
    	break;
  }
  } 
    	}
  
    catch (RuntimeException e)
    {
    	throw new LispExpressionEvaluatorException("LispExpressionError");
    }
    	
}
    /**
     * This funtion evaluates current Lisp expression in inputExpr
     * It return result of the expression 
     *
     * The algorithm:  
     *
     * Step 1   Scan the tokens in the string.
     * Step 2		If you see an operand, push operand object onto the inputExprStack
     * Step 3  	    	If you see "(", next token should be an operator
     * Step 4  		If you see an operator, push operator object onto the inputExprStack
     * Step 5		If you see ")"  // steps in evaluateCurrentOperation() :
     * Step 6			Pop operands and push them onto evaluationStack 
     * 					until you find an operator
     * Step 7			Apply the operator to the operands on evaluationStack
     * Step 8			Push the result into inputExprStack
     * Step 9    If you run out of tokens, the value on the top of inputExprStack is
     *           is the result of the expression.
     */
    public double evaluate()
    {
        // only outline is given...
        // you need to add statements/local variables
        // you may delete or modify any statements in this method
        double result = 0;
        

        // use scanner to tokenize inputExpr
        @SuppressWarnings("resource")
		Scanner inputExprScanner = new Scanner(inputExpr);
        
        // Use zero or more white space as delimiter,
        // which breaks the string into single character tokens
        inputExprScanner = inputExprScanner.useDelimiter("\\s*");

        // Step 1: Scan the tokens in the string.
        while (inputExprScanner.hasNext())
        {
		
     	    // Step 2: If you see an operand, push operand object onto the inputExprStack
            if (inputExprScanner.hasNextInt())
            {
                // This force scanner to grab all of the digits
                // Otherwise, it will just get one char
                String dataString = inputExprScanner.findInLine("\\d+");
               inputExprStack.push(dataString);
               //System.out.println(dataString);
   		// more ...
            }
            else
            {
                // Get next token, only one char in string token
                String aToken = inputExprScanner.next();
                char item = aToken.charAt(0);
                
                switch (item)
                {
                case '(':
                	inputExprStack.push(item);
                	//System.out.println(item);
                    aToken = inputExprScanner.next();
                    item = aToken.charAt(0);
                	if (aToken.equals("+") || aToken.equals("-") || aToken.equals("/") || aToken.equals("*") )
                	{
                		inputExprStack.push(item);
                		//System.out.println(item);
                	}
                	else
                		{
                		throw new LispExpressionEvaluatorException(item + " is not legal ");
                		}
                		break;
               
                case '+': 
                	inputExprStack.push("+");
                	//System.out.println(item);
                case '-':
                	inputExprStack.push("-");	
                	//System.out.println(item);	
                case '/': 
                	inputExprStack.push("/");
                	//System.out.println(item);
                case '*':
                	inputExprStack.push("*");
                	//System.out.println(item);
                	break;
                case ')':	
                	evaluateCurrentOperation();
                	//System.out.println(item);
    
                	break;
                    default:  // error
                        throw new LispExpressionEvaluatorException(item + " is not a legal expression operator");
                } // end switch
            } // end else
        } // end while
        
      String temp = String.valueOf(inputExprStack.pop());
      System.out.println(temp);
      result = Double.parseDouble(temp);
      evaluationStack.push(result);
      
      if (!inputExprStack.isEmpty())
    	  throw new LispExpressionEvaluatorException("Expression is not legal");
      else 
    	  return result;
			
    }


    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    //=====================================================================

    
    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect)
    {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
	expr.reset(s);
        try {
           result = expr.evaluate();
           System.out.printf("Evaluated result : %.2f\n", result);
        }
        catch (LispExpressionEvaluatorException e) {
            System.out.println("Evaluated result :"+e);
        }
        
        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExpressionEvaluator expr= new LispExpressionEvaluator();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 0))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+ 0))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 )) (/ 1))";
        String test4 = "(+ (/2)(+ 1))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        String test7 = "(+ (*))";
        String test8 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ ))";

	evaluateExprTest(test1, expr, "16.50");
	evaluateExprTest(test2, expr, "-378.12");
	evaluateExprTest(test3, expr, "4.50");
	evaluateExprTest(test4, expr, "1.50");
	evaluateExprTest(test5, expr, "Infinity or LispExpressionEvaluatorException");
	evaluateExprTest(test6, expr, "LispExpressionEvaluatorException");
	evaluateExprTest(test7, expr, "LispExpressionException");
	evaluateExprTest(test8, expr, "LispExpressionException");
	
    }
}
