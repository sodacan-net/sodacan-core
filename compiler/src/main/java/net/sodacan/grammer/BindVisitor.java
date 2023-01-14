package net.sodacan.grammer;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.sodacan.grammer.LanguageParser.AssignExprContext;
import net.sodacan.grammer.LanguageParser.UnitContext;
import net.sodacan.grammer.LanguageParser.VariableExprContext;
import net.sodacan.grammer.LanguageParser.WhenIdContext;
import net.sodacan.grammer.LanguageParser.WhenIdentifierContext;

public class BindVisitor extends LanguageBaseVisitor<Void> {

	protected Map<String,Unit> units;
	// The current unit
	private Unit unit;
	
	public BindVisitor( Map<String,Unit> units) {
		this.units = units;
	}

	@Override
	public Void visitUnit(UnitContext ctx) {
		// Find the unit in the map
		unit = units.get(ctx.ID(0).getText());
		return super.visitUnit(ctx);
	}

	@Override
	public Void visitWhenId(WhenIdContext ctx) {
		// TODO Auto-generated method stub
		return super.visitWhenId(ctx);
	}

	@Override
	public Void visitWhenIdentifier(WhenIdentifierContext ctx) {
		List<TerminalNode> ids = ctx.ID();
		// attribute.value is 2 nodes or unit.attribute.value is 3 nodes
		Unit localUnit = unit;
		String variable;
		String value;
		if (ids.size()==3) {
			localUnit = units.get(ids.get(0).getText());
			variable = ids.get(1).getText();
			value = ids.get(2).getText();
		} else if (ids.size()==2) {
			variable = ids.get(0).getText();
			value = ids.get(1).getText();
		// This means: return true if the variable has changed
		} else if (ids.size()==1) {
			variable = ids.get(0).getText();
			value = null;
		} else {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Incorrect number of id segments");
		}
		// Did this reference match a declaration?
		if (!localUnit.isValidDeclaration(variable,value)) {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " Identifier " + ctx.getText() + " is not declared");
		}
		return super.visitWhenIdentifier(ctx);
	}


	
	@Override
	public Void visitAssignExpr(AssignExprContext ctx) {
		String variable = ctx.ID().getText();
		if (!unit.isValidDeclaration(variable,null)) {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Invalid Identifier " + variable);
		}
		return super.visitAssignExpr(ctx);
	}
	
}
