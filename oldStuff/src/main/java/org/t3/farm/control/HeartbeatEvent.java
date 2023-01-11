package org.t3.farm.control;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role( Type.EVENT)
@Expires( "10s" )
public class HeartbeatEvent extends DevEvent {
	public HeartbeatEvent(String key) {
		super(key);
	}
}
