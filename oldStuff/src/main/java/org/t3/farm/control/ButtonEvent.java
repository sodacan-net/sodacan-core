package org.t3.farm.control;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;


/**
 * A simple button or toggle event. This kind of event doesn't last long since a delayed button-press is usually undesirable.
 * @author john
 *
 */
@Role( Type.EVENT)
@Expires( "5s" )
public class ButtonEvent extends DevEvent {
	public ButtonEvent(String key) {
		super( key);
	}
}
