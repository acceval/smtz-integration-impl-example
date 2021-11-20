package com.acceval.core.audit.aspect;

import java.util.ArrayList;
import java.util.List;

import org.javers.common.string.PrettyValuePrinter;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.springframework.beans.factory.annotation.Autowired;

import com.acceval.core.amqp.AuditTrailChange;

public class CompareAspect {
	
	@Autowired
	private Javers javers;

    public List<AuditTrailChange> compareChanges(Object before, Object after) {
    	
    	List<AuditTrailChange> values = new ArrayList<AuditTrailChange>();
    	
    	Diff diff = javers.compare(before, after);
    	Changes changes = diff.getChanges();
		changes.forEach(change -> {			
			
			AuditTrailChange value = new AuditTrailChange();
			value.setChange(change.toString());
			int index = value.getChange().indexOf("{");
			
			if (index != -1) {
				value.setChangeType(value.getChange().substring(0, index));
			}
						
			String className = change.getClass().getSimpleName();
			PrettyValuePrinter printer = PrettyValuePrinter.getDefault();
			
			if (className.indexOf("ValueChange") != -1) {
				
				ValueChange type = (ValueChange) change;			
				value.setLeftValue(printer.format(type.getLeft()));
				value.setRightValue(printer.format(type.getRight()));
				value.setPropertyName(type.getPropertyNameWithPath());
				
			} else if (className.indexOf("ListChange") != -1) {
				
				ListChange type = (ListChange) change;
				value.setPropertyName(type.getPropertyNameWithPath());
				
			} else if (className.indexOf("NewObject") != -1) {
				
				NewObject type = (NewObject) change;
				value.setPropertyName(type.getAffectedGlobalId().value());
				
			} else if (className.indexOf("ObjectRemoved") != -1) {
				
				ObjectRemoved type = (ObjectRemoved) change;
				value.setPropertyName(type.getAffectedGlobalId().value());		
			} else if (className.indexOf("ReferenceChange") != -1) {
				
				ReferenceChange type = (ReferenceChange) change;
				value.setLeftValue(printer.format(type.getLeft()));
				value.setRightValue(printer.format(type.getRight()));
				value.setPropertyName(type.getPropertyNameWithPath());
			}
			
			values.add(value);
		});
				
		return values;
				
    }
}
