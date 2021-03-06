/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VerilogCompiler.SyntacticTree.Expressions;

import VerilogCompiler.Interpretation.ExpressionValue;
import VerilogCompiler.Interpretation.InstanceModuleScope;
import VerilogCompiler.Interpretation.SimulationScope;
import VerilogCompiler.SemanticCheck.ErrorHandler;
import VerilogCompiler.SemanticCheck.ExpressionType;
import VerilogCompiler.SemanticCheck.SemanticCheck;
import VerilogCompiler.SyntacticTree.Range;
import VerilogCompiler.SyntacticTree.VNode;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudezs@gmail.com >
 */
public class RangeLValue extends LValue {
    String identifier;
    Range range;

    public RangeLValue(String identifier, Range range, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.range = range;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return String.format("%s %s", identifier, range);
    }

    @Override
    public ExpressionType validateSemantics() {
        if (!SemanticCheck.getInstance().variableIsRegistered(identifier)) {
            ErrorHandler.getInstance().handleError(line, column, 
                    identifier + " is not declared");
            return ExpressionType.ERROR;
        } else if (!SemanticCheck.getInstance().variableIsVector(identifier)) {
            ErrorHandler.getInstance().handleError(line, column, 
                    identifier + " is not a vector");
            return ExpressionType.ERROR;
        }
        range.validateSemantics();
        if (SemanticCheck.getInstance().variableIsVector(identifier))
            return ExpressionType.VECTOR;
        return ExpressionType.INTEGER; /*En realidad un vector de tamaño 1*/
    }

    @Override
    public void setValue(SimulationScope simulationScope, String moduleName, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VNode getCopy() {
        return new RangeLValue(identifier, (Range)range.getCopy(), line, column);
    }

    @Override
    public void setValue(InstanceModuleScope scope, ExpressionValue value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
