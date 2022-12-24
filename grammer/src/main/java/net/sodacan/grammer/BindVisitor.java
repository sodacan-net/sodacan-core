package net.sodacan.grammer;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.sodacan.grammer.LanguageParser.ThenIdentifierContext;
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
		} else {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Incorrect number of id segments");
		}
		// Did this reference match a declaration?
		if (!localUnit.isValidDeclaration(variable,value)) {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Invalid Identifier " + ctx.getText());
		}
		return super.visitWhenIdentifier(ctx);
	}

	/**
	 * A "then identifier" is always local (cannot change the value of a variable in another unit)
	 */
	@Override
	public Void visitThenIdentifier(ThenIdentifierContext ctx) {
		List<TerminalNode> ids = ctx.ID();
		String variable = ids.get(0).getText();
		String value = ids.get(1).getText();
		// Did this reference match a declaration?
		if (!unit.isValidDeclaration(variable,value)) {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Invalid Identifier " + ctx.getText());
		}
		return super.visitThenIdentifier(ctx);
	}

	/**
	 * A variable on the "then" side is only a reference to a local unit
	 */
	@Override
	public Void visitVariableExpr(VariableExprContext ctx) {
		String variable = ctx.ID().getText();
		if (!unit.isValidDeclaration(variable,null)) {
			throw new RuntimeException("Line: " + ctx.start.getLine() + " - Invalid Identifier " + variable);
		}
		return super.visitVariableExpr(ctx);
	}

	
}
