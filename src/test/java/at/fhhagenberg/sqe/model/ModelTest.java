package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeListener;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ModelTest {

	
	
	@Test
	void SetPropertyChangesProperty() {
		var cut = new Model() {
			
			void setX() {
				setProperty("X", 1);
			}
			
			Object getX() {
				return getProperty("X");
			}
		};
		
		cut.setX();
		
		var x = cut.getX();
		assertTrue(x instanceof Integer);
	}
	
	@Test
	void SetPropertyCallEventListener() {
		var cut = new Model() {
			
			void setX() {
				setProperty("X", 1);
			}
			
			Object getX() {
				return getProperty("X");
			}
		};
		
		var listener = Mockito.mock(PropertyChangeListener.class, Mockito.CALLS_REAL_METHODS);
		
		cut.addListener(listener);
		
		cut.setX();
		Mockito.verify(listener, Mockito.times(1)).propertyChange(Mockito.any());
	}
	
	@Test
	void DoesNotCallEventListenerIfSameValueWasSet() {
		var cut = new Model() {
			
			void setX() {
				setProperty("X", 1);
			}
			
			Object getX() {
				return getProperty("X");
			}
		};
		
		var listener = Mockito.mock(PropertyChangeListener.class, Mockito.CALLS_REAL_METHODS);
		
		cut.addListener(listener);
		
		cut.setX();
		cut.setX();
		Mockito.verify(listener, Mockito.times(1)).propertyChange(Mockito.any());
	}
}
